package com.sanver.basics.streamapi;

import lombok.Value;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupingSampleWithMultipleFields {
    private static final Random random = new Random();
    private static AtomicInteger lastId = new AtomicInteger(0);

    public static void main(String[] args) {
        int personCount = 16;
        var people = IntStream.rangeClosed(1, personCount).mapToObj(x -> generatePerson()).sorted(Comparator.comparing((Person x) -> x.name).thenComparing(x -> x.city).thenComparing(x -> x.age)).toList();
        people.forEach(System.out::println);

        var group = people.stream().collect(Collectors.groupingBy(p -> new GroupKey(p.getName(), p.getCity())));
        System.out.printf("%nName city group:%n%n");
        var format = "%-8s %-8s: %s";
        var entry = new ArrayList<>(group.entrySet());
        entry.sort(Comparator.comparing((Map.Entry<GroupKey, List<Person>> x) -> x.getKey().name()).thenComparing(x -> x.getKey().city()));

        entry.forEach(e -> System.out.println(String.format(format, e.getKey().name(), e.getKey().city(), e.getValue().stream().map(Person::toString).collect(Collectors.joining(", ")))));
    }

    private static Person generatePerson() {
        String[] names = {"Abdullah", "Hatice"};
        String[] cities = {"Istanbul", "Urfa"};
        return new Person(names[random.nextInt(0, names.length)], random.nextInt(1, 91), cities[random.nextInt(0, cities.length)]);
    }

    @Value
    private static class Person {
        int id = lastId.incrementAndGet();
        String name;
        int age;
        String city;

        public String toString() {
            return String.format("%2d- %-8s %-2d %-8s", id, name, age, city);
        }
    }

    private record GroupKey(String name, String city) {
    }
}