package com.sanver.basics.concurrentcollections;

import com.sanver.basics.utils.PerformanceComparer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

public class SyncPerformance {
    // The performance order is
    // 1- Adding to ArrayList without any synchronization
    // 2- Adding to ArrayList inside a synchronized block
    // 3- Collections.synchronizedList
    // 4- Vector
    // 5- CopyOnWriteArrayList

    public static final String FORMAT = "%-34s";
    private static final int TASK_SIZE = 3;
    private static final int INCREMENT = 10_000_000; // This will take hours for CopyOnWriteArrayList, so the code for this is commented out. You can uncomment to test it.
    private static final ExecutorService POOL = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // This pool is used to assure all futures are completed before the program exits.

    public static void main(String[] args) {
        var unsynchronized = getRunnable(new ArrayList<>(), "Unsynchronized ArrayList");
        var sync = getSync();
        var collectionsSync = getRunnable(Collections.synchronizedList(new ArrayList<>()), "Collections.synchronizedList");
        var vector = getRunnable(new Vector<>(), "Vector");
//        var copyOnWriteArrayList = getRunnable(new CopyOnWriteArrayList<>(), "CopyOnWriteArrayList");

        var comparer = new PerformanceComparer(Map.of(
                unsynchronized, String.format(FORMAT, "Unsynchronized ArrayList"),
                sync, String.format(FORMAT, "Synchronized block with ArrayList"),
                vector, String.format(FORMAT, "Vector"),
                collectionsSync, String.format(FORMAT, "Collections.synchronizedList")
//                ,copyOnWriteArrayList, "CopyOnWriteArrayList"
        ));

        comparer.compare();
        System.out.printf("%n%n");
        POOL.shutdown(); // WriteListSizeAsync will be executed before the main thread finishes.
    }

    private static Runnable getRunnable(List<Integer> list, String name) {
        return () -> {
            var futures = IntStream.range(0, TASK_SIZE)
                    .mapToObj(x -> CompletableFuture.runAsync(() -> {
                        for (int i = 0; i < INCREMENT; i++) {
                            list.add(i + x * INCREMENT);
                        }
                    }, POOL))
                    .toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(futures).join();
            writeListSizeAsync(list, name);
        };
    }

    private static Runnable getSync() {
        return () -> {
            var list = new ArrayList<Integer>();
            var futures = IntStream.range(0, TASK_SIZE)
                    .mapToObj(x -> CompletableFuture.runAsync(() -> {
                        for (int i = 0; i < INCREMENT; i++) {
                            synchronized (list) {
                                list.add(i + x * INCREMENT);
                            }
                        }
                    }, POOL))
                    .toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(futures).join();
            writeListSizeAsync(list, "Synchronized block with ArrayList");
        };
    }

    public static void writeListSizeAsync(List<?> list, String name) {
        CompletableFuture.runAsync(() -> {
            sleep(5000); // This is to make sure that task finish times for all different lists are written first.
            System.out.printf(FORMAT + " list size: %s%n", name, NumberFormat.getInstance().format(list.size()));
        }, POOL);
    }
}
