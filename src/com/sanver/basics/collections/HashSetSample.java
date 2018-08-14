package com.sanver.basics.collections;

import java.util.HashSet;

public class HashSetSample {

	public static void main(String[] args) {
		HashSet<String> firstSet = new HashSet<>();
		HashSet<String> secondSet = new HashSet<>();
		HashSet<String> union;
		HashSet<String> intersection;

		firstSet.add("Ahmet");
		firstSet.add("Muhammed");
		secondSet.add("Mustafa");
		secondSet.add("Muhammed");
		union = new HashSet<>(firstSet);
		union.addAll(secondSet);
		intersection = new HashSet<>(firstSet);
		intersection.retainAll(secondSet);

		System.out.println(firstSet.contains("Ahmet"));
		System.out.println(String.join(", ", firstSet));
		System.out.println(secondSet);
		System.out.println(union);
		System.out.println(intersection);
	}

}
