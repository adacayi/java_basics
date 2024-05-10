package com.sanver.basics.utils;

import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class PerformanceComparer {
    private final Runnable[] tasks;

    public PerformanceComparer(Runnable... tasks) {
        this.tasks = tasks;
    }

    public synchronized void compare() {
        var countDownLatch = new CountDownLatch(1);
        var completableFutures = IntStream.range(0, tasks.length)
                .mapToObj(
                        taskId -> {
                            var stopWatch = new StopWatch();
                            return CompletableFuture.runAsync(() -> {
                                try {
                                    countDownLatch.await();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                stopWatch.start();
                                tasks[taskId].run();
                                stopWatch.stop();
                            }).whenComplete((result, ex) -> {
                                if (ex != null) {
                                    System.out.printf("An error occurred for task %s. Error: %s%n", taskId, ex);
                                    return;
                                }
                                System.out.printf("Task %d completed in: %s%06d%n", taskId, stopWatch, stopWatch.getNanoTime() % 1_000_000);
                            });
                        })
                .toArray(CompletableFuture[]::new);
        countDownLatch.countDown();
        CompletableFuture.allOf(completableFutures).join();
    }
}
