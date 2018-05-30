package com.sanver.basics.streamapi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupingSample {

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
		GroupingSample sample = new GroupingSample();

		Map<Boolean, List<Person>> people = IntStream.rangeClosed(1, personCount).mapToObj(x -> {
			Person p = sample.new Person();
			p.id = x;
			p.isMale = x % 2 == 0;
			p.name = (p.isMale ? "Abdullah" : "Hatice");
			return p;
		}).collect(Collectors.groupingBy(p -> p.isMale));

		System.out.println("Genders:\n");
		people.keySet().forEach(gender -> System.out.println(gender ? "Male" : "Female"));
		System.out.println("\nDetailed Info:\n");
		people.forEach((k, v) -> {
			System.out.println(
					(k ? "Male: " : "Female: ") + v.stream().map(Person::toString).collect(Collectors.joining(", ")));
		});
	}
}
