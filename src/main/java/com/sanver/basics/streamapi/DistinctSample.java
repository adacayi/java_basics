package com.sanver.basics.streamapi;

import java.util.Arrays;

public class DistinctSample {

	public static void main(String[] args) {
		int[] numbers = { 3, 2, 5, 6, 3, 3 };
		System.out.println(Arrays.toString(Arrays.stream(numbers).distinct().toArray()));
	}

}
