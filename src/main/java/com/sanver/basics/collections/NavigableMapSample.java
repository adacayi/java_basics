package com.sanver.basics.collections;

import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Demonstrates the usage of the {@link NavigableMap} interface, part of the Java Collections Framework,
 * to maintain a map of key-value pairs in sorted order based on keys with additional navigation capabilities.
 * It extends {@link java.util.SortedMap} and provides methods to find entries relative to other keys
 * (e.g., closest matches, ceiling, floor) and to navigate the map in both ascending and descending order.
 *
 * <p>The {@link NavigableMap} interface defines the following key methods beyond those in {@link SortedMap}:
 * <ul>
 *   <li>{@code lowerEntry(K key)} - Returns the entry with the greatest key strictly less than key, or null if none</li>
 *   <li>{@code floorEntry(K key)} - Returns the entry with the greatest key less than or equal to key, or null if none</li>
 *   <li>{@code ceilingEntry(K key)} - Returns the entry with the least key greater than or equal to key, or null if none</li>
 *   <li>{@code higherEntry(K key)} - Returns the entry with the least key strictly greater than key, or null if none</li>
 *   <li>{@code lowerKey(K key)} - Returns the greatest key strictly less than key, or null if none</li>
 *   <li>{@code floorKey(K key)} - Returns the greatest key less than or equal to key, or null if none</li>
 *   <li>{@code ceilingKey(K key)} - Returns the least key greater than or equal to key, or null if none</li>
 *   <li>{@code higherKey(K key)} - Returns the least key strictly greater than key, or null if none</li>
 *   <li>{@code descendingMap()} - Returns a reverse-order view of the map</li>
 *   <li>{@code descendingKeySet()} - Returns a reverse-order NavigableSet of the keys</li>
 * </ul>
 * <p>It also inherits all methods from {@link java.util.SortedMap} and {@link java.util.Map}.
 *
 * <p>The primary implementation of {@link NavigableMap} in the Java standard library is:
 * <ul>
 *   <li>{@link java.util.TreeMap}: A red-black tree-based implementation that maintains
 *       keys in sorted order with efficient navigation operations.</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 *     NavigableMap<String, Integer> map = new TreeMap<>();
 *     map.put("Banana", 2);
 *     map.put("Apple", 1);
 *     System.out.println(map.ceilingEntry("Apricot").getValue()); // Outputs: 2
 *     System.out.println(map.lowerKey("Banana")); // Outputs: "Apple"
 * </pre>
 */
public class NavigableMapSample {
    public static void main(String[] args) {
        // Create a NavigableMap using TreeMap
        NavigableMap<String, Integer> map = new TreeMap<>();

        // Demonstrate adding elements (automatically sorted by keys)
        map.put("Banana", 2);
        map.put("Apple", 1);
        map.put("Cherry", 3);
        map.put("Date", 4);
        map.put("Apple", 5); // Updates existing entry

        System.out.println("Original navigable map: " + map);

        // Demonstrate navigation methods returning entries
        System.out.println("Lower than 'Cherry': " + map.lowerEntry("Cherry"));
        System.out.println("Floor of 'Apricot': " + map.floorEntry("Apricot"));
        System.out.println("Ceiling of 'Apricot': " + map.ceilingEntry("Apricot"));
        System.out.println("Higher than 'Banana': " + map.higherEntry("Banana"));

        // Demonstrate navigation methods returning keys only
        System.out.println("Lower key than 'Cherry': " + map.lowerKey("Cherry"));
        System.out.println("Floor key of 'Apricot': " + map.floorKey("Apricot"));
        System.out.println("Ceiling key of 'Apricot': " + map.ceilingKey("Apricot"));
        System.out.println("Higher key than 'Banana': " + map.higherKey("Banana"));

        // Demonstrate first and last (inherited from SortedMap)
        System.out.println("First entry: " + map.firstEntry());
        System.out.println("Last entry: " + map.lastEntry());

        // Demonstrate subset operations (inherited from SortedMap)
        System.out.println("Entries before 'Cherry': " + map.headMap("Cherry"));
        System.out.println("Entries from 'Banana' onward: " + map.tailMap("Banana"));
        System.out.println("Entries from 'Apple' to 'Cherry': " + map.subMap("Apple", "Date"));
        System.out.println("Entries from 'Banana' to 'Date': " + map.subMap("Apple", false, "Date", true));

        // Demonstrate poll operations
        System.out.println("Poll first: " + map.pollFirstEntry());
        System.out.println("Poll last: " + map.pollLastEntry());
        System.out.println("Map after polling: " + map);

        // Add new elements to see sorting in action
        map.put("Elderberry", 6);
        map.put("Apricot", 7);
        System.out.println("After adding new elements: " + map);

        // Demonstrate key navigation with new elements
        System.out.println("Lower key than 'Elderberry': " + map.lowerKey("Elderberry"));
        System.out.println("Floor key of 'Blueberry': " + map.floorKey("Blueberry"));
        System.out.println("Ceiling key of 'Blueberry': " + map.ceilingKey("Blueberry"));
        System.out.println("Higher key than 'Apricot': " + map.higherKey("Apricot"));

        // Demonstrate descending view
        NavigableMap<String, Integer> descending = map.descendingMap();
        System.out.println("Descending order view: " + descending);

        // Demonstrate key set navigation
        System.out.println("Descending key set: " + map.descendingKeySet());

        // Demonstrate comparator (natural ordering in this case)
        System.out.println("Comparator: " + (map.comparator() == null ? "Natural ordering" : map.comparator()));
    }
}