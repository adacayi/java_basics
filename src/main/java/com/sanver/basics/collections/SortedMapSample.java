package com.sanver.basics.collections;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Demonstrates the usage of the {@link SortedMap} interface, part of the Java Collections Framework,
 * to maintain a map of key-value pairs with keys in sorted order. Unlike a regular Map, which does not
 * guarantee order, {@link SortedMap} ensures keys are ordered according to their natural ordering
 * (e.g., alphabetical for Strings, numerical for Integers) or a custom Comparator provided at creation time.
 *
 * <p>The {@link SortedMap} interface extends {@link java.util.SequencedMap} and defines the following key methods:
 * <ul>
 *   <li>{@code firstKey()} - Returns the first (lowest) key in the map</li>
 *   <li>{@code lastKey()} - Returns the last (highest) key in the map</li>
 *   <li>{@code headMap(K toKey)} - Returns a view of the portion of the map with keys strictly less than toKey</li>
 *   <li>{@code tailMap(K fromKey)} - Returns a view of the portion of the map with keys greater than or equal to fromKey</li>
 *   <li>{@code subMap(K fromKey, K toKey)} - Returns a view of the portion of the map from fromKey (inclusive) to toKey (exclusive)</li>
 *   <li>{@code comparator()} - Returns the Comparator used to order the keys, or null if natural ordering is used</li>
 * </ul>
 * <p>Additional inherited methods from the Map interface are also available.
 *
 * <p>The primary implementations of {@link java.util.SortedMap} in the Java standard library are:
 * <ul>
 *   <li>{@link java.util.NavigableMap}</li>
 *   <li>{@link java.util.concurrent.ConcurrentNavigableMap}</li>
 *   <li>{@link java.util.TreeMap}: A red-black tree-based implementation that maintains
 *       keys in sorted order. It provides efficient operations for adding, removing, and
 *       retrieving key-value pairs while ensuring key uniqueness and order.</li>
 *   <li>{@link java.util.concurrent.ConcurrentSkipListMap}</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 *     SortedMap<String, Integer> map = new TreeMap<>();
 *     map.put("Banana", 2);
 *     map.put("Apple", 1);
 *     System.out.println(map.firstKey()); // Outputs: "Apple"
 * </pre>
 */
public class SortedMapSample {
    public static void main(String[] args) {
        // Create a SortedMap using TreeMap
        SortedMap<Integer, String> map = new TreeMap<>();

        // Demonstrate adding elements (keys automatically sorted)
        map.put(3, "Three");
        map.put(2, "Two");
        map.put(4, "Four");
        map.put(5, "Five");
        map.put(2, "Two Updated"); // Duplicate key, will update value

//        map.putFirst(1, "One"); // This will throw UnsupportedOperationException, since we cannot add an element to a random place in a SortedMap.
//        map.putLast(7, "Seven"); // This will throw UnsupportedOperationException, since we cannot add an element to a random place in a SortedMap.

        System.out.println("Original sorted map: " + map);

        // Demonstrate firstKey and lastKey
        System.out.println("map.firstKey(): " + map.firstKey());
        System.out.println("map.lastKey(): " + map.lastKey());

        // Demonstrate subset operations
        SortedMap<Integer, String> head = map.headMap(4);
        System.out.println("head = map.headMap(4): " + head);

        SortedMap<Integer, String> tail = map.tailMap(3);
        System.out.println("tail = map.tailMap(3): " + tail);

        SortedMap<Integer, String> sub = map.subMap(2, 4);
        System.out.println("sub = map.subMap(2, 4): " + sub);

        // Demonstrate removal of first and last entries
        System.out.println("Removing first entry: " + map.remove(map.firstKey()));
        System.out.println("Removing last entry: " + map.remove(map.lastKey()));
        print(head, tail, sub, map); // Show effect on subsets

        // Demonstrating changes to map/submap affect other submaps and original map
        map.put(7, "Seven");
        tail.put(6, "Six");
        tail.put(5, "Five Updated");
        System.out.println("map.put(7, \"Seven\");");
        System.out.println("tail.put(6, \"Six\");");
        System.out.println("tail.put(5, \"Five\");");
        print(head, tail, sub, map);

        // Demonstrate comparator (natural ordering in this case)
        System.out.println("Comparator: " + (map.comparator() == null ? "Natural ordering" : map.comparator()));
    }

    private static void print(SortedMap<Integer, String> head,
                              SortedMap<Integer, String> tail,
                              SortedMap<Integer, String> sub,
                              SortedMap<Integer, String> map) {
        System.out.println("map: " + map);
        System.out.println("head: " + head);
        System.out.println("tail: " + tail);
        System.out.println("sub: " + sub);
    }
}
