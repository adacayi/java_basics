package com.sanver.basics.stream_api;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.stream.IntStream;

public class GeneratingStreamsWithIterate {

	public static void main(String[] args) {
		IntStream oddNumbers = IntStream.iterate(1, x -> {
			sleep(1000);
			return x + 2;
		}).skip(4).limit(5);

		oddNumbers.forEach(System.out::println);
	}
}
