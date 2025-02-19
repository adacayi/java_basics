package com.sanver.basics.streamapi;

import java.util.*;
import java.util.stream.*;

/**
 * <p>
 * This is just to demonstrate {@code collect(Collectors.mapping())} usage.
 * </p>
 *
 * Use {@code map().collect()} instead.
 */
public class CollectorsMappingSample {

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
    }

    record Person(String name, int age) {

    }
}