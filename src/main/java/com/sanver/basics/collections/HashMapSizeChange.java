package com.sanver.basics.collections;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HashMapSizeChange {
    public static void main(String... args) {
        // When running this code, you might encounter the following error:
        // java.lang.reflect.InaccessibleObjectException: Unable to make field transient java.util.HashMap$Node[] java.util.HashMap.table accessible: module java.base does not "opens java.util" to unnamed module
        // To overcome it, run this with the following jvm options --add-opens java.base/java.util=ALL-UNNAMED
        // https://stackoverflow.com/questions/70756414/java-lang-reflect-inaccessibleobjectexception-unable-to-make-field-private-fina
        Map<Integer, String> map = new HashMap<>();
        System.out.println("Initial capacity");
        System.out.printf("Size : %-3d Capacity: %-3d\n", map.size(), getCapacity(map));
        System.out.println("\nAdding 100 elements");

        for (int i = 0; i < 100; i++) {
            add(i, map);
        }

        System.out.println("\nRemoving 100 elements");

        for (int i = 0; i < 100; i++) {
            remove(i, map);
        }

        System.out.println("\nBe aware that the size of the HashMap did not shrink.");
        System.out.println("\nAdding 24 elements");

        for (int i = 0; i < 24; i++) {
            add(i, map);
        }

        System.out.println("\nTo decrease capacity, we have to generate a new HashMap from the current one." +
                "\nHashMap<Integer, String> newMap = new HashMap<>(map);\n");
        HashMap<Integer, String> newMap = new HashMap<>(map);
        System.out.printf("Size : %-3d Capacity: %-3d\n", newMap.size(), getCapacity(newMap));
    }

    private static int getCapacity(Map<?, ?> map) {
        Map.Entry[] table = new Map.Entry[0];
        try {
            Field tableField = HashMap.class.getDeclaredField("table");
            tableField.setAccessible(true);
            table = (Map.Entry[]) tableField.get(map);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return table == null ? 0 : table.length;
    }

    public static <K, V> V add(K key, Map<K, V> map) {
        V result = map.put(key, null);
        System.out.printf("Size : %-3d Capacity: %-3d\n", map.size(), getCapacity(map));
        return result;
    }

    public static <K, V> V remove(K key, Map<K, V> map) {
        V result = map.remove(key);
        System.out.printf("Size : %-3d Capacity: %-3d\n", map.size(), getCapacity(map));
        return result;
    }
}
