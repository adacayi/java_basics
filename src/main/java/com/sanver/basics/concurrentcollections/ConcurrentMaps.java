package com.sanver.basics.concurrentcollections;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

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
 *     <p>
 *       A {@code ConcurrentSkipListMap} is a thread-safe, sorted map that is implemented using a
 *       <strong>skip list</strong>. A skip list is a probabilistic data structure that allows fast
 *       search, insertion, and deletion, similar to a balanced tree but with hierarchical linked lists.
 *     </p>
 *
 *     <h2>Features of Skip List:</h2>
 *     <ul>
 *         <li>Consists of multiple levels of linked lists.</li>
 *         <li>The lowest level contains all elements in sorted order.</li>
 *         <li>Higher levels serve as express lanes, skipping over multiple elements for fast access.</li>
 *         <li>Randomization determines the height of each node, ensuring a balanced structure.</li>
 *     </ul>
 *
 *     <h2>Why Use ConcurrentSkipListMap?</h2>
 *     <ul>
 *         <li>Provides <strong>O(log n)</strong> time complexity for search, insert, and delete operations.</li>
 *         <li>Offers <strong>efficient concurrent access</strong> with fine-grained locking.</li>
 *         <li>Maintains a <strong>sorted order</strong>, making it useful for range queries.</li>
 *         <li>Supports lock-free reads, improving performance compared to {@link ConcurrentHashMap}.</li>
 *     </ul>
 *
 *     <h2>Comparison with Other Data Structures:</h2>
 *     <table border="1">
 *         <tr>
 *             <th>Feature</th>
 *             <th>{@code ConcurrentSkipListMap} (Skip List)</th>
 *             <th>{@code TreeMap} (Red-Black Tree)</th>
 *             <th>{@code ConcurrentHashMap}</th>
 *         </tr>
 *         <tr>
 *             <td>Ordering</td>
 *             <td>Yes (Sorted)</td>
 *             <td>Yes (Sorted)</td>
 *             <td>No (Unordered)</td>
 *         </tr>
 *         <tr>
 *             <td>Time Complexity (Search)</td>
 *             <td>O(log n)</td>
 *             <td>O(log n)</td>
 *             <td>O(1) (for get/put)</td>
 *         </tr>
 *         <tr>
 *             <td>Concurrent Access</td>
 *             <td>Lock-free reads, fine-grained locks</td>
 *             <td>Single-threaded (needs external sync)</td>
 *             <td>Thread-safe with segments</td>
 *         </tr>
 *         <tr>
 *             <td>Best Use Case</td>
 *             <td>High-concurrency, ordered data, range queries</td>
 *             <td>Single-threaded ordered storage</td>
 *             <td>High-throughput unordered storage</td>
 *         </tr>
 *      </table>
 *   </li>
 *   <li>{@code java.util.Collections.synchronizedMap(map)} :
 *     <ul>
 *       <li>Provides a synchronized map for use in concurrent environments.</li>
 *       <li>Explicit synchronization required for iteration</li>
 *     </ul>
 *   </li>
 *   <li>{@code java.util.Map.of()}, {@code java.util.Map.ofEntries()} (Immutable Map):
 *     <ul>
 *       <li>Provides an immutable map for use in concurrent environments.</li>
 *       <li>Cannot be modified after creation, ensuring thread-safety.</li>
 *       <li>Ideal for use cases where map content does not change.</li>
 *       <li>{@code Map.of()} is limited to 10 key-value pairs, while there is no such limit for {@code Map.ofEntries()}</li>
 *     </ul>
 *   </li>
 * </ul>
 */
public class ConcurrentMaps {

    private static final String CHERRY = "Cherry";
    private static final String BANANA = "Banana";
    private static final String APPLE = "Apple";

    public static void main(String[] args) {
        concurrentHashMap();
        concurrentSkipListMap();
        synchronizedMap();
        mapOf();
        mapOfEntries();
    }

    private static void concurrentHashMap() {
        var map = new ConcurrentHashMap<String, Integer>();
        putValues(map);
        System.out.printf("%nConcurrentHashMap: %s%n", map);
        testConcurrency(map, "ConcurrentHashMap");
    }

    private static void concurrentSkipListMap() {
        var map = new ConcurrentSkipListMap<String, Integer>();
        putValues(map);
        System.out.printf("%nConcurrentSkipListMap (Sorted by key): %s%n", map);
        testConcurrency(map, "ConcurrentSkipListMap");
    }

