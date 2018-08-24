package com.sanver.basics.stream_api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BoxedSample {

	public static void main(String[] args) {
		int[] numbers = { 3, 4, 2, 5 };
		// Arrays.stream(numbers).collect(Collectors.toList()); This won't compile
		// because we have an IntStream and we cannot generate a primitive List.
		List<Integer> list = Arrays.stream(numbers).boxed().collect(Collectors.toList());
		list.add(8);
		System.out.println(list);
	}

}
