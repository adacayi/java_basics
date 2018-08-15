package com.sanver.basics.collections;

import java.util.HashSet;
import java.util.Set;

public class HashSetSortingWithItsHashCode {

    static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int hashCode() {
            return name.charAt(4);
        }
    }

    public static void main(String[] args) {
        // HashSet sorts elements according to their HashCode
        Set<Person> set = new HashSet<>();
        set.add(new Person("Muhammed"));
        set.add(new Person("Ahmet"));
        set.add(new Person("Mustafa"));
        System.out.println(set);
        set.forEach(t -> System.out.printf("Value: %s Hash code: %s\n", t, t.hashCode())); // Hashcode changes order
        // but couldn't understand how its does it
    }

}
