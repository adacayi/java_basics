package com.sanver.basics.collections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HashMapSizeChange {
    public static void main(String... args) {
        // When running this code, you might encounter the following error:
        // java.lang.reflect.InaccessibleObjectException: Unable to make field transient java.util.HashMap$Node[] java.util.HashMap.table accessible: module java.base does not "opens java.util" to unnamed module
        // Check comments in AccessingPrivateElements
        Map<Integer, Integer> map = new HashMap<>();
        System.out.println("Initial state of the map");
        System.out.printf("Size: 0 Underlying table is null Threshold: %-3d%n", getThreshold(map));
        System.out.printf("%nWhen allocated, the table length will always be a power of 2 (starting from 16 as the default initial capacity) and when the threshold is exceeded, the table size will double%n");
        System.out.printf("%nPutting 100 entries%n%n");

        for (int i = 0; i < 100; i++) {
            put(i, i, map);
        }

        System.out.printf("%nRemoving 100 entries%n%n");

        for (int i = 0; i < 100; i++) {
            remove(i, map);
        }

        System.out.printf("%nBe aware that the size of the HashMap did not shrink.%n");
        System.out.printf("%nPutting 24 entries%n%n");

        for (int i = 0; i < 24; i++) {
            put(i, i, map);
        }

        System.out.printf("%nTo decrease capacity, we have to generate a new HashMap from the current one." +
                "%nHashMap<Integer, String> newMap = new HashMap<>(map);%n%n");
        HashMap<Integer, Integer> newMap = new HashMap<>(map);
        printMap(newMap);
        System.out.println("Check the constructor public HashMap(Map<? extends K, ? extends V> m) for more details");
    }

    private static void printMap(Map<?, ?> map) {
        System.out.printf("Size: %-3d Underlying table capacity: %-3d Threshold: %-3d%n", map.size(), getCapacity(map), getThreshold(map));
    }

    private static int getCapacity(Map<?, ?> map) {
        var table = getTable(map);
        return table == null ? 0 : table.length;
    }

    private static <K,V> Map.Entry<K, V>[] getTable(Map<K, V> map) {
        Map.Entry<K, V>[] table = null;

        try {
            Field tableField = HashMap.class.getDeclaredField("table");
            tableField.setAccessible(true);
            table = (Map.Entry<K,V>[]) tableField.get(map);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return table;
    }

    private static int getThreshold(Map<?, ?> map) {
        int threshold = 0;

        try {
            Field thresholdField = HashMap.class.getDeclaredField("threshold");
            thresholdField.setAccessible(true);
            threshold = (int)thresholdField.get(map);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return threshold;
    }

    public static <K, V> V put(K key, V value, Map<K, V> map) {
        V result = map.put(key, value);
        printMap(map);
        return result;
    }

    public static <K, V> V remove(K key, Map<K, V> map) {
        V result = map.remove(key);
        printMap(map);
        return result;
    }
}
