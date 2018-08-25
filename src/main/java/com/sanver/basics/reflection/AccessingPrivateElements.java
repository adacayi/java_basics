package com.sanver.basics.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AccessingPrivateElements {
    public static void main(String... args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(3, "Abdullah");
        try {
            System.out.println("We cannot directly access neither the table used in HashMap nor the capacity method." +
                    "\nTo do that we will use reflection.");
            Field tableField = HashMap.class.getDeclaredField("table");
            Method capacityMethod = HashMap.class.getDeclaredMethod("capacity");
            tableField.setAccessible(true);// If this was not called we would get an error.
            capacityMethod.setAccessible(true);// If this was not called we would get an error.
            Map.Entry[] table = (Map.Entry[]) tableField.get(map);
            for (Map.Entry entry : table) {
                if (entry == null)
                    continue;

                System.out.printf("Key: %s Value: %s\n", entry.getKey(), entry.getValue());
            }
            System.out.printf("Capacity: %s", capacityMethod.invoke(map));
        } catch (NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
