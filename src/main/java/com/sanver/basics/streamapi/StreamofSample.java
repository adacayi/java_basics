package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.stream.Stream;

public class StreamofSample {

	public static void main(String[] args) {
		String[] names = { "Ahmet", "Mustafa", "Muhammed" };
		int[] numbers = { 4, 3, 5 };
		Stream.of(names).sorted().forEach(System.out::println);
		Stream.of(3, 2, 1, 5).sorted().forEach(System.out::println);

		Stream.of(numbers).sorted().forEach(System.out::println);// Stream.of with primitive arrays returns a one
																	// element stream. That one element is an array
																	// containing primitive values. With primitive
																	// collections it is better to use Arrays.stream()
																	// method, because it returns a primitive stream.
		// This behaviour is the same with the Arrays.asList method which returns a list
		// of one element when the array is a primitive array.

		Arrays.stream(numbers).sorted().forEach(System.out::println);
	}
}
