package com.sanver.basics.collections;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.SequencedMap;
import java.util.SequencedSet;

/**
 * SequencedMap and SequencedSet interfaces are introduced with Java 21
 */
public class SequencedMapAndSequencedSetSample {
    public static void main(String[] args) {
        SequencedMap<Integer, String> sequencedMap = new LinkedHashMap<>();
        sequencedMap.putLast(1, "B");
        sequencedMap.putFirst(2, "A");
        sequencedMap.put(3, "C");
        System.out.println(sequencedMap);
        System.out.println(sequencedMap.reversed());
        System.out.println(sequencedMap);
        System.out.println(sequencedMap.sequencedKeySet());
        System.out.println(sequencedMap.sequencedValues());

        SequencedSet<String> sequencedSet = new LinkedHashSet<>();
        sequencedSet.addLast("B");
        sequencedSet.addFirst("A");
        sequencedSet.add("C");
        System.out.println(sequencedSet);
    }
}
