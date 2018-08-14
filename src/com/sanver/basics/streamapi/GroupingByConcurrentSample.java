package com.sanver.basics.streamapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class GroupingByConcurrentSample {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>(Arrays.asList(new Integer[] { 3, 6, 2, 1 }));
		ConcurrentMap<String, List<Integer>> result = list.parallelStream()
				.collect(Collectors.groupingByConcurrent(i -> i % 2 == 0 ? "Even" : "Odd"));
		result.forEach((k, v) -> {
			System.out.println(k + " " + v.stream().map(x -> x.toString()).collect(Collectors.joining(", ")));
		});
	}
}
