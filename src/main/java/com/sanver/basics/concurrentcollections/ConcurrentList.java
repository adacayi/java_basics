package com.sanver.basics.concurrentcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

/**
 * Provides an overview of thread-safe List implementations available in Java 21.
 * <p>
 * Java offers several concurrent List implementations, each with different
 * synchronization mechanisms and performance characteristics. Below are the main
 * thread-safe List implementations as of Java 21:
 * </p>
 *
 * <ul>
 *   <li><b>CopyOnWriteArrayList</b> - A thread-safe variant of ArrayList where all
 *       mutative operations create a new copy of the underlying array. It is optimized
 *       for read-heavy workloads.</li>
 *
 *   <li><b>Collections.synchronizedList(List&lt;T&gt; list)</b> - A wrapper that
 *       synchronizes all method calls on a given List instance. This is a simple way
 *       to make any List thread-safe but requires external synchronization during iteration.</li>
 *
 *   <li><b>Vector</b> (Legacy) - A fully synchronized implementation of List.
 *       Although thread-safe, it suffers from performance issues in highly concurrent environments
 *       due to method-level synchronization. Generally replaced by {@code CopyOnWriteArrayList} or
 *       {@code synchronizedList()}.</li>
 *   <li>{@code java.util.List.of()} (Immutable List):
 *     <ul>
 *       <li>Provides an immutable list for use in concurrent environments.</li>
 *       <li>Cannot be modified after creation, ensuring thread-safety.</li>
 *       <li>Ideal for use cases where list content does not change.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <p><b>Comparison of Concurrent Lists:</b></p>
 * <table border="1">
 *   <tr>
 *     <th>List Implementation</th>
 *     <th>Synchronization Mechanism</th>
 *     <th>Best Use Case</th>
 *   </tr>
 *   <tr>
 *     <td>CopyOnWriteArrayList</td>
 *     <td>Copy-on-write strategy</td>
 *     <td>Read-heavy workloads where iteration should not be affected by modifications</td>
 *   </tr>
 *   <tr>
 *     <td>Collections.synchronizedList()</td>
 *     <td>Method-level synchronization</td>
 *     <td>General-purpose thread safety, manual synchronization required for iteration</td>
 *   </tr>
 *   <tr>
 *     <td>Vector</td>
 *     <td>Fully synchronized methods</td>
 *     <td>Legacy support, replaced by modern alternatives</td>
 *   </tr>
 * </table>
 *
 * <p>For concurrent queue-based alternatives, consider using
 * {@code java.util.concurrent.BlockingQueue} implementations such as
 * {@code ConcurrentLinkedQueue} or {@code LinkedBlockingQueue}.</p>
 *
 * @author ChatGPT
 * @version Java 21
 */
public class ConcurrentList {
    public static void main(String[] args) {
        copyOnWriteArrayList();
        synchronizedList();
        vector();
        listOf();
    }

    private static void vector() {
        var vector = new Vector<String>();
        addValues(vector);
        System.out.printf("%nVector: %s%n", vector);
        testConcurrency(vector, "Vector");
    }

    private static void synchronizedList() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        addValues(list);
        System.out.printf("%nCollections.synchronizedList(new ArrayList<>()): %s%n", list);
        sleep(7000);

        System.out.print("Using iterator     with synchronized block: ");
        synchronized (list) { // Explicit synchronization required for methods using iterators.
            // This works, because synchronizedList() actually wraps the original map methods by invoking them through synchronized blocks
            // which use the final list object (e.g. list in this example) as the monitor object, thus the synchronized block here will block all modifications to the map.
            for (var item : list) {
                System.out.printf("%s, ", item);
            }
            sleep(2000);
        }

        System.out.printf("%nUsing forEach() without synchronized block: ");
        list.forEach(item -> System.out.printf("%s, ", item)); // This does not require any synchronized block since the forEach method is also synchronized by the final list object, hence will block any modification when the lock is acquired.
        sleep(2000);
        testConcurrency(list, "Collections.synchronizedList()");
    }

    private static void listOf() {
        var list = List.of("c", "B", "a", "A");
        System.out.printf("""
                %nInsert order:
                
                Set.of("c", "B", "a", "A");
                """);
        System.out.printf("%nImmutable List (List.of): %s%n", list);
        sleep(7000);
        demonstrateImmutability(list);
    }

    private static void copyOnWriteArrayList() {
        var list = new CopyOnWriteArrayList<String>();
        addValues(list);
        System.out.printf("%nCopyOnWriteArrayList: %s%n", list);
        testConcurrency(list, "CopyOnWriteArrayList");
    }

    private static void demonstrateImmutability(List<String> list) {
        try {
            list.add("4"); // UnsupportedOperationException expected
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify an immutable list " + list);
        }
    }

    private static void addValues(List<String> list) {
        list.add("c");
        list.add("B");
        list.add("a");
        list.add("A");
        printInsertOrder();
    }

    private static void printInsertOrder() {
        System.out.printf("""
                %nInsert order:
                
                list.add("c");
                list.add("B");
                list.add("a");
                list.add("A");
                """);
    }

    private static void testConcurrency(List<String> list, String type) {
        list.clear();
        sleep(7000);
        System.out.printf("%nTesting concurrency for %s%n", type);
        sleep(3000);
        System.out.println("Adding 1,000 values asynchronously");
        var count = 1000;
        IntStream.range(0, count).parallel().forEach(i -> list.add(String.valueOf(i)));
        sleep(2000);
        System.out.printf("Final size: %,d%n", list.size());
        IntStream.range(0, count).forEach(x -> {
            if (!list.contains(String.valueOf(x))) {
                throw new AssertionError("List should contain " + x);
            }
        });
        sleep(3000);
    }
}
