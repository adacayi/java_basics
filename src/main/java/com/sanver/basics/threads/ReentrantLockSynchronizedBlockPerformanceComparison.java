package com.sanver.basics.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;

public class ReentrantLockSynchronizedBlockPerformanceComparison {
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final Object lockObject = new Object();
    private static int valueForReentrantLock = 0;
    private static int valueForSynchronizedBlock = 0;

    public static void incrementForReentrantLock() {
        reentrantLock.lock(); // We invoke the lock method outside try, so that if there is any exception thrown without lock being acquired, unlock() in the finally block will not be executed, and we will not get an IllegalMonitorStateException

        try {
            valueForReentrantLock++;
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void incrementForSynchronizedBlock() {
        synchronized (lockObject) {
            valueForSynchronizedBlock++;
        }
    }

    public static void main(String[] args) {
        measure(runMultipleTimes(() -> incrementForReentrantLock()), "Reentrant Lock    ");
        measure(runMultipleTimes(() -> incrementForSynchronizedBlock()), "Synchronized Block");
        System.out.printf("Value for reentrant lock    : %,d%n", valueForReentrantLock);
        System.out.printf("Value for synchronized block: %,d%n", valueForSynchronizedBlock);
    }

    private static Runnable runMultipleTimes(Runnable operation) {
        return () -> {
            var futures = IntStream.range(0, Runtime.getRuntime().availableProcessors() / 4)
                    .mapToObj(x -> CompletableFuture.runAsync(() -> {
                        for (int i = 0; i < 10_000_000; i++) {
                            operation.run();
                        }
                    })).toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(futures).join();
        };
    }
}
