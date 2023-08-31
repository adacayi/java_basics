package com.sanver.basics.collections;

import lombok.Value;

import java.util.HashSet;
import java.util.Set;

public class HashSetSortingWithItsHashCode {

    // This is the method where the eventual hash code is calculated for the HashMap.
    // The final index of the element is calculated by hash & (capacity -1), where capacity is the length of the backed array of the HashMap
    // That formula ensures the final index values are smaller than the length of the array.
    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public static void main(String[] args) {
        Set<Person> set = new HashSet<>();
        set.add(new Person("Muhammed"));
        set.add(new Person("Ahmet"));
        set.add(new Person("Mustafa"));
        System.out.println(set);
        set.forEach(t -> System.out.printf("Value: %-12s Object hash code: %-12s Hash code for the map(object hash code ^ (object hash code >>> 16)): %-12s Index for the map which has a backed array of length 16: %s\n", t, t.hashCode(), hash(t), hash(t) & (16 - 1)));
        System.out.println("The implementation of the forEach goes over each element of the backed array of the map for the set (HashSet is actually using a HashMap to store its values), so the order depends on the calculated index for that array");
    }

    @Value
    static class Person {
        String name;

        @Override
        public String toString() {
            return name;
        }
    }
}
