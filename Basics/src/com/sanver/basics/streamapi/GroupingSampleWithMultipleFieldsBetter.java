package com.sanver.basics.streamapi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupingSampleWithMultipleFieldsBetter {

	static Map<String, Boolean> name = new HashMap<>();
	static Map<String, String[]> location = new HashMap<>();
	static {
		name.put("Ahmet", true);
		name.put("Fatima", false);
		name.put("Hatice", false);
		name.put("Abdullah", true);
		location.put("T�rkiye", new String[] { "Istanbul", "Urfa", "Konya" });
		location.put("England", new String[] { "London", "Cambridge", "Kent" });
	}
	static String[] nameArray = name.keySet().toArray(new String[0]);
	static String[] countryArray = location.keySet().toArray(new String[0]);

	static int getRandomInt(int a, int b) {
		return (int) (Math.random() * (b - a + 1) + a);
	}

	static class Tuple<T1, T2> {
		T1 value1;
		T2 value2;

		public Tuple(T1 value1, T2 value2) {
			this.value1 = value1;
			this.value2 = value2;
		}
	}

	static Tuple<String, Boolean> getRandomName() {
		int length = nameArray.length;
		int index = getRandomInt(0, length - 1);
		Tuple<String, Boolean> result = new Tuple<String, Boolean>(nameArray[index], name.get(nameArray[index]));

		return result;
	}

	static Tuple<String, String> getRandomLocation() {
		int length = countryArray.length;
		int index = getRandomInt(0, length - 1);
		String[] cityArray = location.get(countryArray[index]);
		int cityIndex = getRandomInt(0, cityArray.length - 1);
		Tuple<String, String> result = new Tuple<String, String>(countryArray[index], cityArray[cityIndex]);

		return result;
	}

	static class Person implements Comparable<Person> {
		int id;
		String name;
		boolean isMale;
		String country;
		String city;

		public String gender() {
			return isMale ? "Male" : "Female";
		}

		public String toString() {
			return id + " " + name + " " + country + " " + city;
		}

		@Override
		public int compareTo(Person o2) {
			Person o1 = this;
			Comparator<Person> comparator = Comparator.comparing((Person p) -> p.country == null ? "" : p.country)
					.thenComparing(p -> p.city == null ? "" : p.city).thenComparing(p -> p.name == null ? "" : p.name)
					.thenComparing(p -> p.gender());// If we do not check for null and replace for a not null value we
													// get an error in runtime.
			return comparator.compare(o1, o2);
		}
	}

	static class Grouper<T> {

		private List<T> list = new ArrayList<>();
		private List<Field> fields;

		public Grouper(T type) {// This constructor is coded because Grouper class does not know which
			// generic type it has at construction. Hence we provide
			// an object of that type, so it can infer and get its
			// fields while constructing.
			fields = Arrays.asList(type.getClass().getDeclaredFields());
		}

		public T group(T obj) {

			Optional<T> result = list.parallelStream().filter(o -> !fields.parallelStream().anyMatch(field -> {
				Object value1, value2;
				try {
					value1 = field.get(obj);
					value2 = field.get(o);

					if (value1 == null || value2 == null) {
						if (value1 != value2)
							return true;

						return false;
					}

					if (!value1.equals(value2))
						return true;

				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
					return true;
				}
				return false;
			})).findFirst();

			if (result.isPresent())
				return result.get();

			list.add(obj);
			return obj;
		}
	}

	public static void main(String[] args) {
		int personCount = 16;
		Grouper<Person> grouper = new Grouper<>(new Person()); // This is done because Grouper class does not know which
																// generic type it has at construction. Hence we provide
																// an object of that type, so it can infer and get its
																// fields while constructing.
		List<Person> personList = IntStream.rangeClosed(1, personCount).mapToObj(x -> {
			Person p = new Person();
			Tuple<String, Boolean> name = getRandomName();
			Tuple<String, String> location = getRandomLocation();
			p.id = x;
			p.name = name.value1;
			p.isMale = name.value2;
			p.country = location.value1;
			p.city = location.value2;
			return p;
		}).sorted().collect(Collectors.toList());

		System.out.println("People:\n");
		personList.forEach(System.out::println);
		System.out.println();

		// Previous grouping method
		System.out.println("Previous grouping method\n");
		Map<String, Map<String, Map<String, List<Person>>>> grouped = personList.stream().collect(Collectors.groupingBy(
				p -> p.country, Collectors.groupingBy(p -> p.city, Collectors.groupingBy(p -> p.gender()))));

		grouped.keySet().forEach(p -> grouped.get(p).keySet().forEach(q -> grouped.get(p).get(q).keySet().forEach(
				r -> System.out.printf("%s %s %s Count: %d\n", p, q, r, grouped.get(p).get(q).get(r).size()))));
		System.out.println("");

		Map<Person, List<Person>> group = personList.stream().collect(Collectors.groupingBy(p -> {
			Person p1 = new Person();
			p1.country = p.country;
			p1.city = p.city;
			p1.isMale = p.isMale;

			return grouper.group(p1);
		}));

		System.out.println("Country City Gender group:\n");
		List<Person> keys = group.keySet().stream().sorted().collect(Collectors.toList());

		keys.forEach(key -> System.out
				.println(key.country + " " + key.city + " " + key.gender() + " Count: " + group.get(key).size()));
		System.out.println("\nDetailed Info:\n");
		keys.forEach(key -> {
			System.out.print(key.country + " " + key.city + " " + key.gender() + ": ");
			group.get(key).stream().sorted().forEach(person -> System.out.print(person + ", "));
			System.out.println();
		});
	}
}
