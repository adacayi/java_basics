package com.sanver.basics.arrays;

import java.util.Arrays;
import java.util.Comparator;

public class Sorting {

	public static void main(String[] args) {
		double[] array = { 1, 3, 2 };
		Arrays.sort(array);
		System.out.println(Arrays.toString(array));
		// To sort in descending order
		array = Arrays.stream(array).mapToObj(p -> new Double(p))
				.sorted(Comparator.comparing((Double p) -> p).reversed()).mapToDouble(p -> p).toArray();
		// We had to box double to Double because DoubleStream does not have a sorted
		// method that takes a Comparator.
		System.out.println(Arrays.toString(array));
		// To map DoubleStream to Stream<Double> we can use boxed method of DoubleStream
		array = Arrays.stream(array).boxed().sorted(Comparator.comparing((Double p) -> p).reversed())
				.mapToDouble(p -> p).toArray();
		System.out.println(Arrays.toString(array));
	}

}
