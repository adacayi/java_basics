package com.sanver.basics.streamapi;

import lombok.Value;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupingSampleAggregation {
    private static final Random random = new Random();

    public static void main(String[] args) {
        int personCount = 16;
        var people = IntStream.rangeClosed(1, personCount).mapToObj(x -> generatePerson()).sorted(Comparator.comparing(Person::getName).thenComparing(Person::getCity)).toList();
        System.out.printf("People%n%n");

        for (Person person : people) {
            System.out.println(person);
        }

        Map<GroupKey, Double> group = people.stream().collect(Collectors.groupingBy(p -> new GroupKey(p.getName(), p.getCity()), Collectors.averagingInt(x -> x.age)));

        System.out.printf("%nName city groups%n%n");
        var format = "[%-8s, %-8s]: Average age = %.2f%n";
        var comparator = Comparator.comparing((Map.Entry<GroupKey, Double> x) -> x.getKey().name()).thenComparing(x -> x.getKey().city());
        group.entrySet().stream().sorted(comparator).forEach(entry -> System.out.printf(format, entry.getKey().name, entry.getKey().city, entry.getValue()));
    }

    public static Person generatePerson() {
        String[] names = {"Abdullah", "Hatice"};
        String[] cities = {"Istanbul", "Urfa"};
        return new Person(names[random.nextInt(0, names.length)], random.nextInt(1, 91), cities[random.nextInt(0, cities.length)]);
    }

    @Value
    public static class Person {
        private static AtomicInteger lastId = new AtomicInteger(0);
        int id = lastId.incrementAndGet();
        String name;
        int age;
        String city;

        public String toString() {
            return "[%2d, %-8s, %2d, %-8s]".formatted(id, name, age, city);
        }
    }

    record GroupKey(String name, String city) {
    }
}
