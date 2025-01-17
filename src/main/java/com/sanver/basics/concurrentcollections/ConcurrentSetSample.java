package com.sanver.basics.concurrentcollections;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

/**
 * Demonstrates various types of concurrent sets available in Java 21.
 *
 * <p>Concurrent sets are thread-safe collections that support safe concurrent access. This class
 * showcases four main implementations:</p>
 *
 * <ul>
 *   <li>{@code ConcurrentHashMap.newKeySet()}:
 *     <ul>
 *       <li>Backed by a ConcurrentHashMap with dummy values.</li>
 *       <li>Provides fast and scalable operations for large sets.</li>
 *       <li>Does not maintain any order of elements.</li>
 *       <li>Suitable for high-concurrency environments requiring frequent updates.</li>
 *     </ul>
 *   </li>
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
 *       <li>Maintains insertion order</li>
 *     </ul>
 *   </li>
 *   <li>{@code java.util.Collections.synchronizedSet(set)} :
 *     <ul>
 *       <li>Provides a synchronized set for use in concurrent environments.</li>
 *       <li>Explicit synchronization required for iteration</li>
 *     </ul>
 *   </li>
 *   <li>{@code java.util.Set.of()} (Immutable Set):
 *     <ul>
 *       <li>Provides an immutable set for use in concurrent environments.</li>
 *       <li>Cannot be modified after creation, ensuring thread-safety.</li>
 *       <li>Ideal for use cases where set content does not change.</li>
 *     </ul>
 *   </li>

 * </ul>
 */
public class ConcurrentSetSample {

    public static void main(String[] args) {
        concurrentHashMapNewKeySet();
        concurrentSkipListSet();
        copyOnWriteArraySet();
        synchronizedSet();
        setOf();
    }

    private static void concurrentHashMapNewKeySet() {
        var set = ConcurrentHashMap.<String>newKeySet();
        addValues(set);
        System.out.printf("%nConcurrentHashMap.newKeySet(): %s%n", set);
        testConcurrency(set, "ConcurrentHashMap.newKeySet()");
    }

    private static void concurrentSkipListSet() {
        var set = new ConcurrentSkipListSet<String>();
        addValues(set);
        System.out.printf("%nConcurrentSkipListSet (Sorted by items): %s%n", set);
        testConcurrency(set, "ConcurrentSkipListSet");
    }

    private static void copyOnWriteArraySet() {
        var set = new CopyOnWriteArraySet<String>();
        addValues(set);
        System.out.printf("%nCopyOnWriteArraySet (Maintains insertion order): %s%n", set);
        testConcurrency(set, "CopyOnWriteArraySet");
    }

    private static void synchronizedSet() {
        Set<String> set = Collections.synchronizedSet(new LinkedHashSet<>());
        addValues(set);
        System.out.printf("%nCollections.synchronizedSet(new LinkedHashSet<>()) (Maintains insertion order): %s%n", set);
        sleep(7000);

        System.out.print("Using iterator     with synchronized block: ");
        synchronized (set) { // Explicit synchronization required for methods using iterators.s
            // This works, because synchronizedSet() actually wraps the original map methods by invoking them through synchronized blocks
            // which use the final set object (e.g. set in this example) as the monitor object, thus the synchronized block here will block all modifications to the map.
            for (var item : set) {
                System.out.printf("%s, ", item);
            }
            sleep(2000);
        }

        System.out.printf("%nUsing forEach() without synchronized block: ");
        set.forEach(item -> System.out.printf("%s, ", item)); // This does not require any synchronized block since the forEach method is also synchronized by the final set object, hence will block any modification when the lock is acquired.
        sleep(2000);
        testConcurrency(set, "Collections.synchronizedSet()");
    }

    private static void setOf() {
        var set = Set.of("c", "B", "a", "A");
        System.out.printf("""
                %nInsert order:
                
                Set.of("c", "B", "a", "A");
                """);
        System.out.printf("%nImmutable Set (Set.of): %s%n", set);
        sleep(7000);
        demonstrateImmutability(set);
    }

    private static void demonstrateImmutability(Set<String> set) {
        try {
            set.add("4"); // UnsupportedOperationException expected
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify an immutable set " + set);
        }
    }

    private static void addValues(Set<String> set) {
        set.add("c");
        set.add("B");
        set.add("a");
        set.add("A");
        printInsertOrder();
    }

    private static void printInsertOrder() {
        System.out.printf("""
                %nInsert order:
                
                set.add("c");
                set.add("B");
                set.add("a");
                set.add("A");
                """);
    }

    private static void testConcurrency(Set<String> set, String type) {
        set.clear();
        sleep(7000);
        System.out.printf("%nTesting concurrency for %s%n", type);
        sleep(3000);
        System.out.println("Putting 1,000 values asynchronously");
        var count = 1000;
        IntStream.range(0, count).parallel().forEach(i -> set.add(String.valueOf(i)));
        sleep(2000);
        System.out.printf("Final size: %,d%n", set.size());
        IntStream.range(0, count).forEach(x -> {
            if (!set.contains(String.valueOf(x))) {
                throw new AssertionError("Set should contain " + x);
            }
        });
        sleep(3000);
    }
}

