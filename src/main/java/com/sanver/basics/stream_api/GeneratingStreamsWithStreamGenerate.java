package com.sanver.basics.stream_api;

import java.time.LocalDateTime;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class GeneratingStreamsWithStreamGenerate {

	public static void main(String[] args) {
		Supplier<LocalDateTime> supplier = () -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return LocalDateTime.now();
		};
		Stream<LocalDateTime> stream = Stream.generate(supplier).limit(5);

		stream.forEach(System.out::println);
	}

}
