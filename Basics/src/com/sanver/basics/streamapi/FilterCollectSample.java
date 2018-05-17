package com.sanver.basics.streamapi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FilterCollectSample {

	public static void main(String[] args) {
		int length = 20;
		List<Integer> list = IntStream.rangeClosed(1,length).boxed().collect(Collectors.toList());
		List<Integer> evenList;
		List<Integer> oddList;

		evenList = list.stream().filter(number -> number % 2 == 0).collect(Collectors.toList());
		oddList = list.stream().filter(number -> number % 2 == 1).collect(Collectors.toList());
		evenList.forEach(item -> System.out.print(item + " "));
		System.out.println();
		oddList.forEach(item -> System.out.print(item + " "));
	}
}
