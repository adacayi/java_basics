package com.sanver.basics.collections;

import java.util.Collections;
import java.util.Comparator;
//import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortSample {

	public static void main(String[] args) {
		List<Integer> values = new ArrayList<Integer>(Arrays.asList(4, 2, 3, 5));
		Comparator<Integer> ascending = Comparator.comparing(p -> p);
		Comparator<Integer> descending = ascending.reversed();
		values.sort(ascending);
		System.out.println(values);
		Collections.sort(values); // This can also be used for sorting
		// Collections.reverse(values) should not be used to sort in descending order.
		// Instead comparators should be used.
		values.sort(descending);
		System.out.println(values);
		// This is another alternative
		Collections.sort(values, descending);
		System.out.println(values);
	}

}
