package com.sanver.basics.streamapi;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindAnyFindFirstSample {

	public static void main(String[] args) {
		Supplier<IntStream> getStream = () -> IntStream.of(3, 2, 1, 5);
		System.out
				.println("Stream is " + getStream.get().mapToObj(Integer::toString).collect(Collectors.joining(", ")));
		System.out.println("findFirst result: " + getStream.get().parallel().findFirst().orElse(-1));// We used parallel
																										// stream, else
																										// the results
																										// would be
																										// same.
		System.out.println("findAny result: " + getStream.get().parallel().findAny().orElse(-1));
	}

}
