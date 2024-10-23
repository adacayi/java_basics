package com.sanver.basics.collections;

import java.util.HashMap;
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
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @see HashMap
 * @see Map
 */
public class LinkedHashMapForCaching {
    public static void main(String[] args) {
        final var maxEntries = 3;
        final var loadFactor = 0.75f;

        // LinkedHashMap extends HashMap, and uses the same implementation to store its elements in an array,
        // but it overrides the newNode(), afterNodeAccess() and afterNodeInsertion() methods of the HashMap to also form a linked list
        // with head and tail nodes, which are modified with put and remove.
        // This linked list is used to keep track of the order of the elements.
        // Check the newNode() method in LinkedHashMap, which is called from HashMap.putVal when a new value is inserted.
        var linkedHashMap = new LinkedHashMap<Integer, String>((int) (maxEntries / loadFactor) + 1, loadFactor, true) { // Set accessOrder to true and override removeEldestEntry, so that the least accessed element is removed when the
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > maxEntries;
            }
        };

        System.out.println("LinkedHashMap which will remove the oldest entry based on access order after its size reaches 3 entries and a new entry is put");
        System.out.println("linkedHashMap.get(1): " + linkedHashMap.get(1));
        System.out.printf("%nPutting 1 = Adam, 2 = James, 3 = Mary%n");
        linkedHashMap.put(1, "Adam");
        linkedHashMap.put(2, "James");
        linkedHashMap.put(3, "Mary");

        System.out.println(linkedHashMap);
        System.out.printf("%nlinkedHashMap.get(1): %s%n", linkedHashMap.get(1));
        System.out.printf("%nlinkedHashMap after linkedHashMap.get(1):%n\tNote that the order is changed based on the access order%n\t%s%n", linkedHashMap);
        System.out.printf("%nlinkedHashMap.get(1): %s%n", linkedHashMap.get(1));
        System.out.printf("%nlinkedHashMap after linkedHashMap.get(1):%n\t%s%n", linkedHashMap);
        System.out.printf("%nlinkedHashMap.get(2): %s%n", linkedHashMap.get(2));
        System.out.printf("%nlinkedHashMap after linkedHashMap.get(2):%n\tNote that the order is changed based on the access order, not the access count (1 is accessed twice, but 2 is accessed once, but more recently)%n\t%s%n", linkedHashMap);
        System.out.printf("%nlinkedHashMap.put(4, \"Roy\"): %s%n", linkedHashMap.put(4, "Roy"));
        System.out.printf("%nlinkedHashMap after linkedHashMap.put(4, \"Roy\"): %n\tNote that the least recently accessed entry is removed which is 3.%n\t%s%n", linkedHashMap);
    }
}