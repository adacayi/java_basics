package com.sanver.basics.arrays;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;

public class SortingPerformance {

    public static final int SIZE = 1_000_000;

    public static void main(String... args) {
        // This code is to show giving lambda expressions for Comparator is faster.
        // Also it shows parallel sort might be faster for large arrays.
        Comparator<Long> longComparator = Comparator.naturalOrder();
        long[][] originalArray = new long[SIZE][], array;
        for (int i = 0; i < SIZE; i++)
            originalArray[i] = new long[]{(long) (Integer.MAX_VALUE * Math.sin(i)), 0};

        array = Arrays.copyOf(originalArray, originalArray.length);
        Comparator<long[]> comparing = Comparator.comparing(a -> a[0]); // This generates a comparator that uses the lambda expression as the key extractor for long[] objects (in our case long[2] where the first element is generated with math.sin() and the second element is 0), and sorts the array of those long[] by sorting the keys in natural order (i.e. with the compare method of the key object as ascending)
        LocalTime startTime = LocalTime.now();
        Arrays.sort(array, comparing);
        System.out.printf("Duration for comparing: %s\n", Duration.between(startTime, LocalTime.now()).toMillis());

        array = Arrays.copyOf(originalArray, originalArray.length);
        startTime = LocalTime.now();
        Arrays.sort(array, (x, y) -> longComparator.compare(x[0], y[0]));
        System.out.printf("Duration for natural order: %s\n", Duration.between(startTime, LocalTime.now()).toMillis());

        array = Arrays.copyOf(originalArray, originalArray.length);
        startTime = LocalTime.now();
        Arrays.sort(array, (x, y) -> Long.compare(x[0], y[0]));
        System.out.printf("Duration for Long.compare: %s\n", Duration.between(startTime, LocalTime.now()).toMillis());

        array = Arrays.copyOf(originalArray, originalArray.length);
        Comparator<long[]> comparingLong = Comparator.comparingLong(x -> x[0]);
        startTime = LocalTime.now();
        Arrays.sort(array, comparingLong);
        System.out.printf("Duration for comparingLong: %s\n", Duration.between(startTime, LocalTime.now()).toMillis());

        array = Arrays.copyOf(originalArray, originalArray.length);
        startTime = LocalTime.now();
        Arrays.sort(array, (x, y) -> {
            long a = x[0], b = y[0];

            if (a < b) {
                return -1;
            }

            if (a > b) {
                return 1;
            }

            return 0;
        });
        System.out.printf("Duration for lambda: %s\n", Duration.between(startTime, LocalTime.now()).toMillis());

        array = Arrays.copyOf(originalArray, originalArray.length);
        startTime = LocalTime.now();
        Arrays.parallelSort(array, (x, y) -> {
            long a = x[0], b = y[0];

            if (a < b)
                return -1;

            if (a > b)
                return 1;

            return 0;
        });
        System.out.printf("Duration for lambda with parallel sort: %s\n", Duration.between(startTime, LocalTime.now()).toMillis());

        array = Arrays.copyOf(originalArray, originalArray.length);
        startTime = LocalTime.now();
        Arrays.parallelSort(array, (x, y) -> Long.compare(x[0], y[0]));
        System.out.printf("Duration for Long.compare with parallel sort: %s\n", Duration.between(startTime, LocalTime.now()).toMillis());

        array = Arrays.copyOf(originalArray, originalArray.length);
        startTime = LocalTime.now();
        Arrays.parallelSort(array, Comparator.comparingLong(x -> x[0]));
        System.out.printf("Duration for comparingLong with parallel sort: %s\n", Duration.between(startTime, LocalTime.now()).toMillis());
    }
}
