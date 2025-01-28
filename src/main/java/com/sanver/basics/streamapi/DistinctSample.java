package com.sanver.basics.streamapi;

import java.util.Arrays;

public class DistinctSample {

    record Person(String name) {
        @Override
        public boolean equals(Object o) {
            return false; // This method is overridden to show that distinct does not invoke it if the item references are the same.
        }

        @Override
        public String toString() {
            return "(%s)".formatted(name);
        }
    }

    public static void main(String[] args) {
        int[] numbers = {3, 2, 5, 6, 3, 3};
        String[] names = {"Ahmet", "Salih", "Ahmet"};
        System.out.println(Arrays.toString(Arrays.stream(numbers).distinct().toArray()));
		System.out.println(Arrays.toString(Arrays.stream(names).distinct().toArray(String[]::new)));

        Integer[] values = {127, 127, 128, 128};
        System.out.println("values[0] == values[1]     : " + (values[0] == values[1])); // Java keeps cached objects for Integer values from -128 to 127 (inclusive), this is why values[0] == values[1] returns true.
        System.out.println("values[2] == values[3]     : " + (values[2] == values[3])); // For integers outside this range (-128 to 127 (inclusive)) (e.g., 128), new Integer objects are created each time. Therefore, values[2] == values[3] returns false since they refer to different objects.
        System.out.println("values[2].equals(values[3]): " + values[2].equals(values[3]));
        System.out.println(Arrays.stream(values).distinct().toList()); // This is to show that distinct uses equals method to check for equality if the item references are not equal.
        var person1 = new Person("Ahmet");
        Person[] people = {person1, person1};
        var list = Arrays.stream(people).distinct().toList(); // This is to show that distinct first checks for reference equality using '==' before invoking the equals method.
        System.out.println(list);
    }
}
