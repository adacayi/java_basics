package com.sanver.basics.utils;

import org.apache.commons.lang3.time.StopWatch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public class PerformanceComparer {
    private final Map<Runnable, String> taskMap;

    public PerformanceComparer(Runnable... tasks) {
        taskMap = new HashMap<>();

        for (int i = 0; i < tasks.length; i++) {
            taskMap.put(tasks[i], "" + i);
        }
    }

    public PerformanceComparer(Map<Runnable, String> taskMap) {
        this.taskMap = taskMap;
    }

    public synchronized void compare() {
        var countDownLatch = new CountDownLatch(1);
        var completableFutures = taskMap.entrySet().stream()
                .map(entry -> {
                    var stopWatch = new StopWatch();
                    var task = entry.getKey();
                    var taskName = entry.getValue();
                    return CompletableFuture.runAsync(() -> {
                        try {
                            countDownLatch.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        stopWatch.start();
                        task.run();
                        stopWatch.stop();
                    }).whenComplete((result, ex) -> {
                        if (ex != null) {
                            System.out.printf("An error occurred for task %s. Error: %s%n", taskName, ex);
                            return;
                        }
                        System.out.printf("Task %s completed in: %s%06d%n", taskName, stopWatch, stopWatch.getNanoTime() % 1_000_000);
                    });
                })
                .toArray(CompletableFuture[]::new);
        countDownLatch.countDown();
        CompletableFuture.allOf(completableFutures).join();
    }
}
