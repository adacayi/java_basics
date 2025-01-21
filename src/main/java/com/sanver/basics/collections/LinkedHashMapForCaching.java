package com.sanver.basics.collections;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@code LinkedHashMap} class is an implementation of {@code Map} that maintains the order
 * of the entries based on insertion order or access order (if access-order mode is enabled).
 * It extends the {@code HashMap} class and differs by maintaining a doubly-linked list across
 * all entries.
 * <p>
 * This linked list defines the iteration order, which can be the order in which keys were
 * inserted (insertion order), or the order in which keys were last accessed (from least-recently
 * accessed to most-recently accessed), depending on the mode.
 * </p>
 * <p>
 * This class provides all optional map operations and permits {@code null} values and the
 * {@code null} key. Like {@code HashMap}, it provides constant-time performance for basic
 * operations (get and put), assuming the hash function disperses elements properly among the buckets.
 * </p>
 *
 * <p> The iterators of the collection views (i.e., the entry set, key set, and values) are
 * fail-fast: if the map is structurally modified after the iterator is created (except through the
 * iterator's own {@code remove} operation), the iterator will throw a {@code ConcurrentModificationException}.
 * </p>
 *
 * <p> This implementation is not synchronized. If multiple threads access a {@code LinkedHashMap}
 * concurrently, and at least one of the threads modifies the map structurally, it must be synchronized
 * externally. This is typically accomplished by synchronizing on some object that naturally
 * encapsulates the map.
 * </p>
 *
 * <p> This class has an additional feature of removing the eldest entry when a new entry is inserted,
 * if the map is in access-order mode and the {@code removeEldestEntry} method is overridden.
 * </p>
 *
 */
public class LinkedHashMapForCaching {
    public static void main(String[] args) {
        var linkedHashMap = getLinkedHashMap();

        System.out.println("LinkedHashMap which will remove the oldest entry based on access order after its size reaches 3 entries and a new entry is put");
        System.out.println("linkedHashMap.get(1): " + linkedHashMap.get(1));
        System.out.printf("%nPutting 1 = Adam, 2 = James, 3 = Mary%n");
        linkedHashMap.put(1, "Adam");
        linkedHashMap.put(2, "James");
        linkedHashMap.put(3, "Mary");

        System.out.println(linkedHashMap);
        System.out.printf("%nlinkedHashMap.get(1): %s%n", linkedHashMap.get(1));
        System.out.printf("%nlinkedHashMap after linkedHashMap.get(1):%n\tNote that the accessed item is moved to the end of the linked list of the LinkedHashMap%n\t%s%n", linkedHashMap);
        System.out.printf("%nlinkedHashMap.put(2, \"Sam\"): %s%n", linkedHashMap.put(2, "Sam"));
        System.out.printf("%nlinkedHashMap after linkedHashMap.put(2, \"Sam\"):%n\tNote that the updated item is moved to the end of the linked list of the LinkedHashMap%n\t%s%n", linkedHashMap);
        System.out.printf("%nlinkedHashMap.put(4, \"Roy\"): %s%n", linkedHashMap.put(4, "Roy"));
        System.out.printf("%nlinkedHashMap after linkedHashMap.put(4, \"Roy\"): %n\tNote that the first item in the linked list of the LinkedHashMap is removed which is (3,Mary) and the inserted item is inserted to the end of the linked list.%n\t%s%n", linkedHashMap);
    }

    private static LinkedHashMap<Integer, String> getLinkedHashMap() {
        final var maxEntries = 3;
        final var loadFactor = 1;

        // LinkedHashMap extends HashMap, and uses the same implementation to store its elements in an array,
        // but it overrides the get(), newNode(), afterNodeInsertion(), afterNodeAccess() and afterNodeRemoval() methods of the HashMap to also form a linked list
        // with head and tail nodes, which are modified with get (if accessOrder is set to true), put and remove methods.
        // This linked list is used to keep track of the order of the elements (based on insertion order or access order).
        return new LinkedHashMap<>(maxEntries, loadFactor, true) { // Set accessOrder to true and override removeEldestEntry, so that the least accessed element is removed when the
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > maxEntries;
            }
        };
    }
}