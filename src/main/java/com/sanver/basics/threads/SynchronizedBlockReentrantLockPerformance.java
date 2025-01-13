package com.sanver.basics.threads;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static com.sanver.basics.utils.PerformanceComparer.measure;
import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;

/**
 * This is to compare synchronized blocks vs. Reentrant lock with 2 thread scenario.
 * This also tests the performance of wait() and await(), and same functionality without wait().
 * Note that performance with multiple thread access might be different, so test it as well.
 */
public class SynchronizedBlockReentrantLockPerformance {
    public static final int OPERATION_COUNT = 1_000_000;
    private static List<Integer> list = new ArrayList<>();
    private static List<Integer> read = new ArrayList<>();

    public static void main(String[] args) {
        var lock = new ReentrantLock();
        var condition = lock.newCondition();
        var blockLock = new Object();

        measure(
                () -> {
                    clear();
                    var future1 = CompletableFuture.runAsync(getReadNoWait(blockLock));
                    var future2 = CompletableFuture.runAsync(getWriteNoWait(blockLock));
                    future1.join();
                    future2.join();
                    printState();
                }, "Synchronized block without wait");
        measure(() -> {
            clear();
            var future1 = CompletableFuture.runAsync(getRead(blockLock));
            var future2 = CompletableFuture.runAsync(getWrite(blockLock));
            future1.join();
            future2.join();
            printState();
        }, "Synchronized block with wait");
        measure(
                () -> {
                    clear();
                    var future1 = CompletableFuture.runAsync(getRead(lock));
                    var future2 = CompletableFuture.runAsync(getWrite(lock));
                    future1.join();
                    future2.join();
                    printState();
                }, "Reentrant lock with no condition");
        measure(
                () -> {
                    clear();
                    var future1 = CompletableFuture.runAsync(getRead(lock, condition));
                    var future2 = CompletableFuture.runAsync(getWrite(lock, condition));
                    future1.join();
                    future2.join();
                    printState();
                }, "Reentrant lock with condition"
        );

    }

    private static void clear() {
        list.clear();
        read.clear();
    }

    private static void printState() {
        System.out.printf("Read: %,d Is ordered: %s List size: %,d%n", read.size(), isOrdered(), list.size());
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
                    if (list.isEmpty()) {
                        uncheck(() -> lock.wait());
                    }
                    var removed = list.remove(0);
                    read.add(removed);
                    lock.notify();
                }
            }
        };
    }

    private static Runnable getWrite(Object lock) {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                synchronized (lock) {
                    if (!list.isEmpty()) {
                        uncheck(() -> lock.wait());
                    }

                    list.add(i);
                    lock.notify();
                }
            }
        };
    }

    private static Runnable getRead(ReentrantLock lock, Condition condition) {
        return () -> {
            for (int i = 0; i < OPERATION_COUNT; i++) {
                lock.lock();
                try {
                    if (list.isEmpty()) {
                        uncheck(() -> condition.await());
                    }
                    var removed = list.remove(0);
                    read.add(removed);
                    condition.signal();
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
                    if (!list.isEmpty()) {
                        uncheck(() -> condition.await());
                    }

                    list.add(i);
                    condition.signal();
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

