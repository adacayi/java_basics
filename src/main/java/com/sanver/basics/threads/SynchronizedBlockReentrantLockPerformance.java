package com.sanver.basics.threads;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;

/**
 * This is to compare synchronized blocks vs. Reentrant lock with 2 threads scenario (One thread for read and one for write).
 * This also tests the performance of wait() and await(), and same functionality without wait().
 * Note that performance with multiple thread access might be different, so test it as well.
 */
public class SynchronizedBlockReentrantLockPerformance {
    private static final int OPERATION_COUNT = 300_000;
    private static final int THREAD_COUNT = 1; // Count of read and write threads. THREAD_COUNT = 1 means one read thread and 1 write thread, i.e. 2 threads in total.
    private static final Object BLOCK_LOCK = new Object();
    private static final List<Integer> list = new ArrayList<>();
    private static final List<Integer> read = new ArrayList<>();
    private static ExecutorService executor;

    public static void main(String[] args) {
        var lock = new ReentrantLock();
        var condition = lock.newCondition();

        try (var executor = Executors.newCachedThreadPool()) { // We used cached thread pool to make sure that there are both read and write threads so there is no blocking (if the thread count exceeds the pool max thread count, then all threads will be read threads, and they will all be blocked)
            SynchronizedBlockReentrantLockPerformance.executor = executor;
            measure(() -> {
                clear();
                var futures = getFutures(getRead(), getWrite());
                CompletableFuture.allOf(futures).join();
                printState();
            }, "Synchronized block with wait");

            measure(
                    () -> {
                        clear();
                        var futures = getFutures(getRead(lock, condition), getWrite(lock, condition));
                        CompletableFuture.allOf(futures).join();
                        printState();
                    }, "Reentrant lock with condition"
            );

            measure(
                    () -> {
                        clear();
                        var futures = getFutures(getReadNoWait(), getWriteNoWait());
                        CompletableFuture.allOf(futures).join();
                        printState();
                    }, "Synchronized block without wait");

            measure(
                    () -> {
                        clear();
                        var futures = getFutures(getRead(lock), getWrite(lock));
                        CompletableFuture.allOf(futures).join();
                        printState();
                    }, "Reentrant lock with no condition");
        }
    }

    private static void clear() {
        list.clear();
        read.clear();
    }

    private static void printState() {
        System.out.printf("%nRead: %,d Is ordered: %s List size: %,d%n", read.size(), isOrdered(), list.size());
    }

    private static boolean isOrdered() {
        var readSize = read.size();

        for (int i = 1; i < readSize; i++) {
            if (read.get(i - 1) > read.get(i)) {
                return false;
            }
        }

        return true;
    }

    private static CompletableFuture<?>[] getFutures(Runnable runnable1, Runnable runnable2) {
        var first = IntStream.range(0, THREAD_COUNT).mapToObj(x -> CompletableFuture.runAsync(runnable1, executor)).toArray(CompletableFuture[]::new);
        var second = IntStream.range(0, THREAD_COUNT).mapToObj(x -> CompletableFuture.runAsync(runnable2, executor)).toArray(CompletableFuture[]::new);
        CompletableFuture<?>[] combined = new CompletableFuture[first.length + second.length];
        System.arraycopy(first, 0, combined, 0, first.length);
        System.arraycopy(second, 0, combined, first.length, second.length);
        return combined;
    }

    private static Runnable getReadNoWait() {
        return () -> {
            int i = 0;

            while (i < OPERATION_COUNT) {
                synchronized (BLOCK_LOCK) {
                    if (list.isEmpty()) {
                        continue;
                    }

                    var removed = list.removeFirst();
                    read.add(removed);
                    i++;
                }
            }
        };
    }

    private static Runnable getWriteNoWait() {
        return () -> {
            int i = 0;

            while (i < OPERATION_COUNT) {
                synchronized (BLOCK_LOCK) {
                    if (!list.isEmpty()) {
                        continue;
                    }

                    list.add(i);
                    i++;
                }
            }
        };
    }

    private static Runnable getRead() {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                synchronized (BLOCK_LOCK) {
                    while (list.isEmpty()) {
                        try {
                            BLOCK_LOCK.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    var removed = list.removeFirst();
                    read.add(removed);
                    BLOCK_LOCK.notifyAll();
                }
            }
        };
    }

    private static Runnable getWrite() {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                synchronized (BLOCK_LOCK) {
                    while (!list.isEmpty()) {
                        try {
                            BLOCK_LOCK.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    list.add(i);
                    BLOCK_LOCK.notifyAll();
                }
            }
        };
    }

    private static Runnable getRead(ReentrantLock lock, Condition condition) {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                lock.lock();
                try {
                    while (list.isEmpty()) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    var removed = list.removeFirst();
                    read.add(removed);
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        };
    }

    private static Runnable getWrite(ReentrantLock lock, Condition condition) {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                lock.lock();
                try {
                    while (!list.isEmpty()) {
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    list.add(i);
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        };
    }

    private static Runnable getRead(ReentrantLock lock) {
        return () -> {
            int i = 0;

            while (i < OPERATION_COUNT) {
                lock.lock();

                try {
                    if (list.isEmpty()) {
                        continue;
                    }
                    var removed = list.removeFirst();
                    read.add(removed);
                    i++;
                } finally {
                    lock.unlock();
                }
            }
        };
    }

    private static Runnable getWrite(ReentrantLock lock) {
        return () -> {
            int i = 0;

            while (i < OPERATION_COUNT) {
                lock.lock();
                try {
                    if (!list.isEmpty()) {
                        continue;
                    }
                    list.add(i);
                } finally {
                    lock.unlock();
                }
                i++;
            }
        };
    }
}

