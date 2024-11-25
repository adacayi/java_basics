package com.sanver.basics.concurrentcollections;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Demonstrates various types of concurrent maps available in Java 21.
 *
 * <p>Concurrent maps are thread-safe implementations of the {@code Map} interface. This class
 * highlights three main implementations:</p>
 *
 * <ul>
 *   <li>{@code ConcurrentHashMap}:
 *     <ul>
 *       <li>Most commonly used concurrent map implementation.</li>
 *       <li>High performance and scalability.</li>
 *       <li>Does not maintain any order of keys.</li>
 *       <li>Best suited for high-concurrency environments with frequent updates.</li>
 *     </ul>
 *   </li>
 *   <li>{@code ConcurrentSkipListMap}:
 *     <ul>
 *       <li>Maintains keys in a sorted order (based on natural ordering or a custom comparator).</li>
 *       <li>Ideal for range-based operations or cases requiring ordered traversal.</li>
 *       <li>Slightly slower than {@code ConcurrentHashMap} for basic operations.</li>
 *     </ul>
 *   </li>
 *   <li>{@code java.util.concurrent.Map.ofEntries()} (Immutable Map):
 *     <ul>
 *       <li>Provides an immutable map for use in concurrent environments.</li>
 *       <li>Cannot be modified after creation, ensuring thread-safety.</li>
 *       <li>Ideal for use cases where map content does not change.</li>
 *     </ul>
 *   </li>
 *   <li>{@code java.util.Collections.synchronizedMap(map)} :
 *     <ul>
 *       <li>Provides a synchronized map for use in concurrent environments.</li>
 *       <li>Explicit synchronization required for iteration</li>
 *     </ul>
 *   </li>
 * </ul>
 */
public class ConcurrentMaps {

    public static void main(String[] args) {
        // ConcurrentHashMap example
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("Apple", 3);
        concurrentHashMap.put("Banana", 5);
        concurrentHashMap.put("Cherry", 2);
        System.out.println("ConcurrentHashMap: " + concurrentHashMap);

        // ConcurrentSkipListMap example
        ConcurrentSkipListMap<String, Integer> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        concurrentSkipListMap.put("Apple", 3);
        concurrentSkipListMap.put("Cherry", 2);
        concurrentSkipListMap.put("Banana", 5);
        System.out.println("ConcurrentSkipListMap (Sorted by key): " + concurrentSkipListMap);

        // Immutable Map example (from Map.ofEntries)
        Map<String, Integer> immutableMap = Map.ofEntries(
                Map.entry("Apple", 3),
                Map.entry("Banana", 5),
                Map.entry("Cherry", 2)
        );
        System.out.println("Immutable Map (Map.ofEntries): " + immutableMap);

        // Demonstrating immutability
        try {
            immutableMap.put("Apple", 4); // UnsupportedOperationException expected
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify an immutable map.");
        }

        // As of Java 21, there is no direct concurrent map implementation that maintains insertion order, similar to LinkedHashMap. Alternatively you can wrap a LinkedHashMap with Collections.synchronizedMap
        Map<String, Integer> synchronizedMap = Collections.synchronizedMap(new LinkedHashMap<>());

        synchronizedMap.put("Apple", 1);
        synchronizedMap.put("Banana", 2);
        synchronizedMap.put("Cherry", 3);

        synchronized (synchronizedMap) { // Explicit synchronization required for iteration
            for (Map.Entry<String, Integer> entry : synchronizedMap.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        }
    }
}

