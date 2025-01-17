package com.sanver.basics.concurrentcollections;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;
import static com.sanver.basics.utils.Utils.sleep;
import static com.sanver.basics.utils.Utils.warmup;

public class ConcurrentSetsPerformanceComparison {
    public static void main(String[] args) {
        warmup();
        concurrentHashMapNewKeySet();
        concurrentSkipListSet();
        synchronizedSet();
        copyOnWriteArraySet();
    }

    private static void concurrentHashMapNewKeySet() {
        var set = ConcurrentHashMap.<String>newKeySet();
        testPerformance(set, "ConcurrentHashMap.newKeySet()");
    }

    private static void concurrentSkipListSet() {
        var set = new ConcurrentSkipListSet<String>();
        testPerformance(set, "ConcurrentSkipListSet");
    }

    private static void synchronizedSet() {
        var set = Collections.synchronizedSet(new HashSet<String>());
        testPerformance(set, "Collections.synchronizedSet(new HashSet<String>())");
    }

    private static void copyOnWriteArraySet() {
        var set = new CopyOnWriteArraySet<String>();
        System.out.printf("%nNote: CopyOnWriteArraySet performance will take too long to execute. Feel free to cancel at any time%n");
        testPerformance(set, "CopyOnWriteArraySet");
    }

    private static void testPerformance(Set<String> set, String type) {
        int count = 30_000_000;
        int parallelism = Runtime.getRuntime().availableProcessors() * 2;
        System.out.printf("%nChecking write performance for %s. Putting %,d items asynchronously.%n", type, count);
        sleep(2000);
        var block = count / parallelism + 1;
        measure(() -> IntStream.range(0, parallelism).parallel().forEach(i -> IntStream.range(0, block).forEach(j -> set.add(String.valueOf(i * block + j)))));
        sleep(2000);
        System.out.printf("Checking read performance for %s.  Reading %,d items asynchronously.%n", type, count);
        measure(() -> IntStream.range(0, parallelism).parallel().forEach(i -> IntStream.range(0, block).forEach(j -> {
            var item = String.valueOf(i * block + j);
            if (!set.contains(item)) {
                throw new AssertionError("Set should contain " + item);
            }
        })));
        sleep(2000);
    }
}
