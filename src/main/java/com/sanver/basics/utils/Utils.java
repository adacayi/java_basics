package com.sanver.basics.utils;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.LambdaExceptionUtil.uncheck;

public class Utils {
    private static final Random random = new Random();

    private Utils() {
    }

    public static void sleep(long millis) {
        uncheck(() -> Thread.sleep(millis));
    }

    public static void sleepNano(long nanos) {
        var start = System.nanoTime();
        while (System.nanoTime() - start < nanos);
    }

    public static String getThreadInfo() {
        var thread = Thread.currentThread();
        return String.format("Thread id: %-3d Thread name: %s Is Daemon: %s", thread.getId(), thread.getName(), thread.isDaemon());
    }

    public static void printCurrentThread(String... info) {
        displayInfo(info);
        System.out.println(getThreadInfo());
    }

    public static void printForkJoinPool(ForkJoinPool pool, String... info) {
        displayInfo(info);

        System.out.printf("Active threads in the fork join pool: %d%n", pool.getActiveThreadCount());
        System.out.printf("The number of threads in the fork join pool: %d%n%n", pool.getPoolSize());
    }

    public static void printThreadPool(ThreadPoolTaskExecutor threadPool, String... info) {
        displayInfo(info);

        System.out.printf("Active threads in the pool: %d%n", threadPool.getActiveCount());
        System.out.printf("The number of threads in the pool: %d%n%n", threadPool.getPoolSize());
    }

    public static void printThreadPool(ThreadPoolExecutor threadPool, String... info) {
        displayInfo(info);

        System.out.printf("Active threads in the pool: %d%n", threadPool.getActiveCount());
        System.out.printf("The number of threads in the pool: %d%n%n", threadPool.getPoolSize());
    }

    private static void displayInfo(String... info) {
        System.out.println();
        for (var infoItem : info) {
            System.out.println(infoItem);
        }
        var max = Arrays.stream(info).map(String::length).mapToInt(x -> x).max();

        if (max.isPresent()) {
            System.out.println(IntStream.range(0, max.getAsInt()).mapToObj(x -> "-").collect(Collectors.joining()));
        }
    }

    public static int getRandomInt(int a, int b) {
        return random.nextInt(a, b + 1);
    }
}
