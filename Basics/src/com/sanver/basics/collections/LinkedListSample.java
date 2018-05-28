package com.sanver.basics.collections;

import java.util.ListIterator;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LinkedListSample {

	public static void main(String[] args) {
		List<String> list = new LinkedList<>(Arrays.asList("Ahmet","Mustafa","Muhammed"));
		list.add("Ibrahim");
		list.forEach(System.out::println);

		System.out.println();

		ListIterator<String> iterator = list.listIterator(list.size());

		while (iterator.hasPrevious())
			System.out.println(iterator.previous());

		System.out.println();

		for (int i = 0; i < list.size(); i++)
			System.out.println(list.get(i));
	}
}
