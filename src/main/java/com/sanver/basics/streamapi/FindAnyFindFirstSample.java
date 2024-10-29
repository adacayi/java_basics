package com.sanver.basics.streamapi;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FindAnyFindFirstSample {

	public static void main(String[] args) {
		Supplier<IntStream> getStream = () -> IntStream.of(3, 2, 1, 5);
		System.out
				.println("Stream is " + getStream.get().mapToObj(Integer::toString).collect(Collectors.joining(", ")));
		System.out.println("findFirst result: " + getStream.get().parallel().findFirst().orElse(-1)); // If the stream has order, findFirst always returns the first element if the stream is not empty.
		System.out.println("findAny result: " + getStream.get().parallel().findAny().orElse(-1)); // findAny returns an OptionalInt describing some element of the stream, or an empty OptionalInt if the stream is empty. The behavior of this operation is explicitly nondeterministic; it is free to select any element in the stream. This is to allow for maximal performance in parallel operations
	}
}
