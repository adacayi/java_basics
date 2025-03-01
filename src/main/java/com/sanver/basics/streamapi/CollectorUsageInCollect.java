package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * This is to demonstrate collector usage in the {@code stream().collect()}.
 */
public class CollectorUsageInCollect {

    public static void main(String[] args) {
        // Create a list of strings
        List<String> words = Arrays.asList("Java", "is", "cool", "and", "fun");

        // Use Collectors.mapping to transform each string to its length
        List<Integer> wordLengths = words.stream()
                .collect(Collectors.mapping(
                        String::length, // Function to map each word to its length
                        Collectors.toList() // Collector to gather results into a List
                )); // Note: 'collect(mapping())' can be replaced with 'map().collect()'

        // Print the lengths
        System.out.println("Word lengths: " + wordLengths);

        // Example with a custom object
        List<Person> people = Arrays.asList(
                new Person("Alice", 30),
                new Person("Bob", 25),
                new Person("Charlie", 45),
                new Person("Charlie", 35)
        );

        // Use mapping to collect names from Person objects into a Set
        Set<String> names = people.stream()
                .collect(Collectors.mapping(
                        Person::name, // Function to extract name from Person
                        Collectors.toSet() // Collector to gather results into a Set
                )); // 'collect(mapping())' can be replaced with 'map().collect()'

        // Print the names
        System.out.println("Names: " + names);

        // Combining Collectors

        System.out.println(
                people.stream().collect(Collectors.filtering(x -> x.age > 25, Collectors.mapping(x -> x.name, Collectors.toList())))
        );
        System.out.println(people.stream().collect(Collectors.mapping(x -> x.age, Collectors.filtering(x -> x > 25, Collectors.toList()))));

        System.out.println(people.stream().collect(Collectors.filtering(x -> x.age > 25, Collectors.groupingBy(x -> x.name))));
        System.out.println(people.stream().collect(Collectors.filtering(x -> x.age > 25, Collectors.groupingBy(x -> x.name, Collectors.averagingInt(x -> x.age)))));
        System.out.println(people.stream().collect(Collectors.groupingBy(x -> x.name, Collectors.mapping(x -> x.age, Collectors.toSet()))));
        System.out.println(people.stream().collect(Collectors.groupingBy(x -> x.name, Collectors.partitioningBy(x -> x.age > 40))));
        System.out.println(people.stream().collect(Collectors.partitioningBy(x -> x.age > 25, Collectors.groupingBy(x -> x.age > 40))));
        System.out.println(people.stream().collect(Collectors.partitioningBy(x -> x.age > 25, Collectors.groupingBy(x -> x.age > 40, Collectors.toSet()))));

        // Collectors toCollection
        var personComparator = Comparator.<Person, String>comparing(p -> p.name).thenComparing(p -> p.age);
        var sorted = people.stream().collect(Collectors.toCollection(() -> new TreeSet<>(personComparator)));
        System.out.println(sorted);
    }

    record Person(String name, int age) {

    }
}