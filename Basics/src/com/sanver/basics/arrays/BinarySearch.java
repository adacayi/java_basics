package com.sanver.basics.arrays;

import java.util.Arrays;

public class BinarySearch {

	public static void main(String[] args) {
		double[] array = { 1, 10, 5 };
		Arrays.sort(array);// We have to sort the array for binary search to work correctly.
		System.out.println("Array: " + Arrays.toString(array));
		System.out.printf("Result for search for 5: %d\n" , Arrays.binarySearch(array, 5));
		System.out.printf("Result for search for 3: %d Its complement: %d\n", Arrays.binarySearch(array, 3), ~Arrays.binarySearch(array, 3));
		System.out.printf("Result for search for 0: %d Its complement: %d\n", Arrays.binarySearch(array,0), ~Arrays.binarySearch(array, 0));
		System.out.printf("Result for search for 11: %d Its complement: %d\n", Arrays.binarySearch(array, 11), ~Arrays.binarySearch(array, 11));
	}
}
