package com.sanver.basics.streamapi;

import java.util.stream.Stream;

public class ParallelSortingAndForEach {

	public static void main(String[] args) {
		System.out.println("Parallel stream sorting and then using forEach: ");
		Stream.of(3, 2, 1, 5, 6).parallel().sorted().forEach(x -> System.out.print(x + " "));
		System.out.println("\n\nWith forEachOrdered: ");
		Stream.of(3, 2, 1, 5, 6).parallel().sorted().forEachOrdered(x -> System.out.print(x + " "));
		System.out.println("\n\nWith sequential stream and forEach: ");
		Stream.of(3, 2, 1, 5, 6).parallel().sorted().sequential().forEach(x -> System.out.print(x + " "));
	}
}
