package com.sanver.basics.threads.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

public class ThreadFactorySample {
    public static void main(String[] args) {
        // thenApply uses the same executor as the previous CompletableFuture, whereas thenApplyAsync uses the ForkJoinPool.commonPool.
        var executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(2, new CustomThreadFactory("Custom-pool-worker"));

        IntUnaryOperator square = x -> {
            var thread = Thread.currentThread();
            System.out.printf("Calculation of square of %3d  started. Thread id: %3d, Thread name: %s%n",x, thread.getId(), thread.getName());
            sleep(2000);
            System.out.printf("Calculation of square of %3d finished. Thread id: %3d, Thread name: %s%n",x, thread.getId(), thread.getName());
            return x * x;
        };

        var future = CompletableFuture.supplyAsync(() -> square.applyAsInt(3), executor).thenApply(square::applyAsInt);
        future.join();

        printThreadPool(executor, "State of the Custom pool after all operations finished");
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
