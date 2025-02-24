package com.sanver.basics.collections;

import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Demonstrates the usage of the {@link NavigableSet} interface, part of the Java Collections Framework,
 * to maintain a set of unique elements in sorted order with additional navigation capabilities.
 * It extends {@link java.util.SortedSet} and provides methods to find elements relative to others
 * (e.g., closest matches, ceiling, floor) and to navigate the set in both ascending and descending order.
 *
 * <p>The {@link NavigableSet} interface defines the following key methods beyond those in {@link SortedSet}:
 * <ul>
 *   <li>{@code lower(E e)} - Returns the greatest element strictly less than e, or null if none</li>
 *   <li>{@code floor(E e)} - Returns the greatest element less than or equal to e, or null if none</li>
 *   <li>{@code ceiling(E e)} - Returns the least element greater than or equal to e, or null if none</li>
 *   <li>{@code higher(E e)} - Returns the least element strictly greater than e, or null if none</li>
 *   <li>{@code pollFirst()} - Retrieves and removes the first (lowest) element, or null if empty</li>
 *   <li>{@code pollLast()} - Retrieves and removes the last (highest) element, or null if empty</li>
 *   <li>{@code descendingSet()} - Returns a reverse-order view of the set</li>
 *   <li>{@code descendingIterator()} - Returns an iterator over the elements in descending order</li>
 * </ul>
 * <p>It also inherits all methods from {@link java.util.SortedSet} and {@link java.util.Set}.
 *
 * <p>The primary implementations of {@link NavigableSet} in the Java standard library are:
 * <ul>
 *   <li>{@link java.util.TreeSet}: A red-black tree-based implementation that maintains
 *       elements in sorted order with efficient navigation operations.</li>
 *   <li>{@link java.util.concurrent.ConcurrentSkipListSet}: A concurrent, navigable set implementation.</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 *     NavigableSet<String> set = new TreeSet<>();
 *     set.add("Banana");
 *     set.add("Apple");
 *     System.out.println(set.ceiling("Apricot")); // Outputs: "Banana"
 * </pre>
 */
public class NavigableSetSample {
    public static void main(String[] args) {
        // Create a NavigableSet using TreeSet
        NavigableSet<String> set = new TreeSet<>();

        // Demonstrate adding elements (automatically sorted)
        set.add("Banana");
        set.add("Apple");
        set.add("Cherry");
        set.add("Date");
        set.add("Apple"); // Duplicate, will be ignored

        System.out.println("Original navigable set: " + set);

        // Demonstrate navigation methods
        System.out.println("Lower than 'Cherry': " + set.lower("Cherry"));
        System.out.println("Floor of 'Apricot': " + set.floor("Apricot"));
        System.out.println("Ceiling of 'Apricot': " + set.ceiling("Apricot"));
        System.out.println("Higher than 'Banana': " + set.higher("Banana"));

        // Demonstrate first and last (inherited from SortedSet)
        System.out.println("First element: " + set.first());
        System.out.println("Last element: " + set.last());

        // Demonstrate subset operations (inherited from SortedSet)
        System.out.println("Elements before 'Cherry': " + set.headSet("Cherry"));
        System.out.println("Elements from 'Banana' onward: " + set.tailSet("Banana"));
        System.out.println("Elements from 'Apple' to 'Date': " + set.subSet("Apple", "Date"));

        // Demonstrate poll operations
        System.out.println("Poll first: " + set.pollFirst());
        System.out.println("Poll last: " + set.pollLast());
        System.out.println("Set after polling: " + set);

        // Add new elements to see sorting in action
        set.add("Elderberry");
        set.add("Apricot");
        System.out.println("After adding new elements: " + set);

        // Demonstrate descending view
        NavigableSet<String> descending = set.descendingSet();
        System.out.println("Descending order view: " + descending);

        // Demonstrate comparator (natural ordering in this case)
        System.out.println("Comparator: " + (set.comparator() == null ? "Natural ordering" : set.comparator()));
    }
}