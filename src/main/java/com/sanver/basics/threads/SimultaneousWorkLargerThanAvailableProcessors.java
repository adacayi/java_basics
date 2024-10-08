package com.sanver.basics.threads;

import java.text.NumberFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;
import static com.sanver.basics.utils.Utils.sleepNano;

public class SimultaneousWorkLargerThanAvailableProcessors {
    public static void main(String[] args) {
        var parallelism = Runtime.getRuntime().availableProcessors() * 3;
        var workCount = parallelism;
        var executor = Executors.newFixedThreadPool(parallelism); // Even though the parallelism is larger than the processor count available to the JVM, all the work is executed in parallel and the final duration is around 1 second.
        execute(parallelism, workCount, executor);
        executor.shutdown();


        parallelism = ForkJoinPool.commonPool().getParallelism();
        workCount = 3 * parallelism; // Since the work count is 3 times the number of parallelism, the execution takes 3 times more than the individual work duration, which is roughly 1 second.
        execute(parallelism, workCount, ForkJoinPool.commonPool());
    }

    private static void execute(int parallelism, int workCount, Executor executor) {
        var startLatch = new CountDownLatch(1);
        System.out.println("Parallelism: " + parallelism);
        System.out.println("Work count: " + workCount);

        Runnable work = () -> {
            uncheck(() -> startLatch.await());
            sleepNano(1_000_000_000); // Simulates a job taking 1 second by checking System.nanoTime()
        };
        var futures = IntStream.range(0, workCount).mapToObj(x -> CompletableFuture.runAsync(work, executor)).toArray(CompletableFuture[]::new);
        startLatch.countDown();
        var start = System.nanoTime();
        CompletableFuture.allOf(futures).join();
        var end = System.nanoTime();
        var format = NumberFormat.getInstance();
        System.out.printf("Duration: %s%n%n", format.format(end - start));
    }
}
