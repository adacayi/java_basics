package com.sanver.basics.arrays;

import java.util.Arrays;

public class MultidimensionalArrayAssignmentsAndBoxingUnboxing {

	public static void main(String[] args) {
		long[][] l2d = new long[3][4];
		long[] l1 = { 2, 3, 1 };
		Object o = l1;
		l2d[1] = (long[]) o;
		Arrays.stream(l2d).forEach(m -> System.out.println(Arrays.toString(m)));
	}
}
