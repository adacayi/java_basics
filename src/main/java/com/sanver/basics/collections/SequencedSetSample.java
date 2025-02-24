package com.sanver.basics.collections;

import java.util.LinkedHashSet;
import java.util.SequencedSet;

/**
 * Demonstrates the usage of {@link SequencedSet} interface, introduced in Java 21,
 * to enhance sets with sequence-aware operations while maintaining uniqueness.
 * It extends {@link java.util.SequencedCollection}.
 * A {@link SequencedSet} provides methods to access elements in a defined order,
 * with support for operations at both ends of the set, and ensures no duplicate elements.
 *
 * <p>The {@link SequencedSet} interface inherits from {@link java.util.SequencedCollection} and defines the following methods:
 * <ul>
 *   <li>{@code addFirst(E e)} - Adds an element at the beginning if not already present</li>
 *   <li>{@code addLast(E e)} - Adds an element at the end if not already present</li>
 *   <li>{@code getFirst()} - Returns the first element of the set</li>
 *   <li>{@code getLast()} - Returns the last element of the set</li>
 *   <li>{@code removeFirst()} - Removes and returns the first element</li>
 *   <li>{@code removeLast()} - Removes and returns the last element</li>
 *   <li>{@code reversed()} - Returns a reverse-ordered view of the set</li>
 * </ul>
 * <p>Additional inherited methods from Set and Collection interfaces are also available.
 *
 * <p>Common implementations of {@link java.util.SequencedSet} in the Java standard library include:
 * <ul>
 *   <li>{@link java.util.NavigableSet}</li>
 *   <li>{@link java.util.SortedSet}</li>
 *   <li><b>{@link java.util.LinkedHashSet}</b>: A hash set that preserves insertion order.
 *       It combines the uniqueness of a set with sequenced access, supporting operations
 *       like retrieving the first and last elements added.</li>
 *   <li><b>{@link java.util.TreeSet}</b> (via views or wrappers): A sorted set implementation
 *       that maintains elements in a natural or custom order. It can be adapted to
 *       {@link java.util.SequencedSet} in specific contexts, though it primarily focuses on sorting.</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 *     SequencedSet<String> set = new LinkedHashSet<>();
 *     set.addLast("Second");
 *     set.addFirst("First");
 *     System.out.println(set.getFirst()); // Outputs: "First"
 * </pre>
 */
public class SequencedSetSample {
    public static void main(String[] args) {
        // Create a SequencedSet using LinkedHashSet
        SequencedSet<String> set = new LinkedHashSet<>();

        // Demonstrate addFirst and addLast
        set.addFirst("First"); // Note, unlike the Collection.add() which returns boolean, addFirst and addLast don't return anything.
        set.addLast("Last");
        set.addFirst("New Last");
        set.addFirst("New First");
        set.addLast("New Last"); // Although duplicate, it will change the order of the New Last as the last element. Comment out to see the difference.

        System.out.println("Original set: " + set);

        // Demonstrate getFirst and getLast
        System.out.println("First element: " + set.getFirst());
        System.out.println("Last element: " + set.getLast());

        // Demonstrate removeFirst and removeLast
        String removedFirst = set.removeFirst();
        String removedLast = set.removeLast();
        System.out.println("Removed first: " + removedFirst);
        System.out.println("Removed last: " + removedLast);
        System.out.println("After removals: " + set);

        // Demonstrate reversed view
        SequencedSet<String> reversed = set.reversed();
        System.out.println("Reversed view: " + reversed);

        // Original set remains unchanged
        System.out.println("Original after reverse: " + set);

        // Note that reversed() also returns a SequencedSet
        reversed.addFirst("newFirst"); // Adds to end of original set
        reversed.addLast("newLast");   // Adds to start of original set
        System.out.println("Reversed after addFirst and addLast: " + reversed);
        System.out.println("Original after reversed modifications: " + set); // Note that changes to the reversed also changes the original
        System.out.println("reversed.getFirst(): " + reversed.getFirst());
        System.out.println("reversed.getLast(): " + reversed.getLast());
        System.out.println("reversed.removeFirst(): " + reversed.removeFirst());
        System.out.println("reversed.removeLast(): " + reversed.removeLast());
        System.out.println("Final original set: " + set);
    }
}