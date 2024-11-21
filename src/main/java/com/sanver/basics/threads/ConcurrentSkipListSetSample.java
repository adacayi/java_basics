package com.sanver.basics.threads;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;

/**
 * Demonstrates the features of the ConcurrentSkipListSet in Java.
 *
 * <p>
 * The <code>ConcurrentSkipListSet</code> is a thread-safe, scalable, and sorted set
 * based on a concurrent skip list data structure. It implements the <code>NavigableSet</code>
 * interface and provides:
 * <ol>
 *     <li>Thread-safe access for multiple threads without needing external synchronization.</li>
 *     <li>Elements in natural order or using a custom comparator if specified.</li>
 *     <li>Efficient <code>O(log n)</code> time complexity for basic operations like add, remove, and search.</li>
 *     <li>Support for navigational methods like <code>ceiling</code>, <code>floor</code>, <code>higher</code>, <code>lower</code>.</li>
 * </ol>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Maintains sorted order of elements.</li>
 *     <li>Concurrent access for both reads and updates.</li>
 *     <li>Useful for high-concurrency scenarios requiring ordered sets.</li>
 * </ul>
 *
 * <h2>Main Methods:</h2>
 * <ul>
 *     <li><code>add(E e)</code>: Adds an element to the set if it is not already present.</li>
 *     <li><code>remove(Object o)</code>: Removes a specific element from the set.</li>
 *     <li><code>ceiling(E e)</code>: Finds the least element greater than or equal to the given element.</li>
 *     <li><code>floor(E e)</code>: Finds the greatest element less than or equal to the given element.</li>
 *     <li><code>higher(E e)</code>: Finds the least element strictly greater than the given element.</li>
 *     <li><code>lower(E e)</code>: Finds the greatest element strictly less than the given element.</li>
 *     <li><code>pollFirst()</code>: Removes and returns the smallest element.</li>
 *     <li><code>pollLast()</code>: Removes and returns the largest element.</li>
 * </ul>
 */

public class ConcurrentSkipListSetSample {
    private static final ConcurrentSkipListSet<String> values = new ConcurrentSkipListSet<>(); // Change this to HashSet to see the final set size being indeterminate because HashSet is not thread-safe and the elements of the set are not ordered according to string comparison but by hashcode and the capacity of the HashSet.
    // Use ConcurrentHashMap.newKeySet() to see unordered results.

    public static void main(String[] args) {
        var latch = new CountDownLatch(1);
        IntConsumer populate = i -> {
            uncheck(() -> latch.await());
            IntStream.range(0, 100).forEach(j -> values.add("%03d".formatted(100 * i + j))); // %03d is used, so that the order of the strings are the same as their corresponding integer values.
        };

        var futures = IntStream.range(0, 10).mapToObj(i -> CompletableFuture.runAsync(() -> populate.accept(i))).toArray(CompletableFuture[]::new);
        latch.countDown();
        CompletableFuture.allOf(futures).join();
        System.out.println("values.remove(\"001\") : " + values.remove("001"));
        System.out.println("values.ceiling(\"001\"): " + values.ceiling("001"));
        System.out.println("values.ceiling(\"002\"): " + values.ceiling("002"));
        System.out.println("values.floor(\"001\")  : " + values.floor("001"));
        System.out.println("values.floor(\"000\")  : " + values.floor("000"));
        System.out.println("values.higher(\"002\") : " + values.higher("002"));
        System.out.println("values.lower(\"000\")  : " + values.lower("000"));
        System.out.println("values.pollFirst()   : " + values.pollFirst());
        System.out.println("values.pollLast()    : " + values.pollLast());
        System.out.println("values.addAll(Set.of(\"000\", \"001\", \"002\", \"999\")): " + values.addAll(Set.of("000", "001", "002", "999"))); // Returns true if this collection changed as a result of the call
        System.out.printf("%nSize: %,d%n", values.size());
        System.out.println(values);
    }
}
