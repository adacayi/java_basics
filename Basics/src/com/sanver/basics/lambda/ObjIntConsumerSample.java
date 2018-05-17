package com.sanver.basics.lambda;

import java.util.function.ObjIntConsumer;

public class ObjIntConsumerSample {
	class Person {
		private String name;

		public Person(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Person " + name;
		}
	}

	public static void main(String[] args) {
		Person ahmet = new ObjIntConsumerSample().new Person("Ahmet");
		ObjIntConsumer<Person> numerize = (s, i) -> System.out.println(s + " is at age " + i);
		numerize.accept(ahmet, 1);
	}

}
