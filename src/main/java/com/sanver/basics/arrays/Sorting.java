package com.sanver.basics.arrays;

import com.sanver.basics.utils.PerformanceComparer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import static com.sanver.basics.utils.ArrayUtils.printArray;

public class Sorting {

    public static void main(String[] args) {
        double[] array = {1.3, 3.2, 2.5};
        Arrays.sort(array);
        printArray(array);
        // To sort in descending order
        // We had to box double to Double because DoubleStream does not have a sorted
        // method that takes a Comparator.
        // To map DoubleStream to Stream<Double> we can use boxed method of DoubleStream
        array = Arrays.stream(array).boxed().sorted(Comparator.reverseOrder())
                .mapToDouble(p -> p).toArray();
        printArray(array);

        // Parallel sorting and its performance advantage in large arrays
        var data1 = new double[1_000_000];
        Arrays.parallelSetAll(data1, x -> Math.random());
        var data2 = Arrays.copyOf(data1, data1.length);
        var data3 = Arrays.copyOf(data1, data1.length);
        var data4 = Arrays.copyOf(data1, data1.length);
        var performanceComparer = new PerformanceComparer(Map.of(
                () -> Arrays.sort(data1), "Arrays.sort",
                () -> Arrays.parallelSort(data2), "Arrays.parallelSort"
        ));
        System.out.println(Arrays.equals(data1, data3));
        performanceComparer.compare();
        System.out.println(Arrays.equals(data1, data3));
        // With same data you can see that after the first run sorting works much faster and the performance advantage of parallelSort is much obvious
        new PerformanceComparer(Map.of(
                () -> Arrays.sort(data3), "Arrays.sort",
                () -> Arrays.parallelSort(data4), "Arrays.parallelSort"
        )).compare();
        System.out.println(Arrays.equals(data1, data3));
    }
}
