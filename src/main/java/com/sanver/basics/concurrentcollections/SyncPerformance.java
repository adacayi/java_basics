package com.sanver.basics.concurrentcollections;

import com.sanver.basics.utils.PerformanceComparer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class SyncPerformance {
    // The performance order is
    // 1- Adding to ArrayList inside a synchronized block
    // 2- Collections.synchronizedList
    // 3- Vector
    // 4- CopyOnWriteArrayList
    public static void main(String[] args) {
        var taskSize = 3;
        var increment = 10_000_000; // This will take hours for CopyOnWriteArrayList.
        BiFunction<List<Integer>, String, Runnable> getRunnable = (list, name) -> () -> {
            var threads = IntStream.range(0, taskSize).mapToObj(x -> (Runnable) () -> {
                for (int i = 0; i < increment; i++) {
                    list.add(i + x * increment);
                }
            }).map(Thread::new).toList();
            threads.forEach(Thread::start);
            threads.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.printf("%s list size: %d%n", name, list.size());
        };
        var unsynchronized = getRunnable.apply(new ArrayList<>(), "Unsynchronized");
        var sync = getSync(taskSize, increment, "Sync");
        var collectionsSync = getRunnable.apply(Collections.synchronizedList(new ArrayList<>()), "Collections sync");
        var vector = getRunnable.apply(new Vector<>(), "Vector");
        var copyOnWriteArrayList = getRunnable.apply(Collections.synchronizedList(new CopyOnWriteArrayList<>()), "CopyOnWriteArrayList");
        var comparer = new PerformanceComparer(Map.of(
                unsynchronized, "Unsynchronized",
                sync, "Sync",
                vector, "Vector",
                collectionsSync, "CollectionsSync",
                copyOnWriteArrayList, "CopyOnWriteArrayList"));
        comparer.compare();
    }

    private static Runnable getSync(int taskSize, int increment, String name) {
        return () -> {
            var list = new ArrayList<Integer>();
            var threads = IntStream.range(0, taskSize).mapToObj(x -> (Runnable) () -> {
                for (int i = 0; i < increment; i++) {
                    synchronized (list) {
                        list.add(i + x * increment);
                    }
                }
            }).map(Thread::new).toList();
            threads.forEach(Thread::start);
            threads.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            System.out.printf("%s list size: %d%n", name, list.size());
        };
    }
}
