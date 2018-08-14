package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BinarySearch {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(1, 10, 5));
		Collections.sort(list);// We have to sort the list for binary search to work correctly.
		System.out.println("List: " + list);
		System.out.printf("Result for search for 5: %d\n", Collections.binarySearch(list, 5));
		System.out.printf("Result for search for 3: %d Its complement: %d\n", Collections.binarySearch(list, 3),
				~Collections.binarySearch(list, 3));
		System.out.printf("Result for search for 0: %d Its complement: %d\n", Collections.binarySearch(list, 0),
				~Collections.binarySearch(list, 0));
		System.out.printf("Result for search for 11: %d Its complement: %d\n", Collections.binarySearch(list, 11),
				~Collections.binarySearch(list, 11));
	}

}
