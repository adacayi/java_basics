package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.StringJoiner;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorAggregation {

    public static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }

    public static void main(String[] args) {
        Person[] people = {new Person("Ahmet", 3), new Person("Mustafa", 3),
                new Person("Fatima", 6)};
        Supplier<Stream<Person>> supplier = () -> Arrays.stream(people);
        System.out.println("Sum of ages is " + supplier.get().collect(Collectors.summingInt(p -> p.age)));
        System.out.println("Sum of ages is " + supplier.get().mapToInt(p -> p.age).sum());
        System.out.println("Count of ages is " + supplier.get().collect(Collectors.counting()));
        System.out.println("Count of ages is " + supplier.get().count());
        System.out.println("Average age is " + supplier.get().collect(Collectors.averagingInt(p -> p.age)));
        System.out.println("Average age is " + supplier.get().mapToInt(p -> p.age).average().orElse(0));
        System.out.println("Minimum age is "
                + supplier.get().collect(Collectors.minBy(Comparator.comparing((Person p) -> p.age))).
                orElse(new Person("", 0)).age);
        // This is shorter and you don’t need to define a Comparator
        System.out.println("Minimum age is " + supplier.get().mapToInt(p -> p.age).min().orElse(0));
        System.out.println("Maximum age is "
                + supplier.get().collect(Collectors.maxBy(Comparator.comparing((Person p) -> p.age))).
                orElse(new Person("", 0)).age);
        System.out.println("Maximum age is " + supplier.get().mapToInt(p -> p.age).max().orElse(0));
        System.out.println("Summary: " + supplier.get().collect(Collectors.summarizingInt(p -> p.age)));
        System.out.println(supplier.get().map(p -> p.name + " with age " + p.age)
                .collect(Collectors.joining(" and ", "People in the collection are ", ".")));
        System.out.println(
                "Map " + supplier.get().collect(Collectors.toMap(p -> p.age, p -> p.name, (x, y) -> x + " ;" + y)));
        // The last function is a merger function in case there is a key with more than
        // one value.
        Collector<Person, StringJoiner, String> personNameCollector = Collector.of(() -> new StringJoiner(" | "), // supplier
                (j, p) -> j.add(p.name.toUpperCase()), // accumulator
                (j1, j2) -> j1.merge(j2), // combiner
                StringJoiner::toString); // finisher
        // The supplier initially constructs such a StringJoiner with the appropriate
        // delimiter. The accumulator is used to add each persons upper-cased name to
        // the StringJoiner. The combiner knows how to merge two StringJoiners into one.
        // In the last step the finisher constructs the desired String from the
        // StringJoiner.
        System.out.println(supplier.get().collect(personNameCollector));
    }

}
