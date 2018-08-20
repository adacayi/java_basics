package com.sanver.basics.concurrentcollections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ConcurrentMapSample {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 6, 2, 1));
        ConcurrentMap<String, List<Integer>> result = list.parallelStream()
                .collect(Collectors.groupingByConcurrent(i -> i % 2 == 0 ? "Even" : "Odd"));
        result.forEach((k, v) -> System.out
                .println(k + " " + v.stream().map(x -> x.toString()).collect(Collectors.joining(", "))));

        Map map = new ConcurrentHashMap<String,Integer>(); // Change this to HashMap to see varying sizes.
        int count = 10_000;
        Runnable first = () -> {
            for (int i = 0; i < count; i++) {
                map.put(i, Integer.toString(i));
            }
        };
        Runnable second = () -> {
            for (int i = count; i < 2*count; i++) {
                map.put(i, Integer.toString(i));
            }
        };
        Thread t1 = new Thread(first);
        Thread t2 = new Thread(second);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("The element count in the map is %s\n", map.size());
    }
}
