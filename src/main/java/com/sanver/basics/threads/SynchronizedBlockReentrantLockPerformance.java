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
import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;

/**
 * This is to compare synchronized blocks vs. Reentrant lock with 2 threads scenario (One thread for read and one for write).
 * This also tests the performance of wait() and await(), and same functionality without wait().
 * Note that performance with multiple thread access might be different, so test it as well.
 */
public class SynchronizedBlockReentrantLockPerformance {
    private static final int OPERATION_COUNT = 300_000;
    private static final int THREAD_COUNT = 1; // Count of read and write threads. THREAD_COUNT = 1 means one read thread and 1 write thread, i.e. 2 threads in total.
    private static List<Integer> list = new ArrayList<>();
    private static List<Integer> read = new ArrayList<>();
    private static ExecutorService executor;

    public static void main(String[] args) {
        var lock = new ReentrantLock();
        var condition = lock.newCondition();
        var blockLock = new Object();

        try(var executor = Executors.newCachedThreadPool()) { // We used cached thread pool to make sure that there are both read and write threads so there is no blocking (if the thread count exceeds the pool max thread count, then all threads will be read threads, and they will all be blocked)
            SynchronizedBlockReentrantLockPerformance.executor = executor;
            measure(() -> {
                clear();
                var futures = getFutures(getRead(blockLock), getWrite(blockLock));
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
                        var futures = getFutures(getReadNoWait(blockLock), getWriteNoWait(blockLock));
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

    private static Runnable getReadNoWait(Object lock) {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                synchronized (lock) {
                    if (list.isEmpty()) {
                        i--;
                        continue;
                    }
                    var removed = list.remove(0);
                    read.add(removed);
                }
            }
        };
    }

    private static Runnable getWriteNoWait(Object lock) {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                synchronized (lock) {
                    if (!list.isEmpty()) {
                        i--;
                        continue;
                    }
                    list.add(i);
                }
            }
        };
    }

    private static Runnable getRead(Object lock) {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                synchronized (lock) {
                    while (list.isEmpty()) {
                        uncheck(() -> lock.wait());
                    }
                    var removed = list.remove(0);
                    read.add(removed);
                    lock.notifyAll();
                }
            }
        };
    }

    private static Runnable getWrite(Object lock) {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                synchronized (lock) {
                    while (!list.isEmpty()) {
                        uncheck(() -> lock.wait());
                    }

                    list.add(i);
                    lock.notifyAll();
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
                        uncheck(() -> condition.await());
                    }
                    var removed = list.remove(0);
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
                        uncheck(() -> condition.await());
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
            for (int i = 0; i < OPERATION_COUNT; i++) {
                lock.lock();
                try {
                    if (list.isEmpty()) {
                        i--;
                        continue;
                    }
                    var removed = list.remove(0);
                    read.add(removed);
                } finally {
                    lock.unlock();
                }
            }
        };
    }

    private static Runnable getWrite(ReentrantLock lock) {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                lock.lock();
                try {
                    if (!list.isEmpty()) {
                        i--;
                        continue;
                    }
                    list.add(i);
                } finally {
                    lock.unlock();
                }
            }
        };
    }
}

