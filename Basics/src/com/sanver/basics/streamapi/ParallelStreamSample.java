package com.sanver.basics.streamapi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamSample {

	public static void main(String[] args) {
		int personCount = 10;
		List<Integer> idList = IntStream.range(0, personCount).boxed().collect(Collectors.toList());
		List<Integer> newList = new ArrayList<>();
		idList = idList.parallelStream().collect(Collectors.toList());// The order does not change here for efficiency I
																		// think.
		System.out.println(idList);
		idList = idList.parallelStream().peek(x -> System.out.print(x + " ")).collect(Collectors.toList());// Order
																											// changes
																											// here,
																											// because a
																											// print
																											// method is
		// called, which optimization might not guess
		// its runtime. However collect results in an orderer list.
		System.out.println();
		System.out.println(idList);
		idList.parallelStream().forEach(x -> newList.add(x));
		System.out.println(newList);
	}
}
