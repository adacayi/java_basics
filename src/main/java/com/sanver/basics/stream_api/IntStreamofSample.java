package com.sanver.basics.stream_api;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntStreamofSample {

	public static void main(String[] args) {
		Supplier<IntStream> sup = () -> IntStream.of(3, 2, 1, 5);
		System.out.println("Stream is " + sup.get().mapToObj(Integer::toString).collect(Collectors.joining(", ")));
		System.out.println("Count is " + sup.get().count());
		System.out.println("Sum is " + sup.get().sum());
		System.out.println("Average is " + sup.get().average().orElse(-1));
		System.out.println("Min is " + sup.get().min().orElse(-1));
		System.out.println("Max is " + sup.get().max().orElse(-1));
	}
}
