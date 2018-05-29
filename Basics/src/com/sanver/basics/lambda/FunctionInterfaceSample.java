package com.sanver.basics.lambda;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.IntFunction;;

public class FunctionInterfaceSample {

	class Person {
		private int birthDate;

		public Person(int birthDate) {
			this.birthDate = birthDate;
		}

		public int getAge(Function<Integer, Integer> calculate) {
			return calculate.apply(birthDate);
		}

		public <T> T getAge(IntFunction<T> calculate) {
			return calculate.apply(birthDate);
		}
	}

	public static void main(String[] args) {
		FunctionInterfaceSample sample = new FunctionInterfaceSample();
		Person Ahmet = sample.new Person(2010);
		int currentYear = LocalDate.now().getYear();
		Function<Integer, Integer> calculate = birthDate -> currentYear - birthDate;
		IntFunction<String> calculateInt = birthDate -> "Your age is " + (currentYear - birthDate);

		System.out.println("Current year is " + currentYear);
		System.out.println(Ahmet.getAge(calculate));
		System.out.println(Ahmet.getAge(calculateInt));
	}
}
