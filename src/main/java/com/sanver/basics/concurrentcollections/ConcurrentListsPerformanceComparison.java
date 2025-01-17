package com.sanver.basics.concurrentcollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;
import static com.sanver.basics.utils.Utils.sleep;
import static com.sanver.basics.utils.Utils.warmup;

public class ConcurrentListsPerformanceComparison {
    public static void main(String[] args) {
        warmup();
        vector();
        synchronizedList();
        copyOnWriteArrayList();
    }

    private static void vector() {
        var list = new Vector<String>();
        testPerformance(list, "Vector");
    }

    private static void synchronizedList() {
        var list = Collections.synchronizedList(new ArrayList<String>());
        testPerformance(list, "Collections.synchronizedList(new ArrayList<String>())");
    }

    private static void copyOnWriteArrayList() {
        var list = new CopyOnWriteArrayList<String>();
        System.out.printf("%nNote: CopyOnWriteArrayList performance will take too long to execute. Feel free to cancel at any time%n");
        testPerformance(list, "CopyOnWriteArrayList");
    }

    private static void testPerformance(List<String> list, String type) {
        int count = 30_000_000;
        int parallelism = Runtime.getRuntime().availableProcessors() * 2;
        System.out.printf("%nChecking write performance for %s. Putting %,d items asynchronously.%n", type, count);
        sleep(2000);
        var block = count / parallelism + 1;
        measure(() -> IntStream.range(0, parallelism).parallel().forEach(i -> IntStream.range(0, block).forEach(j -> list.add(String.valueOf(i * block + j)))));
        sleep(2000);
        System.out.printf("Checking read performance for %s.  Reading %,d items asynchronously.%n", type, count);
        list.clear();
        var digitCount = String.valueOf(count).length();
        var format = "%0"+digitCount+"d";
        list.addAll(IntStream.range(0, count).parallel().mapToObj(format::formatted).toList()); // Arrange a sorted list so we can use binary search for read, making read significantly faster.
        measure(() -> IntStream.range(0, count).forEach(item ->{
            if (Collections.binarySearch(list, format.formatted(item)) < 0) { // If we didn't use binary search, reads would take too much time.
                throw new AssertionError("List should contain " + item);
            }
        }));
        sleep(2000);
    }
}
