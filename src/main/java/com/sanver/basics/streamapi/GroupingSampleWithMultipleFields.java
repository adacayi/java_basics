package com.sanver.basics.streamapi;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sanver.basics.streamapi.GroupingSampleAggregation.GroupKey;
import static com.sanver.basics.streamapi.GroupingSampleAggregation.Person;
import static com.sanver.basics.streamapi.GroupingSampleAggregation.generatePerson;

public class GroupingSampleWithMultipleFields {
    public static void main(String[] args) {
        int personCount = 16;
        var people = IntStream.rangeClosed(1, personCount).mapToObj(x -> generatePerson()).sorted(Comparator.comparing(Person::getName).thenComparing(Person::getCity)).toList();
        people.forEach(System.out::println);

        var group = people.stream().collect(Collectors.groupingBy(p -> new GroupKey(p.getName(), p.getCity())));
        System.out.printf("%nName city group:%n%n");
        var format = "%-8s %-8s: %s%n";
        var comparator = Comparator.comparing((Map.Entry<GroupKey, List<Person>> x) -> x.getKey().name()).thenComparing(x -> x.getKey().city());
        group.entrySet().stream().sorted(comparator).forEach(e -> System.out.printf(format, e.getKey().name(), e.getKey().city(), e.getValue().stream().map(Person::toString).collect(Collectors.joining(", "))));
    }
}