package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class AnyMatchSample {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6};
        boolean result = Arrays.stream(numbers).filter(x -> {
            System.out.println("Filtering " + x);
            return x % 2 == 0;
        }).anyMatch(x -> {
            System.out.println("AnyMatch " + x);
            return x > 2;
        });
        System.out.printf("%s%n%n", result); // AnyMatch terminates when a match is found.
        // Note that any match doesn't wait all filtering to be done first.

        result = Arrays.stream(numbers).anyMatch(x -> {
            System.out.println("Checking if " + x + " satisfies condition x > 2");
            return x > 2;
        });

        System.out.printf("%s%n%n", result);

        numbers = new int[100];
        AtomicInteger count = new AtomicInteger(0);
        Arrays.parallelSetAll(numbers, x -> x);
        result = Arrays.stream(numbers).parallel().anyMatch(x -> {
            System.out.println("Checking if " + x + " satisfies condition x > 10");
            count.incrementAndGet();
            return x > 10;
        });
        System.out.printf("Finding if any item exists in the int array from 0 to 99, that has an item > 10. Result: %s. Check count with parallel stream: %d%n", result, count.get());
    }
}
