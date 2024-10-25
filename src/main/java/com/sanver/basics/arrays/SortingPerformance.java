package com.sanver.basics.arrays;

import com.sanver.basics.utils.PerformanceComparer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import static com.sanver.basics.utils.ArrayUtils.arrayDeepCopyOf;

public class SortingPerformance {

    public static final int SIZE = 10_000_000;

    public static void main(String... args) {
        // This code is to show giving lambda expressions for Comparator is faster.
        // Also, it shows parallel sort might be faster for large arrays.
        long[][] originalArray = new long[SIZE][];
        for (int i = 0; i < SIZE; i++) {
            originalArray[i] = new long[]{(long) (Integer.MAX_VALUE * Math.sin(i)), 0};
        }

        Comparator<long[]> comparing = Comparator.comparing(a -> a[0]); // This generates a comparator that uses the lambda expression as the key extractor for long[] objects (in our case long[2] where the first element is generated with math.sin() and the second element is 0), and sorts the array of those long[] by sorting the keys in natural order (i.e. with the compare method of the key object as ascending)
        var naturalOrderComparator = Comparator.<Long>naturalOrder();
        Comparator<long[]> comparingLong = Comparator.comparingLong(x -> x[0]);
        Comparator<long[]> customComparer = (x, y) -> {
            long a = x[0];
            long b = y[0];

            if (a < b) {
                return -1;
            }
            if (a > b) {
                return 1;
            }
            return 0;
        };

        new PerformanceComparer(
                Map.of(
                        () -> Arrays.sort(arrayDeepCopyOf(originalArray), comparing), "Comparator.comparing(a -> a[0])",
                        () -> Arrays.sort(arrayDeepCopyOf(originalArray), (x, y) -> naturalOrderComparator.compare(x[0], y[0])), "Comparator.<Long>naturalOrder().compare()",
                        () -> Arrays.sort(arrayDeepCopyOf(originalArray), (x, y) -> Long.compare(x[0], y[0])), "Long.compare()",
                        () -> Arrays.sort(arrayDeepCopyOf(originalArray), comparingLong), "Comparator.comparingLong(x -> x[0])",
                        () -> Arrays.sort(arrayDeepCopyOf(originalArray), customComparer), "customComparer",
                        () -> Arrays.parallelSort(arrayDeepCopyOf(originalArray), (x, y) -> Long.compare(x[0], y[0])), "Long.compare() and parallelSort"
                )
        ).compare();
    }
}
