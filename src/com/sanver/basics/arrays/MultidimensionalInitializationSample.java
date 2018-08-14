package com.sanver.basics.arrays;

import java.util.Arrays;

public class MultidimensionalInitializationSample {

	public static void main(String[] args) {
		String[][] friends = { { "Ahmet", "732 111-2222" }, { "Mustafa", "718 111-2222", "44 7343 123 24" } };
		Arrays.stream(friends).forEach(m -> System.out.println(Arrays.toString(m)));
	}
}
