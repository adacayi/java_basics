package com.sanver.basics.stream_api;

import java.util.stream.IntStream;

public class ConvertingPrimitiveStreamsToObjectStreams {

	public static void main(String[] args) {
		IntStream.range(0, 10).mapToObj(x -> x + " element").forEach(System.out::println);
	}
}
