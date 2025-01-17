package com.sanver.basics.concurrentcollections;

import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static com.sanver.basics.utils.Utils.getThreadInfo;

/**
 * Even though Hashtable is thread safe, it is not very efficient.
 * Another fully synchronized Map, Collections.synchronizedMap, does not exhibit great efficiency either.
 * If we want thread-safety with high throughput under high concurrency, these implementations aren’t the way to go.
 * <p>
 * To solve the problem, the Java Collections Framework introduced ConcurrentMap in Java 1.5.
 * ConcurrentMap is an interface. ConcurrentHashMap is the out-of-box ready ConcurrentMap implementation.
 * <a href="https://www.baeldung.com/java-concurrent-map">Source</a>
 */
public class ConcurrentMapSample {

    public static void main(String[] args) {
        var list = List.of(3, 6, 2, 1);
        ConcurrentMap<String, List<Integer>> result = list.parallelStream()
                .collect(Collectors.groupingByConcurrent(i -> {
                    var key = i%2 == 0 ? "Even" : "Odd";
                    System.out.printf("%d: %-4s %s%n", i, key, getThreadInfo()); // This is to demonstrate grouping is done concurrently
                    return key;
                }));
        System.out.println("ConcurrentMap implementation is ConcurrentHashMap: " + (result instanceof ConcurrentHashMap));
        System.out.println(result);

        var map = new ConcurrentHashMap<Integer, String>(); // Change this to HashMap to see varying sizes.
        int count = 10_000;

        var first = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < count; i++) {
                map.put(i, Integer.toString(i));
            }
        });

        var second = CompletableFuture.runAsync(() -> {
            for (int i = count; i < 2 * count; i++) {
                map.put(i, Integer.toString(i));
            }
        });

        first.join();
        second.join();
        System.out.printf("The element count in the map is %s%n", NumberFormat.getInstance().format(map.size()));
    }
}
