package com.sanver.basics.stream_api;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GroupingSampleWithMultipleFieldsBetter {

    class Person {
        int id;
        String name;
        Boolean isMale;

        public String toString() {
            return id + " " + name;
        }
    }

    class GroupKey {
        private String name;
        private boolean isMale;

        public GroupKey(String name, boolean isMale) {
            this.name = name;
            this.isMale = isMale;
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
                    .append(isMale, other.isMale)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(name)
                    .append(isMale)
                    .hashCode();
        }
    }

    public static void main(String[] args) {
        int personCount = 16;
        var sample = new GroupingSampleWithMultipleFieldsBetter();

        var people = IntStream.rangeClosed(1, personCount).mapToObj(x -> {
            Person p = sample.new Person();
            p.id = x;
            p.isMale = x % 2 == 0;
            p.name = (p.isMale ? "Abdullah" : "Hatice");
            return p;
        }).collect(Collectors.groupingBy(p -> sample.new GroupKey(p.name, p.isMale)));

        System.out.println("Name gender group:\n");
        people.forEach((key, value) -> System.out.println(key.name + " " + (key.isMale ? "Male" : "Female")));
        System.out.println("\nDetailed Info:\n");
        people.forEach((key, value) -> {
            System.out.println(key.name + " " + (key.isMale ? "Male" : "Female"));
            value.forEach(person -> System.out.print(person + ", "));
            System.out.println();
        });
    }
}
