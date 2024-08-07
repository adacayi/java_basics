package com.sanver.basics.utils;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.Random;
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

    public static void printCurrentThread(String... info) {
        displayInfo(info);

        var thread = Thread.currentThread();
        System.out.printf("Thread id: %-3d Thread name: %s%n%n", thread.getId(), thread.getName());
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
