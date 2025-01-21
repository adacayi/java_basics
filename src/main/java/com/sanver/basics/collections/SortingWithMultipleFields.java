package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class SortingWithMultipleFields {

    public static final String AHMET = "Ahmet";
    public static final String ENGLAND = "England";

    public static void main(String[] args) {
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("Turkiye", AHMET, 1),
                new Person(ENGLAND, "AHMET", 3),
                new Person(ENGLAND, AHMET, 2),
                new Person("ENGLAND", "Mustafa", 2),
                new Person("Turkiye", "Mustafa", 5),
                new Person("TURKIYE", "MUSTAFA", 7),
                new Person(null, AHMET, 12),
                new Person(ENGLAND, null, 9),
                new Person(null, null, 15)
        ));
        // To sort with name descending, country ascending and age descending:
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;
        Comparator<String> stringComparator = Comparator.nullsLast(compareToIgnoreCase.reversed());
        Comparator<Person> nameDescending = Comparator.comparing(Person::name, stringComparator);
        Comparator<Person> ageDescending = Comparator.comparing(Person::age).reversed();
        people.sort(Comparator.comparing(Person::country, stringComparator).thenComparing(nameDescending).thenComparing(ageDescending));
        IntStream.range(0, people.size()).forEach(i -> System.out.printf("%-2d- %-10s %-10s %-2d%n", i + 1, people.get(i).country, people.get(i).name, people.get(i).age));
    }

    record Person(String country, String name, int age) {

    }
}
