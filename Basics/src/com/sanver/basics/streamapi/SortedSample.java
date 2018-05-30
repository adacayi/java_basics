package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.Comparator;

public class SortedSample {

	public static class Person {
		String name;
		int age;
	}

	public static void main(String[] args) {
		// We used a Person class since primitive streams do not have sorted method with
		// Comparator parameter. There is only a parameterless sorted method in
		// primitive streams.
		Person[] people = { new Person() {
			{
				name = "Ahmet";
				age = 3;
			}
		}, new Person() {
			{
				name = "Mustafa";
				age = 2;
			}
		}, new Person() {
			{
				name = "Muhammed";
				age = 1;
			}
		} };

		System.out.println("Note that all sorting is done before moving to anyMatch method");
		Comparator<Person> ageComparor = Comparator.comparing(p -> p.age);

		boolean result = Arrays.stream(people).sorted((x, y) -> {
			System.out.println("Comparing " + x.age + " with " + y.age);
			return ageComparor.compare(x, y);
		}).anyMatch(x -> {
			System.out.println("Checking if " + x.name + " satisfies condition");
			return x.age > 2;
		});

		System.out.println(result);
	}
}
