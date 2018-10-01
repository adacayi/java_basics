package com.sanver.basics.arrays;

import java.util.Arrays;
import java.util.Comparator;

public class Sorting {

    public static void main(String[] args) {
        double[] array = {1.3, 3.2, 2.5};
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));
        // To sort in descending order
        // We had to box double to Double because DoubleStream does not have a sorted
        // method that takes a Comparator.
        // To map DoubleStream to Stream<Double> we can use boxed method of DoubleStream
        array = Arrays.stream(array).boxed().sorted(Comparator.<Double>naturalOrder().reversed())
                .mapToDouble(p -> p).toArray();
        System.out.println(Arrays.toString(array));
    }
}
