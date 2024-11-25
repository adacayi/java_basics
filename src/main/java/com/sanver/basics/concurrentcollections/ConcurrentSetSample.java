package com.sanver.basics.concurrentcollections;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Demonstrates various types of concurrent sets available in Java 21.
 *
 * <p>Concurrent sets are thread-safe collections that support safe concurrent access. This class
 * showcases four main implementations:</p>
 *
 * <ul>
 *   <li>{@code ConcurrentSkipListSet}:
 *     <ul>
 *       <li>Backed by a ConcurrentSkipListMap.</li>
 *       <li>Maintains elements in a sorted order.</li>
 *       <li>Good for cases requiring frequent range queries or ordered traversal.</li>
 *       <li>Slower than other implementations for basic operations like add or remove.</li>
 *     </ul>
 *   </li>
 *   <li>{@code CopyOnWriteArraySet}:
 *     <ul>
 *       <li>Backed by a CopyOnWriteArrayList.</li>
 *       <li>Good for cases with frequent iteration and infrequent updates.</li>
 *       <li>Each modification (add/remove) creates a new copy of the underlying array, which is costly.</li>
 *       <li>Ideal for scenarios with more reads than writes.</li>
 *     </ul>
 *   </li>
 *   <li>{@code ConcurrentHashMap.newKeySet()}:
 *     <ul>
 *       <li>Backed by a ConcurrentHashMap with dummy values.</li>
 *       <li>Provides fast and scalable operations for large sets.</li>
 *       <li>Does not maintain any order of elements.</li>
 *       <li>Suitable for high-concurrency environments requiring frequent updates.</li>
 *     </ul>
 *   </li>
 *   <li>{@code java.util.Collections.synchronizedSet(set)} :
 *     <ul>
 *       <li>Provides a synchronized set for use in concurrent environments.</li>
 *       <li>Explicit synchronization required for iteration</li>
 *     </ul>
 *   </li>
 * </ul>
 */
public class ConcurrentSetSample {

    public static void main(String[] args) {
        // ConcurrentSkipListSet example
        Set<Integer> skipListSet = new ConcurrentSkipListSet<>();
        skipListSet.add(3);
        skipListSet.add(1);
        skipListSet.add(2);
        System.out.println("ConcurrentSkipListSet (Sorted): " + skipListSet);

        // CopyOnWriteArraySet example
        Set<String> copyOnWriteSet = new CopyOnWriteArraySet<>();
        copyOnWriteSet.add("A");
        copyOnWriteSet.add("B");
        copyOnWriteSet.add("A"); // Duplicate element will not be added
        System.out.println("CopyOnWriteArraySet: " + copyOnWriteSet);

        // ConcurrentHashMap.newKeySet() example
        Set<String> concurrentHashSet = ConcurrentHashMap.newKeySet();
        concurrentHashSet.add("X");
        concurrentHashSet.add("Y");
        concurrentHashSet.add("Z");
        System.out.println("ConcurrentHashMap KeySet: " + concurrentHashSet);

        // ConcurrentHashMap.newKeySet() example with predefined capacity
        Set<Integer> preSizedSet = ConcurrentHashMap.newKeySet(100);
        preSizedSet.add(10);
        preSizedSet.add(20);
        preSizedSet.add(30);
        System.out.println("Pre-sized ConcurrentHashMap KeySet: " + preSizedSet);

        // Collections.synchronizedSet()
        // As of Java 21, there is no direct concurrent set implementation that maintains insertion order, similar to LinkedHashSet. Alternatively you can wrap a LinkedHashSet with Collections.synchronizedSet
        Set<Integer> synchronizedSet = Collections.synchronizedSet(new LinkedHashSet<>());

        synchronizedSet.add(3);
        synchronizedSet.add(2);
        synchronizedSet.add(1);

        synchronized (synchronizedSet) { // Explicit synchronization required for iteration
            for (var item : synchronizedSet) {
                System.out.println(item);
            }
        }
    }
}

