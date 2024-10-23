package com.sanver.basics.collections;

import java.util.HashSet;
import java.util.Set;

public class HashSetSample {
    public static void main(String[] args) {
        Set<String> firstSet = new HashSet<>();
        Set<String> secondSet = new HashSet<>();
        Set<String> union;
        Set<String> intersection;
        Set<String> differenceFirstSet;

        firstSet.add("Ahmet");
        System.out.printf("Result of adding an element for the second time: %s%n", firstSet.add("Ahmet"));
        firstSet.add("Muhammed");
        secondSet.add("Mustafa");
        secondSet.add("Muhammed");
        union = new HashSet<>(firstSet);
        union.addAll(secondSet);
        intersection = new HashSet<>(firstSet);
        intersection.retainAll(secondSet);
        differenceFirstSet = new HashSet<>(firstSet);
        differenceFirstSet.removeAll(secondSet);

        System.out.println("First set: " + firstSet);
        System.out.println("Second set: " + secondSet);
        System.out.println("firstSet.contains(\"Ahmet\"):" + firstSet.contains("Ahmet"));
        System.out.println("Union: " + union);
        System.out.println("Intersection: " + intersection);
        System.out.println("First set difference second set: " + differenceFirstSet);
    }
}
