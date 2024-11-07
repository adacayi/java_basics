package com.sanver.basics.collections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TreeMapSample {

    static class Person implements Comparable<Person> {
        String name;
        boolean isMale;
        String country;
        String city;

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        public Person(String name, String country, String city, boolean isMale) {
            this.name = name;
            this.isMale = isMale;
            this.country = country;
            this.city = city;
        }

        public String getGender() {
            return isMale ? "Male" : "Female";
        }

        @Override
        public String toString() {
            return String.format("%-15s %-15s %-15s %-15s", country, city, name, getGender());
        }

        @Override
        public int compareTo(Person o2) {
            Person o1 = this;
            Comparator<String> stringComparator = Comparator.nullsFirst(String::compareToIgnoreCase); // To compare strings with null values
            Comparator<Person> comparator = Comparator.comparing(Person::getCountry, stringComparator)
                    .thenComparing(Person::getCity, stringComparator)
                    .thenComparing(Person::getName, stringComparator)
                    .thenComparing(Person::getGender);
            return comparator.compare(o1, o2);
        }
    }

    public static void main(String[] args) {
        // When running this code, you might encounter the following error:
        // java.lang.reflect.InaccessibleObjectException: Unable to make field transient java.util.HashMap$Node[] java.util.HashMap.table accessible: module java.base does not "opens java.util" to unnamed module
        // Check comments in AccessingPrivateElements

        Map<Person, Integer> hashMap = new HashMap<>();
        Person ahmet = new Person("Ahmet", "Turkiye", "Konya", true);
        Person mustafa = new Person("Mustafa", "England", "London", true);
        Person zeynep = new Person("Zeynep", "Turkiye", "Istanbul", false);
        Person deniz = new Person("Deniz", "Turkiye", "Konya", true);
        Person denizFemale = new Person("Deniz", "Turkiye", "Konya", false);
        Person nullPerson = new Person(null, null, null, false);
        hashMap.put(ahmet, 1);
        hashMap.put(mustafa, 2);
        hashMap.put(zeynep, 3);
        hashMap.put(deniz, 4);
        hashMap.put(denizFemale, 5);
        hashMap.put(nullPerson, 6);


        Map<Person, Integer> treeMap = new TreeMap<>(hashMap);
        System.out.println("Hashmap uses an array to store its elements and  puts its elements based on their hash(o) ^ (hash(o) >>> 16) & (capacity - 1) while TreeMap uses a binary tree and puts its elements by comparing their keys\n");
        System.out.println("HashMap output:\n");
        hashMap.forEach((k, v) -> System.out.printf("%d %s %s%n", v, k, "Hash value: " + getHash(hashMap, k)));
        System.out.println();
        System.out.println("Tree output: (Key is Person instance and it is sorted by country, city, name and then gender\n");
        treeMap.forEach((k, v) -> System.out.println(v + " " + k));
    }

    private static int getHash(Map<?, ?> map, Object o) {
        try {
            Field tableField = HashMap.class.getDeclaredField("table");
            tableField.setAccessible(true);
            Map.Entry[] table = (Map.Entry[]) tableField.get(map);
            Method hash = HashMap.class.getDeclaredMethod("hash", Object.class);
            hash.setAccessible(true);
            int n = table == null ? 0 : table.length;
            return (n - 1) & (int) hash.invoke(map, o);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
