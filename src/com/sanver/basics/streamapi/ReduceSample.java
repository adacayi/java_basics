package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ReduceSample {

	public static class Person {
		String name = "";
		int age;

		public String toString() {
			return name + " at age " + age;
		}
	}

	public static void main(String[] args) {
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

		Supplier<Stream<Person>> supplier = () -> Arrays.stream(people);
		Person oldestPerson = supplier.get().reduce((p1, p2) -> p1.age > p2.age ? p1 : p2).get();
		System.out.println("Oldest person is " + oldestPerson);
		Person combinedNamesAndAges = supplier.get().reduce(new Person(), (initial, current) -> {
			System.out.println("Combining " + initial.name + " with " + current.name);
			initial.name += current.name;
			initial.age += current.age;
			return initial;
		});
		System.out.println("\nCombination result with serial execution: " + combinedNamesAndAges + "\n");
		combinedNamesAndAges = supplier.get().parallel().reduce(new Person(), (initial, current) -> {
			System.out.println("Combining " + initial.name + " with " + current.name);
			initial.name += current.name;
			initial.age += current.age;
			return initial;
		});
		System.out.println("\nCombination resultwith parallel execution: " + combinedNamesAndAges + "\n");
	}
}
