package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortingWithMultipleFields {

    static class Person {
        String name;
        String country;
        int age;

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public int getAge() {
            return age;
        }

        public Person(String name, String country, int age) {
            this.name = name;
            this.age = age;
            this.country = country;
        }

        public String toString() {
            return name + "  " + country + "  " + age;
        }
    }

    public static void main(String[] args) {
        List<Person> people = new ArrayList<>(Arrays.asList(
                new Person("Ahmet", "Turkiye", 1),
                new Person("Ahmet", "England", 3),
                new Person("Ahmet", "England", 2),
                new Person("Mustafa", "England", 2),
                new Person("Mustafa", "Turkiye", 5),
                new Person("Mustafa", "Turkiye", 7),
                new Person(null, null, -1)));
        // To sort with name descending, country ascending and age descending:
        Comparator<String> stringComparator = Comparator.nullsFirst(String::compareToIgnoreCase);
        Comparator<Person> nameDescending = Comparator.comparing(Person::getName, stringComparator).reversed();
        Comparator<Person> ageDescending = Comparator.comparing(Person::getAge).reversed();
        people.sort(nameDescending.thenComparing(Person::getCountry, stringComparator).thenComparing(ageDescending));
        String content = people.stream().map(Person::toString).collect(Collectors.joining("\n"));
        System.out.println(content);
    }
}
