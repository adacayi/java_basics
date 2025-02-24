package com.sanver.basics.collections;

import java.util.SequencedSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Demonstrates the usage of the {@link SortedSet} interface, part of the Java Collections Framework,
 * to maintain a set of unique elements in sorted order. It extends {@link SequencedSet} but, unlike SequencedSet, which preserves
 * insertion order and provides sequence-aware operations, SortedSet ensures elements are
 * ordered according to their natural ordering (e.g., alphabetical for Strings, numerical for Integers)
 * or a custom Comparator provided at creation time.
 *
 * <p>The {@link SortedSet} interface extends {@link java.util.Set} and {@link SequencedSet} and defines the following key methods:
 * <ul>
 *   <li>{@code first()} - Returns the first (lowest) element in the set</li>
 *   <li>{@code last()} - Returns the last (highest) element in the set</li>
 *   <li>{@code headSet(E toElement)} - Returns a view of the portion of the set strictly less than toElement</li>
 *   <li>{@code tailSet(E fromElement)} - Returns a view of the portion of the set greater than or equal to fromElement</li>
 *   <li>{@code subSet(E fromElement, E toElement)} - Returns a view of the portion of the set from fromElement (inclusive) to toElement (exclusive)</li>
 *   <li>{@code comparator()} - Returns the Comparator used to order the set, or null if natural ordering is used</li>
 * </ul>
 * <p>Additional inherited methods from Set and Collection interfaces are also available.
 *
 * <p>The primary implementations of {@link java.util.SortedSet} in the Java standard library are:
 * <ul>
 *   <li>{@link java.util.NavigableSet}</li>
 *   <li>{@link java.util.TreeSet}: A red-black tree-based implementation that maintains
 *       elements in sorted order. It provides efficient operations for adding, removing, and
 *       retrieving elements while ensuring uniqueness and order.</li>
 *   <li>{@link java.util.concurrent.ConcurrentSkipListSet}</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 *     SortedSet<String> set = new TreeSet<>();
 *     set.add("Banana");
 *     set.add("Apple");
 *     System.out.println(set.first()); // Outputs: "Apple"
 * </pre>
 */
public class SortedSetSample {
    public static void main(String[] args) {
        // Create a SortedSet using TreeSet
        SortedSet<Integer> set = new TreeSet<>();

        // Demonstrate adding elements (automatically sorted)
        set.add(3);
        set.add(2);
        set.add(4);
        set.add(5);
        set.add(2); // Duplicate, will be ignored

//        set.addFirst("Orange"); // This will throw UnsupportedOperationException, since we cannot add an element to a random place in a SortedSet.
//        set.addLast("Watermelon"); // This will throw UnsupportedOperationException, since we cannot add an element to a random place in a SortedSet.
        System.out.println("Original sorted set: " + set);

        // Demonstrate first and last
        System.out.println("set.first(): " + set.first());
        System.out.println("set.last(): " + set.last());

        // Demonstrate first and last
        System.out.println("set.getFirst(): " + set.getFirst()); // getFirst() calls first()
        System.out.println("set.getLast(): " + set.getLast()); // getLast() calls last

        // Demonstrate subset operations
        SortedSet<Integer> head = set.headSet(4);
        System.out.println("head = set.headSet(4): " + head);

        SortedSet<Integer> tail = set.tailSet(3);
        System.out.println("tail = set.tailSet(3): " + tail);

        SortedSet<Integer> sub = set.subSet(2, 4);
        System.out.println("sub = set.subSet(2, 4): " + sub);


        // Demonstrate removal of first and last (using remove)
        System.out.println("set.removeFirst(): " + set.removeFirst());
        System.out.println("set.removeLast(): " + set.removeLast());
        print(head, tail, sub, set); // This is to demonstrate that sub sets are affected as well.

        // Demonstrating changes to a set/subset changes other subsets and the original set
        set.add(7);
        tail.add(6);
        tail.add(5);
//        sub.add(8); we can only add numbers between the first and last element of the original set to the subsets, (e.g. head, tail, sub)
        System.out.println("set.add(7);");
        System.out.println("tail.add(6);");
        System.out.println("tail.add(5);");
        print(head, tail, sub, set);

        // Add new elements to see sorting in action

        System.out.println("After adding new elements: " + set);

        // Demonstrate comparator (natural ordering in this case)
        System.out.println("Comparator: " + (set.comparator() == null ? "Natural ordering" : set.comparator()));
    }

    private static void print(SortedSet<Integer> head, SortedSet<Integer> tail, SortedSet<Integer> sub, SortedSet<Integer> set) {
        System.out.println("set: " + set);
        System.out.println("head: " + head);
        System.out.println("tail: " + tail);
        System.out.println("sub: " + sub);
    }
}