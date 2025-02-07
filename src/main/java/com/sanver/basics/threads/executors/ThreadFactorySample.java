package com.sanver.basics.threads.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

public class ThreadFactorySample {
    public static void main(String[] args) {
        demonstrate(new CustomThreadFactory("Custom-pool-worker"));
        var threadOfFactory = Thread.ofPlatform().name("thread-of-platform-worker-", 1).daemon().factory(); // This is introduced as of Java 21
        demonstrate(threadOfFactory);
    }

    private static void demonstrate(ThreadFactory factory) {
        // thenApply uses the same executor as the previous CompletableFuture, whereas thenApplyAsync uses the ForkJoinPool.commonPool.
        var executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4, factory);
        printThreadPool(executor, "Initial state");
        sleep(7000);

        var futures = IntStream.range(0,4).mapToObj(i -> CompletableFuture.supplyAsync(squareSupplier(i), executor)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();

        sleep(7_000);
        printThreadPool(executor, "%nState of the custom pool after all operations finished".formatted());
        // Even though the pool size is not zero (there are still threads (not active though)),
        // since the threads are set as daemon,
        // the main thread will exit without calling the executor.shutdown()
    }

    private static Supplier<Integer> squareSupplier(int i) {
        return () -> {
            System.out.printf("Calculation of square of %d  started. %s%n", i, getThreadInfo());
            sleep(7_000);
            System.out.printf("Calculation of square of %d finished. %s%n", i, getThreadInfo());
            return i * i;
        };
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
