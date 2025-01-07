package com.sanver.basics.lambda;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.IntFunction;

public class FunctionInterfaceSample {

    static class Person {
        private int birthDate;

        public Person(int birthDate) {
            this.birthDate = birthDate;
        }

        public int getAge(Function<? super Integer, ? extends Integer> calculate) { // It might be better to use IntUnaryOperator here. We used this just for Function interface demonstration.
            return calculate.apply(birthDate);
        }

        public <T> T getAge(IntFunction<T> getAgeInfo) {
            return getAgeInfo.apply(birthDate);
        }
    }

    public static void main(String[] args) {
        var person = new Person(2010);
        int currentYear = LocalDate.now().getYear();
        Function<Number, Integer> calculateAge = birthDate -> currentYear - birthDate.intValue();
        IntFunction<String> getAgeInfo = birthDate -> "Your age is " + calculateAge.apply(birthDate);

        System.out.println("Current year is " + currentYear);
        System.out.println(person.getAge(calculateAge));
        System.out.println(person.getAge(getAgeInfo));
    }
}
