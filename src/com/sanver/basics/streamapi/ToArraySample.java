package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.stream.Stream;

public class ToArraySample {

	static class Person {
		private String name;
		private int age;

		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return name + " at age " + age;
		}
	}

	public static void main(String[] args) {
		Person[] people = Stream.of(new Person("Ahmet", 1), new Person("Mustafa", 2), new Person("Muhammed", 6))
				.toArray(x -> new Person[3]);// The size of the person array in toArray method must be exactly the same
												// size of the stream, else it will give a runtime error.
		System.out.println(Arrays.toString(people));
	}

}
