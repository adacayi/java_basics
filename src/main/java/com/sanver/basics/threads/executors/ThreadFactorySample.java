package com.sanver.basics.threads.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

public class ThreadFactorySample {
    public static void main(String[] args) {
        // thenApply uses the same executor as the previous CompletableFuture, whereas thenApplyAsync uses the ForkJoinPool.commonPool.
        var executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2, new CustomThreadFactory("Custom-pool-worker"));
        printThreadPool(executor, "Initial state");
        sleep(7000);

        IntUnaryOperator square = x -> {
            System.out.printf("Calculation of square of %3d  started. %s%n", x, getThreadInfo());
            sleep(7_000);
            System.out.printf("Calculation of square of %3d finished. %s%n", x, getThreadInfo());
            return x * x;
        };

        var future = CompletableFuture.supplyAsync(() -> square.applyAsInt(3), executor).thenApply(square::applyAsInt);
        future.join();

        sleep(7_000);
        printThreadPool(executor, "%nState of the custom pool after all operations finished".formatted());
        // Even though the pool size is not zero (there are still threads (not active though)),
        // since the threads are set as daemon,
        // the main thread will exit without calling the executor.shutdown()
    }

    // Custom ThreadFactory to set thread name prefix and isDaemon
    static class CustomThreadFactory implements ThreadFactory {
        private final String namePrefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        CustomThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            // Customize the thread name with a prefix and unique number
            String threadName = namePrefix + "-" + threadNumber.getAndIncrement();
            Thread thread = new Thread(r, threadName);
            thread.setDaemon(true);
            return thread;
        }
    }
}
