package com.sanver.basics.streamapi;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupingSampleAggregation {
    private static int lastId = 0;

    static class Person {
        private int id;
        private String name;
        private int age;
        private String city;

        public Person(String name, int age, String city) {
            this.id = getNewId();
            this.name = name;
            this.age = age;
            this.city = city;
        }

        public String toString() {
            return String.format("%d- %s at age %d in %s", id, name, age, city);
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getCity() {
            return city;
        }

        private synchronized int getNewId() {
            return ++lastId;
        }
    }

    static class GroupKey {
        private String name;
        private String city;

        public GroupKey(String name, String city) {
            this.name = name;
            this.city = city;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (!(obj instanceof GroupKey)) {
                return false;
            }

            var other = (GroupKey) obj;

            return new EqualsBuilder()
                    .append(name, other.name)
                    .append(city, other.city)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(name)
                    .append(city)
                    .hashCode();
        }
    }

    public static void main(String[] args) {
        int personCount = 16;
        var people = IntStream.rangeClosed(1, personCount).mapToObj(x ->
                new Person(Math.random() > 0.5 ? "Abdullah" : "Hatice", (int) (Math.random() * 90 + 1), Math.random() > 0.5 ? "Istanbul" : "Urfa"))
                .sorted(Comparator.comparing((Person x) -> x.name).thenComparing(x -> x.city)).collect(Collectors.toList());
        System.out.println("People:");

        for (Person person : people) {
            System.out.println(person);
        }
        var group = people.stream().collect(Collectors.groupingBy(p -> new GroupKey(p.getName(), p.getCity()), Collectors.averagingDouble(x -> x.age)));

        System.out.println("Name city group:\n");
        group.forEach((key, value) -> System.out.println(String.format("%-18s Average age = %.2f", key.name + " " + key.city, value)));

    }
}
