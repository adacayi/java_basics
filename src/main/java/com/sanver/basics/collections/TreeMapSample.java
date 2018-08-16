package com.sanver.basics.collections;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapSample {

    static class Person implements Comparable<Person> {
        int id;
        String name;
        Boolean isMale;
        String country;
        String city;

        public String gender() {
            return isMale ? "Male" : "Female";
        }

        @Override
        public String toString() {
            return name + " " + country + " " + city;
        }

        @Override
        public int compareTo(Person o2) {
            Person o1 = this;
            Comparator<Person> comparator = Comparator.comparing((Person p) -> p.country == null ? "" : p.country)
                    .thenComparing(p -> p.city == null ? "" : p.city).thenComparing(p -> p.name == null ? "" : p.name)
                    .thenComparing(p -> p.gender());// If we do not check for null and replace for a not null value we
            // get an error in runtime.
            return comparator.compare(o1, o2);
        }
    }

    public static void main(String[] args) {
        Map<Person, Integer> hashMap = new HashMap<>();
        Person ahmet = new Person() {
            {
                name = "Ahmet";
                country = "Turkiye";
                city = "Konya";
                isMale = true;
            }
        };
        Person mustafa = new Person() {
            {
                name = "Mustafa";
                country = "England";
                city = "London";
                isMale = true;
            }
        };
        Person zeynep = new Person() {
            {
                name = "Zeynep";
                country = "Turkiye";
                city = "Istanbul";
                isMale = false;
            }
        };
        hashMap.put(ahmet, 1);
        hashMap.put(mustafa, 2);
        hashMap.put(zeynep, 3);

        Map<Person, Integer> treeMap = new TreeMap<>(hashMap);
        System.out.println("Hashmap sorts by its keys' hashcodes (not sure about the algorithm) while TreeMap sorts by its keys\n");
        System.out.println("HashMap output:\n");
        hashMap.forEach((k, v) -> System.out.printf("%s %s %,d\n", v, k, k.hashCode()));
        System.out.println();
        System.out.println("Tree output:\n");
        treeMap.forEach((k, v) -> System.out.println(v + " " + k));
    }
}
