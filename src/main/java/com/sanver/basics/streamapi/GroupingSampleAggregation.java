package com.sanver.basics.streamapi;

import lombok.Value;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupingSampleAggregation {
    public static void main(String[] args) {
        int personCount = 16;
        var random = new Random();
        var people = IntStream.rangeClosed(1, personCount).mapToObj(x ->
                new Person(Math.random() > 0.5 ? "Abdullah" : "Hatice", random.nextInt(90) + 1, random.nextBoolean() ? "Istanbul" : "Urfa")).toList();
        System.out.printf("People%n%n");

        for (Person person : people) {
            System.out.println(person);
        }

        Map<GroupKey, Double> group = people.stream().collect(Collectors.groupingBy(p -> new GroupKey(p.getName(), p.getCity()), Collectors.averagingInt(x -> x.age)));

        System.out.printf("%nName city groups%n%n");
        var format = "[%-8s, %-8s]: Average age = %.2f%n";
        group.forEach((key, value) -> System.out.printf(format, key.name, key.city, value));
    }

    @Value
    private static class Person {
        private static AtomicInteger lastId = new AtomicInteger(0);
        int id = lastId.incrementAndGet();
        String name;
        int age;
        String city;

        public String toString() {
            return String.format("[%2d, %-8s, %2d, %-8s]", id, name, age, city);
        }
    }

    record GroupKey(String name, String city) {
    }
}
