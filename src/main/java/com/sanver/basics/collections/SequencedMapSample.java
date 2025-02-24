package com.sanver.basics.collections;

import java.util.SequencedMap;
import java.util.TreeMap;

/**
 * Demonstrates the usage of the SequencedMap interface, introduced in Java 21,
 * to enhance map collections with sequence-aware operations.
 * A SequencedMap provides methods to access entries in a defined order,
 * supporting operations at both ends of the map based on its entry sequence.
 *
 * <p>The SequencedMap interface defines the following key methods:
 * <ul>
 *   <li>{@code firstEntry()} - Returns the first entry (key-value pair) in the map</li>
 *   <li>{@code lastEntry()} - Returns the last entry in the map</li>
 *   <li>{@code pollFirstEntry()} - Removes and returns the first entry</li>
 *   <li>{@code pollLastEntry()} - Removes and returns the last entry</li>
 *   <li>{@code putFirst(K k, V v)} - Adds or replaces the first entry with the specified key-value pair</li>
 *   <li>{@code putLast(K k, V v)} - Adds or replaces the last entry with the specified key-value pair</li>
 *   <li>{@code sequencedKeySet()} - Returns a SequencedSet view of the keys</li>
 *   <li>{@code sequencedValues()} - Returns a SequencedCollection view of the values</li>
 *   <li>{@code sequencedEntrySet()} - Returns a SequencedSet view of the entries</li>
 *   <li>{@code reversed()} - Returns a reverse-ordered view of the map</li>
 * </ul>
 * <p>Additional inherited methods from the {@link java.util.Map} interface are also available.
 *
 * <p>Common implementations of {@link java.util.SequencedMap} in the Java standard library include:
 * <ul>
 *   <li>{@link java.util.SortedMap}</li>
 *   <li>{@link java.util.NavigableMap}</li>
 *   <li>{@link java.util.concurrent.ConcurrentNavigableMap}</li>
 *   <li>{@link java.util.TreeMap} A red-black tree-based implementation that maintains
 *       entries in natural key order (or a custom comparator). It provides efficient ordered
 *       operations and supports sequenced access to first and last entries.</li>
 *   <li>{@link java.util.LinkedHashMap} A hash table and linked list implementation that
 *       preserves insertion order. It supports sequenced operations and provides predictable
 *       iteration order, making it suitable for applications requiring stable entry sequencing.</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 *     SequencedMap<Integer, String> map = new TreeMap<>();
 *     map.put(2, "Two");
 *     map.putFirst(1, "One");
 *     map.putLast(3, "Three");
 *     System.out.println(map.firstEntry()); // Outputs: 1=One
 * </pre>
 *
 */
public class SequencedMapSample {
    public static void main(String[] args) {
        // Create a SequencedMap using TreeMap (natural key order)
        SequencedMap<Integer, String> map = new TreeMap<>();

        // Add entries using standard put
        map.put(3, "Three");
        map.put(2, "Two");
        map.put(4, "Four");
        map.put(1, "One");

        // putFirst and putLast will throw UnsupportedOperationException, since the order is the natural key order is defined in the TreeMap and entries cannot be put in a different order.
//        map.putFirst(1, "One");  // Inserts as first due to natural ordering
//        map.putLast(4, "Four");  // Inserts as last due to natural ordering

        System.out.println("Original map: " + map);

        // Demonstrate firstEntry and lastEntry
        System.out.println("First entry: " + map.firstEntry());
        System.out.println("Last entry: " + map.lastEntry());

        // Demonstrate pollFirstEntry and pollLastEntry
        var removedFirst = map.pollFirstEntry();
        var removedLast = map.pollLastEntry();
        System.out.println("Removed first: " + removedFirst);
        System.out.println("Removed last: " + removedLast);
        System.out.println("After removals: " + map);

        // Demonstrate reversed view
        SequencedMap<Integer, String> reversed = map.reversed(); // the returned object is not technically a TreeMap instance itself—it’s a view that implements the SequencedMap interface.
        // Internally, this reversed view is backed by the original TreeMap, and its ordering is determined by reversing the comparator or natural ordering used by the original TreeMap.
        // Thus, it also does not support putFirst and putLast.
        System.out.println("Reversed view: " + reversed);
        reversed.put(6, "Six");
        reversed.put(5, "Five");
//        reversed.putLast(7, "Seven"); // This will result in an UnsupportedOperationException
        System.out.println("Reversed view: " + reversed);

        // Demonstrate sequenced views
        System.out.println("Sequenced keys: " + map.sequencedKeySet());
        System.out.println("Sequenced values: " + map.sequencedValues());
        System.out.println("Sequenced entries: " + map.sequencedEntrySet());

        // Original map remains unchanged after reversed view
        System.out.println("Original after reverse: " + map);
    }
}