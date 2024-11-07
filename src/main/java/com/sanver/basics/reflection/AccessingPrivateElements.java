package com.sanver.basics.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AccessingPrivateElements {
    public static void main(String... args) {
        // When running this code, you might encounter the following error:
        // java.lang.reflect.InaccessibleObjectException: Unable to make field transient java.util.HashMap$Node[] java.util.HashMap.table accessible: module java.base does not "opens java.util" to unnamed module
        // To overcome it, run this with the following jvm options --add-opens java.base/java.util=ALL-UNNAMED
        // https://stackoverflow.com/questions/70756414/java-lang-reflect-inaccessibleobjectexception-unable-to-make-field-private-fina

        // Explanation (source: https://www.baeldung.com/java-modularity):
        // Before Java 9, it was possible to use reflection to examine every type and member in a package, even the private ones.
        // Nothing was truly encapsulated, which can open up all kinds of problems for developers of the libraries.
        // Java 9 enforces strong encapsulation.
        // By default, in Java 9, we will only have access to public classes, methods, and fields in our exported packages.
        // Even if we use reflection to get access to non-public members and call setAccessible(true), we won’t be able to access these members.
        // If we must have access to a module for reflection, and we’re not the owner of that module (i.e., we can’t use the opens…to directive),
        // then it’s possible to use the command line –add-opens option to allow own modules reflection access to the locked down module at runtime.
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
