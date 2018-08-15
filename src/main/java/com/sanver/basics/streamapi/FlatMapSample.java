package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FlatMapSample {

	public static class Country {
		String name;
		List<String> cities;

		public Country(String name, String... cities) {
			this.name = name;
			this.cities = Arrays.stream(cities).collect(Collectors.toList());
		}
	}

	public static void main(String[] args) {
		Country[] countries = { new Country("Turkiye", "Istanbul", "Konya", "Urfa"),
				new Country("England", "London", "Cambridge", "Kent") };
		countries[0].cities.add("Isparta");
		Arrays.stream(countries).map(c -> c.cities).forEach(System.out::println);
		Arrays.stream(countries).flatMap(c -> c.cities.stream()).forEach(System.out::println);
	}
}
