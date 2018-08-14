package com.sanver.basics.reflection;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class LoadingClassFromAString {

	public class Person {

		private String name;
		private int age;

		public Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return String.format("%s at age %d", name, age);
		}

		public String calculate(String s1, String s2) {
			return s1 + s2;
		}
	}

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		@SuppressWarnings("unchecked")
		Class<Person> person = (Class<Person>) Class
				.forName("com.sanver.basics.reflection.LoadingClassFromAString$Person");
		System.out.println(person.getName());
		Arrays.asList(person.getConstructors()).forEach(System.out::println);
		Person ahmet = person.getConstructor(LoadingClassFromAString.class, String.class, int.class) // Since person is
																										// an inner non
																										// static class
																										// we need its
																										// outer class
																										// instance to
																										// initialize
																										// it.
				.newInstance(new LoadingClassFromAString(), "Ahmet", 2);
		System.out.println(ahmet);
	}

}
