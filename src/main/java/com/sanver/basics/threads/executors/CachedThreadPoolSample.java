package com.sanver.basics.threads.executors;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

public class CachedThreadPoolSample {
    /*
    The cached pool starts with zero threads and can potentially grow to have Integer.MAX_VALUE threads.
    Practically, the only limitation for a cached thread pool is the available system resources.
    To better manage system resources, cached thread pools will remove threads that remain idle for one minute.
     */
    public static void main(String[] args) {
        var threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool(); // Note, we need to cast ExecutorService to ThreadPoolExecutor to be able to use the getPoolSize() and getActiveCount() methods.
        print(threadPool, "Initial thread pool information");
        var latch = new CountDownLatch(1);
        var taskCount = 5;
        for (int i = 0; i < taskCount; i++) {
            threadPool.execute(() -> {
                try {
                    System.out.println("Thread " + Thread.currentThread().getName() + " is running");
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        print(threadPool,String.format("Thread pool after %d tasks have been submitted", taskCount));
        latch.countDown();
        sleep(100);
        print(threadPool, String.format("Thread pool after %d tasks have been executed", taskCount));
        sleep(60_000);
        print(threadPool, "Thread pool after 1 minute of idle time");
    }

    private static void print(ThreadPoolExecutor threadPool, String... info) {
        System.out.println();
        for (var infoItem : info) {
            System.out.println(infoItem);
        }
        var max = Arrays.stream(info).map(x -> x.length()).mapToInt(x -> x).max().getAsInt();
        System.out.println(IntStream.range(0, max).mapToObj(x -> "-").collect(Collectors.joining()));

        System.out.printf("Active threads in the pool: %d%n", threadPool.getActiveCount());
        System.out.printf("The number of threads in the pool: %d%n%n", threadPool.getPoolSize());
    }
}
