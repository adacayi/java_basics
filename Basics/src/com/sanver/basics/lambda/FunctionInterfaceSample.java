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

		public int getAge(IntFunction<Integer> calculate) {
			return calculate.apply(birthDate);
		}
	}

	public static void main(String[] args) {
		FunctionInterfaceSample sample = new FunctionInterfaceSample();
		Person Ahmet = sample.new Person(2010);
		int currentYear = LocalDate.now().getYear();
		Function<Integer, Integer> calculate = birthDate -> currentYear - birthDate;
		IntFunction<Integer> calculateInt = birthDate -> currentYear - birthDate;

		System.out.println(currentYear);
		System.out.println(Ahmet.getAge(calculate));
		System.out.println(Ahmet.getAge(calculateInt));
	}
}
