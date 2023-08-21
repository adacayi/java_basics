package com.sanver.basics.streamapi;

import java.util.Arrays;

public class AnyMatchSample {

	public static void main(String[] args) {
		int[] numbers = { 1, 2, 3, 4, 5 };
		boolean result = Arrays.stream(numbers).anyMatch(x -> {
			System.out.println("Checking if " + x + " satisfies condition");
			return x > 2;
		});

		System.out.println(result);
	}
}
