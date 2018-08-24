package com.sanver.basics.stream_api;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RangeClosedSample {

	class Person {
		int id;
		String name;

		public String toString() {
			return id + " " + name;
		}
	}

	public static void main(String[] args) {
		int personCount = 16;
		RangeClosedSample sample = new RangeClosedSample();

		List<Person> people = IntStream.rangeClosed(1, personCount).mapToObj(x -> {
			Person p = sample.new Person();
			p.id = x;
			p.name = "Abdullah_" + x;
			return p;
		}).collect(Collectors.toList());

		people.forEach(System.out::println);
	}
}
