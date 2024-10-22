package com.sanver.basics.collections;

import java.util.HashMap;

public class HashMapSample {

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Ahmet", "Sanver"); // This returns the old value, if there was an existing key, otherwise returns null
        hashMap.put("Mustafa", null); // If we used Hashtable instead, null value would give an exception
        System.out.printf("putting a new value. Old value is %s%n", hashMap.put("Muhammed", "Ali")); // null is returned from put
        // since the key did not exist before
        System.out.println(hashMap.get("Ahmet"));
        System.out.println("Hashmap contains key Ahmet: " + hashMap.containsKey("Ahmet"));
        System.out.println("Hashmap contains value Sanver: " + hashMap.containsValue("Sanver"));
        System.out.println("Hashmap contains key Salih: " + hashMap.containsKey("Salih"));
        System.out.println("Hashmap contains value White: " + hashMap.containsValue("White"));
        System.out.println(hashMap.get("Salih"));// If there is no such key null is returned.
        hashMap.forEach((k, v) -> System.out.printf("Key= %s, Value=%s%n", k, v));
        System.out.println(hashMap.put("Muhammed", "Sanver"));// If a key already exists the old value is returned with
        // put and the value is updated with the new value. Else null is returned.
        hashMap.forEach((k, v) -> System.out.printf("Key= %s, Value=%s%n", k, v));
        System.out.printf("Keys: %s%n", hashMap.keySet());
        System.out.printf("Values: %s%n", hashMap.values());
        System.out.printf("Entries: %s%n", hashMap.entrySet());
        System.out.printf("%nRemoving Ahmet%n");
        System.out.printf("%s", hashMap.remove("Ahmet")); // Remove returns the value of the key. If the key is
        // not present, it will return null
        System.out.printf("%nRemoving non-existent key%n");
        System.out.printf("%s", hashMap.remove("Salih")); // Remove returns the value of the key. If the key is
        // not present, it will return null
        System.out.println();
        hashMap.entrySet().forEach(entry -> System.out.printf("Key= %s, Value=%s%n", entry.getKey(), entry.getValue()));

        System.out.println();
        System.out.println("putIfAbsent puts a new value and returns null if the key does not exist or the key exists and the old value is null. If the key exists and the old value is not null it returns the old value only.");
        System.out.println("putIfAbsent(\"Mustafa\", \"Sanver\") result: " + hashMap.putIfAbsent("Mustafa", "Sanver"));
        System.out.println(hashMap);

        System.out.println();
        var computed = hashMap.computeIfPresent("Mustafa", (k, v) -> v + " modified with " + k); // If the key is present and the value is not null updates the value with the result of the BiFunction and returns the updated value, else just returns null.
        System.out.println("hashMap.computeIfPresent(\"Mustafa\", (k, v) -> v + \" modified with \" + k) result: " + computed);
        System.out.println(hashMap);

        System.out.println();
        System.out.println("hashMap.computeIfPresent(\"Mustafa\", (k, v) -> null) removes the mapping since the BiFunction returns null");
        hashMap.computeIfPresent("Mustafa", (k, v) -> null); // If the BiFunction returns null removes the mapping
        System.out.println(hashMap);

        System.out.println();
        hashMap.put("Ibrahim", null);
        System.out.println(hashMap);
        computed = hashMap.computeIfPresent("Ibrahim", (k, v) -> v + " modified with " + k); // If the key is present and the value is not null updates the value with the result of the BiFunction and returns the updated value, else just returns null.
        System.out.println("hashMap.computeIfPresent(\"Ibrahim\", (k, v) -> v + \" modified with \" + k); Since the value is null, it won't change the mapping. result: " + computed);
        System.out.println(hashMap);


        System.out.println();
        computed = hashMap.computeIfAbsent("Ibrahim", k -> null); // If the key is not present, or the key exists and the value is null, computes the new value and updates the mapping, returning the new value, otherwise returns null. If the computed value is null, returns null and does not change the mapping.
        System.out.println("hashMap.computeIfAbsent(\"Ibrahim\", k -> null) result: " + computed);
        System.out.println(hashMap);

        System.out.println();
        computed = hashMap.computeIfAbsent("Ibrahim", k -> "new value for " + k);
        System.out.println("hashMap.computeIfAbsent(\"Ibrahim\", k -> \"new value for \" + k); result: " + computed);
        System.out.println(hashMap);

        System.out.println();
        computed = hashMap.computeIfAbsent("Salih", k -> null);
        System.out.println("hashMap.computeIfAbsent(\"Salih\", k -> null) result: " + computed);
        System.out.println(hashMap);
    }
}