    private static void synchronizedMap() {
        // As of Java 21, there is no direct concurrent map implementation that maintains insertion order, similar to LinkedHashMap. Alternatively you can wrap a LinkedHashMap with Collections.synchronizedMap().
        Map<String, Integer> map = Collections.synchronizedMap(new LinkedHashMap<>());
        putValues(map);
        System.out.printf("%nCollections.synchronizedMap(new LinkedHashMap<>()) (Maintains insertion order): %s%n", map);
        sleep(7000);

        System.out.print("Using iterator     with synchronized block: ");
        synchronized (map) { // Explicit synchronization required for methods using iterators.s
            // This works, because synchronizedMap() actually wraps the original map methods by invoking them through synchronized blocks
            // which use the final map object (e.g. map in this example) as the monitor object, thus the synchronized block here will block all modifications to the map.
            for (var entry : map.entrySet()) {
                System.out.printf("%s -> %d, ", entry.getKey(), entry.getValue());
            }
            sleep(7000);
        }

        System.out.printf("%nUsing forEach() without synchronized block: ");
        map.forEach((key, value) -> System.out.printf("%s -> %s, ", key, value)); // This does not require any synchronized block since the forEach method is also synchronized by the final map object, hence will block any modification when the lock is acquired.
        sleep(7000);
        System.out.printf("%nUsing entrySet().forEach() without synchronized block: ");
        map.entrySet().forEach(entry -> System.out.printf("%s -> %s, ", entry.getKey(), entry.getValue())); // This does not require any synchronized block since the returned entrySet is a synchronized set (check the entrySet() implementation) which uses the final map object as the monitor, hence will block any modification when the lock is acquired.
        testConcurrency(map, "Collections.synchronizedMap()");
    }

    private static void mapOf() {
        var immutableMapFromMapOf = Map.of(
                CHERRY, 3,
                BANANA, 1,
                APPLE, 2
        );
        System.out.printf("""
                %nInsert order:
                
                Map.of(
                        CHERRY, 3,
                        BANANA, 1,
                        APPLE, 2
                );
                """);
        System.out.printf("%nImmutable Map (Map.of): %s%n", immutableMapFromMapOf);
        sleep(7000);
        demonstrateImmutability(immutableMapFromMapOf);
    }

    private static void mapOfEntries() {
        var immutableMapFromMapOfEntries = Map.ofEntries(
                Map.entry(CHERRY, 3),
                Map.entry(BANANA, 1),
                Map.entry(APPLE, 2)
        );
        System.out.printf("""
                %nInsert order:
                
                Map.ofEntries(
                        Map.entry(CHERRY, 3),
                        Map.entry(BANANA, 1),
                        Map.entry(APPLE, 2)
                );
                """);
        System.out.printf("%nImmutable Map (Map.ofEntries): %s%n", immutableMapFromMapOfEntries);
        sleep(7000);
        demonstrateImmutability(immutableMapFromMapOfEntries);
    }

    private static void demonstrateImmutability(Map<String, Integer> map) {
        try {
            map.put(APPLE, 4); // UnsupportedOperationException expected
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify an immutable map " + map);
        }
    }

    private static void putValues(Map<String, Integer> map) {
        map.put(CHERRY, 3);
        map.put(BANANA, 1);
        map.put(APPLE, 2);
        printInsertOrder();
    }

    private static void printInsertOrder() {
        System.out.printf("""
                %nInsert order:
                
                map.put(CHERRY, 3);
                map.put(BANANA, 1);
                map.put(APPLE, 2);
                """);
    }

    private static void testConcurrency(Map<String, Integer> map, String type) {
        map.clear();
        sleep(7000);
        System.out.printf("%nTesting concurrency for %s%n", type);
        sleep(3000);
        System.out.println("Putting 1,000 values asynchronously");
        var count = 1000;
        IntStream.range(0, count).parallel().forEach(i -> map.put(String.valueOf(i), i));
        sleep(2000);
        System.out.printf("Final size: %,d%n", map.size());
        var entrySet = map.entrySet();
        IntStream.range(0, count).forEach(x-> {
            var entry = Map.entry(String.valueOf(x), x);
            if (!entrySet.contains(entry)) {
                throw new AssertionError("Map should contain " + entry);
            }
        });
        sleep(3000);
    }
}

