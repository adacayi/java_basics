package com.sanver.basics.streamapi;

import java.util.Arrays;

public class IfPresentSample {

	public static void main(String[] args) {
		Arrays.stream(new int[] { 3, 2, 0, -1, 5 }).filter(x -> x < 2).findFirst()
				.ifPresent(x -> System.out.println("First element smaller than 2 in the list is " + x));
	}
}
