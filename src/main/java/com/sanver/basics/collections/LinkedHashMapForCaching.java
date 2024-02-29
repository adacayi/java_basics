package com.sanver.basics.collections;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

public class LinkedHashMapForCaching {
    private final int capacity;
    private LinkedHashMap<Integer, Integer> cache;  // Key -> Value Mapping

    // Constructor to initialize capacity
    public LinkedHashMapForCaching(int capacity) {
        this.capacity = capacity;
        // true flag for access-order so LinkedHashMap updates order on 'get'
        this.cache = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(java.util.Map.Entry<Integer, Integer> eldest) {
                // Override to remove the least recently used when cache is full
                return size() > capacity;
            }
        };
    }

    // Get value associated with a key
    public int get(int key) {
        return cache.getOrDefault(key, -1);
    }

    // Set or insert value for a key
    public void set(int key, int value) {
        cache.put(key, value);
    }

    // Function to process queries and return results
    public static List<Integer> solve(int capacity, String queryString) {
        var cache = new LinkedHashMapForCaching(capacity);
        var result = new ArrayList<Integer>();

        var queries = queryString.split("\n");
        for (var query : queries) {
            var parts = query.split(" ");

            if (parts[0].equals("GET")) {
                int key = Integer.parseInt(parts[1]);
                result.add(cache.get(key));
            } else if (parts[0].equals("SET")) {
                int key = Integer.parseInt(parts[1]);
                int value = Integer.parseInt(parts[2]);
                cache.set(key, value);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(solve(2, "GET 1\nSET 1 3\nGET 1\nGET 2\nSET 2 7\nGET 2\nGET 1\nSET 3 9\nGET 1\nGET 2"));
    }
}