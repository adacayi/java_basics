package com.sanver.basics.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WildCardSample {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>(3);
		list.add("Ahmet");
		list.add("Mustafa");
		list.add("Salih");
		collectionWriter(list);
		collectionWriterWithType(list);
		System.out.println(list);
	}

	public static void collectionWriter(Collection<?> collection) {
		for (Object item : collection)
			System.out.println(item);
	}
	
	public static <T> void collectionWriterWithType(Collection<T> collection) {
		for (T item : collection)
			System.out.println(item);
	}
}
