package com.sanver.basics.concurrentcollections;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;
import static com.sanver.basics.utils.Utils.sleep;
import static com.sanver.basics.utils.Utils.warmup;

public class ConcurrentMapsPerformanceComparison {
    public static void main(String[] args) {
        warmup();
        hashtable();
        concurrentHashMap();
        concurrentSkipListMap();
        synchronizedMap();
    }

    private static void concurrentHashMap() {
        var map = new ConcurrentHashMap<Integer, String>();
        testPerformance(map, "ConcurrentHashMap");
    }

    private static void concurrentSkipListMap() {
        var map = new ConcurrentSkipListMap<Integer, String>();
        testPerformance(map, "ConcurrentSkipListMap");
    }

    private static void synchronizedMap() {
        var map = Collections.synchronizedMap(new HashMap<Integer, String>());
        testPerformance(map, "Collections.synchronizedMap(new HashMap<Integer, String>())");
    }

    private static void hashtable() {
        var map = new Hashtable<Integer, String>();
        testPerformance(map, "Hashtable");
    }

    private static void testPerformance(Map<Integer, String> map, String type) {
        int count = 30_000_000;
        int parallelism = Runtime.getRuntime().availableProcessors() * 2;
        System.out.printf("%nChecking write performance for %s. Putting %,d items asynchronously.%n", type, count);
        sleep(2000);
        var block = count / parallelism + 1;
        measure(() -> IntStream.range(0, parallelism).parallel().forEach(i -> IntStream.range(0, block).forEach(j -> map.put(i * block + j, String.valueOf(i)))));
        sleep(2000);
        System.out.printf("Checking read performance for %s.  Reading %,d items asynchronously.%n", type, count);
        measure(() -> IntStream.range(0, parallelism).parallel().forEach(i -> IntStream.range(0, block).forEach(j -> {
            var key = i * block + j;
            String value;
            if ((value = map.get(key)) == null) {
                throw new AssertionError("Map should contain key" + key);
            } else if (!value.equals(String.valueOf(i))) {
                throw new AssertionError("Map should contain entry (%d, %s)".formatted(key, value));
            }
        })));
        sleep(2000);
    }
}
