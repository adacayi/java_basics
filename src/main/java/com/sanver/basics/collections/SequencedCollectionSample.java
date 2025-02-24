package com.sanver.basics.collections;

import java.util.SequencedCollection;
import java.util.LinkedList;

/**
 * Demonstrates the usage of SequencedCollection interface which was introduced in Java 21
 * to enhance collections with sequence-aware operations.
 * A SequencedCollection provides methods to access elements in a defined order,
 * with support for operations at both ends of the collection.
 *
 * <p>The SequencedCollection interface defines the following methods:
 * <ul>
 *   <li>{@code addFirst(E e)} - Adds an element at the beginning of the collection</li>
 *   <li>{@code addLast(E e)} - Adds an element at the end of the collection</li>
 *   <li>{@code getFirst()} - Returns the first element of the collection</li>
 *   <li>{@code getLast()} - Returns the last element of the collection</li>
 *   <li>{@code removeFirst()} - Removes and returns the first element</li>
 *   <li>{@code removeLast()} - Removes and returns the last element</li>
 *   <li>{@code reversed()} - Returns a reverse-ordered view of the collection</li>
 * </ul>
 * <p>Additional inherited methods from Collection interface are also available.
 *
 * <p>Below are common implementations of {@link java.util.SequencedCollection} in the Java standard library:
 *
 * <ul>
 *   <li>{@link java.util.List}</li>
 *   <li>{@link java.util.Deque}: Note that {@link java.util.Queue} only extends {@link java.util.Collection}</li>
 *   <li>{@link java.util.concurrent.BlockingDeque}</li>
 *   <li>{@link java.util.SequencedSet}</li>
 *   <li>{@link java.util.ArrayList}: A resizable array implementation that supports sequenced operations.
 *       It maintains insertion order and provides efficient random access, making it suitable for
 *       general-purpose sequenced collections.</li>
 *   <li>{@link java.util.LinkedList}: A doubly-linked list implementation that excels at
 *       adding or removing elements at both ends. It implements {@link java.util.Deque} and
 *       {@link java.util.SequencedCollection}, offering efficient first and last element operations.</li>
 *   <li>{@link java.util.ArrayDeque}: A double-ended queue implementation based on a
 *       circular array. It provides high performance for adding and removing elements at both ends,
 *       making it a preferred choice for sequenced operations like stacks and queues.</li>
 *   <li>{@link java.util.TreeSet} (when wrapped or viewed): A sorted set implementation
 *       that maintains elements in a natural or custom order. While not a direct implementation,
 *       it can be adapted to {@link java.util.SequencedCollection} via views or wrappers in specific contexts.</li>
 *   <li>{@link java.util.LinkedHashSet}: A hash set that preserves insertion order.
 *       It combines the benefits of a hash table with sequenced access, supporting operations
 *       like retrieving the first and last elements added.</li>
 * </ul>
 *
 * <p>Example usage:
 * <pre>
 *     SequencedCollection<String> seq = new ArrayList<>();
 *     seq.addLast("Second");
 *     seq.addFirst("First");
 *     System.out.println(seq.getFirst()); // Outputs: "First"
 * </pre>
 *
 */
public class SequencedCollectionSample {
    public static void main(String[] args) {
        // Create a SequencedCollection using LinkedList
        SequencedCollection<String> collection = new LinkedList<>();

        // Demonstrate addFirst and addLast
        collection.addFirst("First");
        collection.addLast("Last");
        collection.addFirst("New First");
        collection.addLast("New Last");

        System.out.println("Original collection: " + collection);

        // Demonstrate getFirst and getLast
        System.out.println("First element: " + collection.getFirst());
        System.out.println("Last element: " + collection.getLast());

        // Demonstrate removeFirst and removeLast
        String removedFirst = collection.removeFirst();
        String removedLast = collection.removeLast();
        System.out.println("Removed first: " + removedFirst);
        System.out.println("Removed last: " + removedLast);
        System.out.println("After removals: " + collection);

        // Demonstrate reversed view
        SequencedCollection<String> reversed = collection.reversed();
        System.out.println("Reversed view: " + reversed);

        // Original collection remains unchanged
        System.out.println("Original after reverse: " + collection);

        // Note that reversed() also returns a SequencedCollection
        reversed.addFirst("newFirst");
        reversed.addLast("newLast");
        System.out.println("reversed after addFirst and addLast: " + reversed);
        System.out.println("Original after reversed modifications: " + collection); // Note that changes to the reversed also changes the original
        System.out.println("reversed.getFirst(): " + reversed.getFirst());
        System.out.println("reversed.getLast(): " + reversed.getFirst());
        System.out.println("reversed.removeFirst(): " + reversed.removeFirst());
        System.out.println("reversed.removeLast(): " + reversed.removeLast());
    }
}