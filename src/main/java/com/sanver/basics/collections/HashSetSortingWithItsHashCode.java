package com.sanver.basics.collections;

import java.util.HashSet;
import java.util.Set;

public class HashSetSortingWithItsHashCode {

    // This is the method where the eventual hash code is calculated for the HashMap.
    // The final index of the element is calculated by hash & (capacity -1), where capacity is the length of the backed array of the HashMap, which is always a power of 2.
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
        set.forEach(t -> System.out.printf("Value: %-17s Object hash code: %,14d  Hash code for the map (object hash code ^ (object hash code >>> 16)): %,14d  Index for the map which has a backed array of length 16: %,2d%n", t, t.hashCode(), hash(t), hash(t) & (16 - 1)));
        System.out.println("The implementation of the forEach goes over each element of the backed array of the map for the set (HashSet is actually using a HashMap to store its values), so the order depends on the calculated index for that array");
    }

    record Person(String name) {
        @Override
        public String toString() {
            return "Person(" + name + ")";
        }
    }
}
