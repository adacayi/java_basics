package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SortedSample {

    record Person(String name, int age) {
        @Override
        public String toString() {
            return name + " " + age;
        }
    }

    public static void main(String[] args) {
        // We used a Person class since primitive streams do not have sorted method with
        // Comparator parameter. There is only a parameterless sorted method in
        // primitive streams.
        Person[] people = {new Person("Ahmet", 3), new Person("Mustafa", 2), new Person("Muhammed", 1)};

        Comparator<Person> ageComparator = Comparator.comparing(Person::age);

        var sorted = Arrays.stream(people).sorted((x, y) -> {
            System.out.println("Comparing " + x.age + " with " + y.age);
            return ageComparator.compare(x, y);
        }).map(Person::toString).collect(Collectors.joining(", "));

        System.out.println(sorted);
    }
}
