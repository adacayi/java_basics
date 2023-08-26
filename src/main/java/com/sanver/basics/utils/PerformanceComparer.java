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

    synchronized public void compare() {
        var countDownLatch = new CountDownLatch(1);
        var completableFutures = IntStream.range(0, tasks.length).mapToObj(
                i -> (Runnable) () -> {
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    var stopWatch = new StopWatch();
                    stopWatch.start();
                    tasks[i].run();
                    stopWatch.stop();
                    System.out.printf("Operation %d completed in: %s%06d\n", i, stopWatch, stopWatch.getNanoTime() % 1_000_000);
                }
        ).map(CompletableFuture::runAsync).toArray(CompletableFuture[]::new);
        countDownLatch.countDown();
        CompletableFuture.allOf(completableFutures).join();
    }
}
