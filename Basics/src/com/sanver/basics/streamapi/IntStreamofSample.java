package com.sanver.basics.streamapi;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntStreamofSample {

	public static void main(String[] args) {
		Supplier<IntStream> sup = () -> IntStream.of(3, 2, 1, 5);
		System.out.println("Stream is " + sup.get().mapToObj(Integer::toString).collect(Collectors.joining(", ")));
		System.out.println("Count is " + sup.get().count());
		System.out.println("Sum is " + sup.get().sum());
		System.out.println("Average is " + sup.get().average().getAsDouble());
		System.out.println("Min is " + sup.get().min().getAsInt());
		System.out.println("Max is " + sup.get().max().getAsInt());
	}
}
