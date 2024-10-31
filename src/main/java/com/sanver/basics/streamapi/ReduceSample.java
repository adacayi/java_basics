package com.sanver.basics.streamapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;

import static com.sanver.basics.utils.Utils.getThreadInfo;

public class ReduceSample {

    public static void main(String[] args) {
        var people = new ArrayList<>(List.of(new Person("Ahmet", 3), new Person("Mustafa", 2), new Person("Muhammed", 1)));

        people.stream().reduce((p1, p2) -> p1.age > p2.age ? p1 : p2)
                .ifPresent(x -> System.out.printf("Oldest person is %s%n%n", x));

        BinaryOperator<Person> reduceOperator = (initial, current) -> {
            System.out.printf("Combining %-25s with %-32s %s%n", initial.name, current.name, getThreadInfo());
            var name = initial.name + ((current.name == null || current.name.isEmpty()) ? "" : " " + current.name);
            var age = initial.age + current.age;
            return new Person(name, age);
        };

        var emptyReduce = Collections.<Person>emptyList().stream().reduce(new Person("Nuh", 50), reduceOperator);
        System.out.printf("Empty stream reduce with identity: %s%n%n", emptyReduce);

        var combinedNamesAndAges = people.stream().reduce(reduceOperator).orElse(null); // Since reduce is called without an identity argument it returns Optional<Person>, because the stream might be empty. If the identity argument is passed to reduce it will return Person, and in case the stream is empty, the result will be the identity argument itself.
        System.out.printf("%nCombination result with serial execution: %s%n%n", combinedNamesAndAges);

        combinedNamesAndAges = people.parallelStream().reduce(reduceOperator).orElse(null);
        System.out.printf("%nCombination result with parallelStream (however, since item count is 3, single thread is used): %s%n%n", combinedNamesAndAges);

        combinedNamesAndAges = people.stream().reduce(new Person("Ibrahim", 40), reduceOperator); // If identity is not set correctly
        System.out.printf("%nCombination result with serial execution, with identity not being an actual identity (i.e. reduceOperator.apply(identity, person) != person): %s%n%n", combinedNamesAndAges);

        combinedNamesAndAges = people.parallelStream().reduce(new Person("Ibrahim", 40), reduceOperator); // If identity is not set correctly
        System.out.printf("%nCombination result with parallel execution, with identity not being an actual identity (i.e. reduceOperator.apply(identity, person) != person): %s%n%n", combinedNamesAndAges);

        combinedNamesAndAges = people.stream().reduce(new Person("", 0), reduceOperator); // If identity is set correctly
        System.out.printf("%nCombination result with serial execution, with identity set correctly(i.e. reduceOperator.apply(identity, person) = person): %s%n%n", combinedNamesAndAges);

        combinedNamesAndAges = people.parallelStream().reduce(new Person("", 0), reduceOperator); // If identity is set correctly
        System.out.printf("%nCombination result with parallel execution, with identity set correctly (i.e. reduceOperator.apply(identity, person) = person): %s%n%n", combinedNamesAndAges);

        System.out.println("Parallel execution with 4 elements, without identity. This is to show that for 4 elements reduce is done with multiple threads in parallelStream.");
        people.add(new Person("Adem", 80));
        combinedNamesAndAges = people.parallelStream().reduce(reduceOperator).orElse(null);
        System.out.printf("%nCombination result with parallel execution: %s%n%n", combinedNamesAndAges);
    }

    public static class Person {
        String name;
        int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String toString() {
            return name + " " + age;
        }
    }
}
