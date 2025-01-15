package com.sanver.basics.threads.executors;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;
import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * This class is to demonstrate how pool size increases for different executors when 4 tasks are submitted sequentially, waiting for the previous one to finish first.
 * Check pool size and how threads are assigned to tasks by checking thread ids for each task.
 */
public class PoolSizeIncrease {
    public static void main(String[] args) {
        cachedThreadPool(); // Pool size not increased after the first task submission and same thread is used for all sequentially submitted tasks.
        fixedThreadPool(); // Pool size increased and new threads are used after the first task until reaching the core pool size for sequentially submitted tasks. After the core pool size is reached, existing threads are used.
        forkJoinPool(); // Pool size not increased after the first task submission and same thread is used for all sequentially submitted tasks.
        scheduledExecutor(); // Pool size increased but existing threads are used after the first task until reaching the core pool size for sequentially submitted tasks. After the core pool size is reached, existing threads are used.
        threadPoolTaskExecutor(); // Pool size increased and new threads are used after the first task until reaching the core pool size for sequentially submitted tasks. After the core pool size is reached, existing threads are used.
    }

    private static void cachedThreadPool() {
        try (var executor = Executors.newCachedThreadPool()) {
            submitTasks(executor, "Cached Thread Pool");
        }
    }

    private static void fixedThreadPool() {
        try (var executor = Executors.newFixedThreadPool(2)) {
            submitTasks(executor, "Fixed Thread Pool with size 2");
        }
    }

    private static void forkJoinPool() {
        submitTasks(ForkJoinPool.commonPool(), "ForkJoinPool.commonPool()");
    }

    private static void scheduledExecutor() {
        try (var executor = Executors.newScheduledThreadPool(2)) {
            var poolType = "Scheduled thread pool with size 2";
            printThreadPool(executor, "Initial pool state for " + poolType);
            sleep(7_000);
            IntStream.rangeClosed(1, 4).forEach(i ->
                    uncheck(() -> {
                        executor.schedule(() -> {
                            System.out.printf("Task %d  started. %s%n", i, getThreadInfo());
                            sleep(3_000);
                            System.out.printf("Task %d finished. %s%n", i, getThreadInfo());
                        }, 400, TimeUnit.MILLISECONDS).get();
                        sleep(3_000);
                        printThreadPool(executor, "%n%s state after task %d finished".formatted(poolType, i));
                        sleep(7_000);
                    }));
        }
    }
    private static void threadPoolTaskExecutor(){
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(0);
        executor.initialize();
        submitTasks(executor.getThreadPoolExecutor(), "ThreadPoolTaskExecutor with corePoolSize 2, queueCapacity 0 and maxPoolSize 4");
        executor.shutdown();
    }

    private static void submitTasks(ExecutorService executor, String poolType) {
        printThreadPool(executor, "Initial pool state for " + poolType);
        sleep(7_000);
        IntStream.rangeClosed(1, 4).forEach(i ->
                uncheck(() -> {
                    executor.submit(() -> {
                        System.out.printf("Task %d  started. %s%n", i, getThreadInfo());
                        sleep(3_000);
                        System.out.printf("Task %d finished. %s%n", i, getThreadInfo());
                    }).get();
                    sleep(3_000);
                    printThreadPool(executor, "%n%s state after task %d finished".formatted(poolType, i));
                    sleep(7_000);
                }));
    }
}
