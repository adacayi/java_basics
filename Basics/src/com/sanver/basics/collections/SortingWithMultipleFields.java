package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortingWithMultipleFields {

	static class Person {
		String name;
		String country;
		int age;

		public Person(String name, String country, int age) {
			this.name = name;
			this.age = age;
			this.country = country;
		}

		public String toString() {
			return name + "  " + country + "  " + age;
		}
	}

	public static void main(String[] args) {
		List<Person> people = new ArrayList<Person>();
		people.addAll(Arrays.asList(new Person[] { new Person("Ahmet", "Turkiye", 1), new Person("Ahmet", "England", 3),
				new Person("Ahmet", "England", 2), new Person("Mustafa", "England", 2),
				new Person("Mustafa", "Turkiye", 5), new Person("Mustafa", "Turkiye", 7) }));
		// To sort with name descending, country ascending and age descending:
		Comparator<Person> nameDescending = Comparator.comparing((Person p) -> p.name).reversed();
		Comparator<Person> ageDescending = Comparator.comparing((Person p) -> p.age).reversed();
		people.sort(nameDescending.thenComparing(p -> p.country).thenComparing(ageDescending));
		String content=people.stream().map(p -> p.toString()).collect(Collectors.joining("\n"));
		System.out.println(content);
	}
}
