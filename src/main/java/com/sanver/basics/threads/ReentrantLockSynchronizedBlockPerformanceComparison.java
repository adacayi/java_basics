package com.sanver.basics.threads;

import com.sanver.basics.utils.PerformanceComparer;

import java.text.NumberFormat;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.LambdaExceptionUtil.rethrowRunnable;
import static com.sanver.basics.utils.Utils.sleep;

public class ReentrantLockSynchronizedBlockPerformanceComparison {
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    private static final Object lockObject = new Object();
    private static final CountDownLatch startLatch = new CountDownLatch(1);
    private static int valueForReentrantLock = 0;
    private static int valueForSynchronizedBlock = 0;

    public static void incrementForReentrantLock() {
        try {
            reentrantLock.lock();
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
        var futuresForReentrantLock = getFutures(() -> incrementForReentrantLock());
        var futuresForSynchronizedBlock = getFutures(() -> incrementForSynchronizedBlock());

        var compare = CompletableFuture.runAsync(() ->
                new PerformanceComparer(
                        Map.of(
                                futuresForReentrantLock::join, "Reentrant Lock    ",
                                futuresForSynchronizedBlock::join, "Synchronized Block")
                ).compare());
        sleep(100);
        startLatch.countDown();
        compare.join();
        System.out.println("Value for reentrant lock    : " + NumberFormat.getInstance().format(valueForReentrantLock));
        System.out.println("Value for synchronized block: " + NumberFormat.getInstance().format(valueForSynchronizedBlock));
    }

    private static CompletableFuture<?> getFutures(Runnable operation) {
        var futures = IntStream.range(0, Runtime.getRuntime().availableProcessors() / 4)
                .mapToObj(x -> CompletableFuture.runAsync(rethrowRunnable(() -> {
                    startLatch.await();

                    for (int i = 0; i < 40_000_000; i++) {
                        operation.run();
                    }
                }))).toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(futures);
    }
}
