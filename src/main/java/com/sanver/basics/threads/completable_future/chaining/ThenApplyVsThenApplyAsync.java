package com.sanver.basics.threads.completable_future.chaining;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

public class ThenApplyVsThenApplyAsync {
    public static void main(String[] args) {
        // thenApply uses the same executor of the CompletableFuture, or if the CompletableFuture is already completed, then it will use the thread from which it was called.
        // An executor can be specified for thenApplyAsync.
        // If no executor was specified, thenApplyAsync uses the ForkJoinPool.commonPool regardless of the previous CompletableFuture completion status.
        var executor = (ThreadPoolExecutor) Executors.newCachedThreadPool(new CustomThreadFactory("Custom-pool-worker")); // If we used newFixedThreadPool(), new threads will be generated until the pool size is reached, even though the already generated threads are idle. We chose cachedThreadPool for simplicity.

        IntUnaryOperator square = x -> {
            System.out.printf("Calculation of square of %3d  started. %s%n", x, getThreadInfo());
            sleep(2000);
            System.out.printf("Calculation of square of %3d finished. %s%n", x, getThreadInfo());
            return x * x;
        };

        var first = CompletableFuture.supplyAsync(() -> square.applyAsInt(3), executor).thenApply(square::applyAsInt);
        first.join();

        var second = CompletableFuture.supplyAsync(() -> square.applyAsInt(5), executor);
        second.join();
        System.out.println("Second is completed: " + second.isDone());
        var secondThenApply = second.thenApply(square::applyAsInt); // Notice this runs in the main thread, the thread calling the thenApply, since the previous completable future was already completed.
        secondThenApply.join();

        var third = CompletableFuture.supplyAsync(() -> square.applyAsInt(7), executor).thenApplyAsync(square::applyAsInt);
        third.join();

        var fourth = CompletableFuture.supplyAsync(() -> square.applyAsInt(11), executor);
        fourth.join();
        fourth.thenApplyAsync(square::applyAsInt).join(); // ThenApplyAsync will be executed in the ForkJoinPool.commonPool() even though the CompletableFuture is completed.

        var fifth = CompletableFuture.supplyAsync(() -> square.applyAsInt(13), executor);
        fifth.join(); // ThenApplyAsync will be executed in the executor even though the CompletableFuture is completed.
        fifth.thenApplyAsync(square::applyAsInt, executor).join();

        printThreadPool(executor, "State of the Custom pool after all operations finished");
        // Even though the pool size is not zero (there are still threads (not active though)), since the threads are set as daemon, the main thread will exit without calling the executor.shutdown()
    }

    // Custom ThreadFactory to set thread name prefix
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
