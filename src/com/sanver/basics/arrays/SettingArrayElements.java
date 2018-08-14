package com.sanver.basics.arrays;

import java.util.Arrays;

public class SettingArrayElements {

	public static void main(String[] args) {
		int[] numbers = new int[10];
		Arrays.parallelSetAll(numbers, x -> 2 * x);
		System.out.println(Arrays.toString(numbers));
	}

}
