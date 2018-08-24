package com.sanver.basics.stream_api;

import java.util.stream.IntStream;

public class IfPresentSample {

	public static void main(String[] args) {
		IntStream.of(3, 2, 1, -1, 5).filter(x -> x < 2).findFirst()
				.ifPresent(x -> System.out.println("First element smaller than 2 in the list is " + x));
	}
}
