package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class NoneMatchSample {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5};
        boolean result = Arrays.stream(numbers).noneMatch(x -> {
            System.out.println("Checking if " + x + " satisfies condition x > 2");
            return x > 2;
        });

        System.out.println(result);

        numbers = new int[100];
        AtomicInteger count = new AtomicInteger(0);
        Arrays.parallelSetAll(numbers, x -> x);
        result = Arrays.stream(numbers).parallel().noneMatch(x -> {
            System.out.println("Checking if " + x + " satisfies condition x > 10");
            count.incrementAndGet();
            return x > 10;
        });
        System.out.printf("Finding if no items in the int array from 0 to 99 are greater than 10. Result: %s. Check count with parallel stream: %d%n", result, count.get());
    }
}
