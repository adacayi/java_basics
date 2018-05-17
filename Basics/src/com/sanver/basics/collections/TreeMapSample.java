package com.sanver.basics.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapSample {

	static class Person implements Comparable<Person> {
		int id;
		String name;
		Boolean isMale;
		String country;
		String city;

		public String gender() {
			return isMale ? "Male" : "Female";
		}

		@Override
		public String toString() {
			return name + " " + country + " " + city;
		}

		@Override
		public int compareTo(Person o2) {
			Person o1 = this;

			if (o1.country == null || o2.country == null) {
				if (o1.country != null)
					return 1;
				else if (o2.country != null)
					return -1;
			} else if (!o1.country.equals(o2.country))
				return o1.country.compareTo(o2.country);

			if (o1.city == null || o2.city == null) {
				if (o1.city != null)
					return 1;
				else if (o2.city != null)
					return -1;
			} else if (!o1.city.equals(o2.city))
				return o1.city.compareTo(o2.city);

			if (o1.name == null || o2.name == null) {
				if (o1.name != null)
					return 1;
				else if (o2.name != null)
					return -1;
			} else if (!o1.name.equals(o2.name))
				return o1.name.compareTo(o2.name);

			return o1.gender().compareTo(o2.gender());
		}
	}

	public static void main(String[] args) {
		Map<Person, Integer> hashMap = new HashMap<>();
		hashMap.put(new Person() {
			{
				name = "Ahmet";
				country = "Turkiye";
				city = "Konya";
				isMale = true;
			}
		}, 1);
		hashMap.put(new Person() {
			{
				name = "Mustafa";
				country = "England";
				city = "London";
				isMale = true;
			}
		}, 2);
		hashMap.put(new Person() {
			{
				name = "Zeynep";
				country = "Turkiye";
				city = "Istanbul";
				isMale = false;
			}
		}, 3);

		Map<Person, Integer> treeMap = new TreeMap<>(hashMap);
		Set<Person> treeKeys = treeMap.keySet();
		Set<Person> hashMapKeys = hashMap.keySet();
		System.out.println("HashMap output:\n");
		hashMapKeys.forEach(key -> System.out.println(hashMap.get(key) + " " + key));
		System.out.println();
		System.out.println("Tree output:\n");
		treeKeys.forEach(key -> System.out.println(treeMap.get(key) + " " + key));
	}
}
