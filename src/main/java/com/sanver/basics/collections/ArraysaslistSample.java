package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArraysaslistSample {

	public static void main(String[] args) {
		// Quick initialisation of an ArrayList
		List<Integer> numbers = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6));
		numbers.add(7);
		System.out.println(numbers);
	}

}
