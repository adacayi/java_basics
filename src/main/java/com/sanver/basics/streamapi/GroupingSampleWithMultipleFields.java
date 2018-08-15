package com.sanver.basics.streamapi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupingSampleWithMultipleFields {

	class Person {
		int id;
		String name;
		Boolean isMale;

		public String toString() {
			return id + " " + name;
		}
	}

	public static void main(String[] args) {
		int personCount = 16;
		GroupingSampleWithMultipleFields sample = new GroupingSampleWithMultipleFields();

		Map<String, Map<Boolean, List<Person>>> people = IntStream.rangeClosed(1, personCount).mapToObj(x -> {
			Person p = sample.new Person();
			p.id = x;
			p.isMale = x % 2 == 0;
			p.name = (p.isMale ? "Abdullah" : "Hatice");
			return p;
		}).collect(Collectors.groupingBy(p -> p.name, Collectors.groupingBy(p -> p.isMale)));

		System.out.println("Name gender group:\n");
		people.keySet().forEach(name -> people.get(name).keySet()
				.forEach(gender -> System.out.println(name + " " + (gender ? "Male" : "Female"))));
		System.out.println("\nDetailed Info:\n");
		people.keySet().forEach(name -> people.get(name).keySet().forEach(gender -> {
			System.out.print(name + " " + (gender ? "Male: " : "Female: "));
			people.get(name).get(gender).forEach(person -> System.out.print(person + ", "));
			System.out.println();
		}));
	}
}
