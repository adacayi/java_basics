package com.sanver.basics.streamapi;

import java.util.Arrays;

public class ConvertingObjectStreamsToPrimitiveStreamsSample {

	public static void main(String[] args) {
		String[] numbers = { "3", "1", "2", "5" };
		Arrays.stream(numbers).mapToInt(Integer::parseInt).average().ifPresent(x -> System.out.println("Average is " + x));
	}

}
