package com.sanver.basics.streamapi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupingSample {

    public static void main(String[] args) {
        int personCount = 16;

        Map<Boolean, List<Person>> people = IntStream.rangeClosed(1, personCount).mapToObj(x -> {
            var p = new Person();
            p.id = x;
            p.isMale = x % 2 == 0;
            p.name = (p.isMale ? "Abdullah" : "Hatice");
            return p;
        }).collect(Collectors.groupingBy(p -> p.isMale));

        System.out.println("Genders:\n");
        people.keySet().forEach(gender -> System.out.println(getGender(gender)));
        System.out.println("\nDetailed Info:\n");
        people.forEach((k, v) -> System.out.printf("%-6s: %s%n", getGender(k),
                v.stream().map(Person::toString).collect(Collectors.joining(", "))));
    }

    private static String getGender(boolean isMale) {
        return isMale ? "Male" : "Female";
    }

    static class Person {
        int id;
        String name;
        boolean isMale;

        public String toString() {
            return String.format("%2d %8s", id, name);
        }
    }
}
