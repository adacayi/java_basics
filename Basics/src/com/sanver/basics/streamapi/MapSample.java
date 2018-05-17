package com.sanver.basics.streamapi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapSample {

	static class Person {
		int id;
		String name;

		public String toString() {
			return id + " " + name;
		}
	}

	public static void main(String[] args) {
		int personCount = 5;

		List<Integer> idList = IntStream.rangeClosed(1, personCount).mapToObj(x -> new Integer(x))
				.collect(Collectors.toList());

		List<Person> people = idList.stream().map(x -> {
			Person p = new Person();
			p.id = x;
			p.name = "Ahmet_" + x;
			return p;
		}).collect(Collectors.toList());

		people.forEach(System.out::println);
	}
}
