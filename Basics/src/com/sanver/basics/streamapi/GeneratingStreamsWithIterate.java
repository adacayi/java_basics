package com.sanver.basics.streamapi;

import java.util.stream.IntStream;

public class GeneratingStreamsWithIterate {

	public static void main(String[] args) {
		IntStream oddNumbers = IntStream.iterate(1, x -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return x + 2;
		}).skip(4).limit(5);

		oddNumbers.forEach(System.out::println);
	}
}
