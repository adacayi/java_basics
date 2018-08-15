package com.sanver.basics.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class PersonBase {
	String fathersName;
	@SuppressWarnings("unused")
	private String mothersMaidenName;
	public String birthPlace;
}

class Person extends PersonBase implements Comparable<Person> {
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

	public Person() {
	}

	public Person(String name, String country, String city) {
		this.name = name;
		this.country = country;
		this.city = city;
		this.fathersName = "Abdullah";
	}

	@Override

	public int compareTo(Person o2) {
		Comparator<Person> comparer = Comparator.comparing((Person p) -> p.country).thenComparing(p -> p.city)
				.thenComparing(p -> p.name);
		return comparer.compare(this, o2);
	}
}

public class SimpleReflectionSample {

	public static void main(String[] args) {
		Class<Person> person = Person.class;
		List<Constructor<?>> constructors = Arrays.asList(person.getConstructors());
		List<Method> methods = Arrays.asList(person.getDeclaredMethods());
		List<Method> inheritedMethods = Arrays.stream(person.getMethods()).filter(x -> !methods.contains(x))
				.collect(Collectors.toList());
		List<Field> fields = Arrays.asList(person.getDeclaredFields());
		List<Field> inheritedFields = Arrays.stream(person.getFields()).filter(x -> !fields.contains(x))
				.collect(Collectors.toList());
		List<Field> nonPublicInheritedFields = Arrays.stream(person.getSuperclass().getDeclaredFields())
				.filter(x -> !inheritedFields.contains(x)).collect(Collectors.toList());

		System.out.println("Constructors\n");
		constructors.forEach(m -> System.out.println(m));
		System.out.println("\nMethods excluding inherited methods\n");
		methods.forEach(m -> System.out.println(m));
		System.out.println("\nInherited public methods\n");
		inheritedMethods.forEach(m -> System.out.println(m));
		System.out.println("\nFields excludes inherited fields\n");
		fields.forEach(System.out::println);
		System.out.println("\nInherited public fields\n");
		inheritedFields.forEach(System.out::println);
		System.out.println("\nNon-public fields of base class\n");
		nonPublicInheritedFields.forEach(System.out::println);
		System.out.println("\nMethod Execution\n");

		try {
			Person ahmet = person.getConstructor(String.class, String.class, String.class).newInstance("Ahmet",
					"England", "London");
			System.out.println("Constructor execution: " + ahmet);
			Person mustafa = new Person("Mustafa", "England", "Kent");
			System.out.println("toString method execution: " + person.getMethod("toString").invoke(mustafa));
			System.out.println(
					"compareTo method execution: " + person.getMethod("compareTo", person).invoke(mustafa, ahmet));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
}
