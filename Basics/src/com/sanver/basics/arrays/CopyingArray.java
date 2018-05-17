package com.sanver.basics.arrays;

import java.util.Arrays;

public class CopyingArray {

	public static void main(String[] args) {
		int[] a, b = { 3, 2, 1 };
		a = Arrays.copyOf(b, 5);
		b[1] = 4;
		System.out.println(Arrays.toString(a) + " ");
		System.out.println(Arrays.toString(b) + " ");
	}
}
