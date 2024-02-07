package com.sanver.basics.collections;

import java.util.HashMap;
import java.util.Map;

public class HashMapSample {

    public static void main(String[] args) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("Ahmet", "Sanver"); // This returns the old value, if there was an existing key, otherwise returns null
        hashMap.put("Mustafa", null); // If we used Hashtable instead, null value would give an exception
        System.out.printf("putting a new value. Old value is %s\n", hashMap.put("Muhammed", "Ali")); // null is returned from put
        // since the key did not exist before
        System.out.println(hashMap.get("Ahmet"));
        System.out.println("Hashmap contains key Ahmet: " + hashMap.containsKey("Ahmet"));
        System.out.println("Hashmap contains value Sanver: " + hashMap.containsValue("Sanver"));
        System.out.println("Hashmap contains key Salih: " + hashMap.containsKey("Salih"));
        System.out.println("Hashmap contains value White: " + hashMap.containsValue("White"));
        System.out.println(hashMap.get("Salih"));// If there is no such key null is returned.
        hashMap.forEach((k, v) -> System.out.printf("Key= %s, Value=%s\n", k, v));
        System.out.println(hashMap.put("Muhammed", "Sanver"));// If a key already exists the old value is returned with
        // put and the value is updated with the new value. Else null is returned.
        hashMap.forEach((k, v) -> System.out.printf("Key= %s, Value=%s\n", k, v));
        System.out.printf("Keys: %s\n", hashMap.keySet());
        System.out.printf("Values: %s\n", hashMap.values());
        System.out.printf("Entries: %s\n", hashMap.entrySet());
        System.out.print("\nRemoving Ahmet\n");
        System.out.printf("%s", hashMap.remove("Ahmet")); // Remove returns the value of the key. If the key is
        // not present, it will return null
        System.out.print("\nRemoving non-existent key\n");
        System.out.printf("%s", hashMap.remove("Salih")); // Remove returns the value of the key. If the key is
        // not present, it will return null
        System.out.print("\n");
        hashMap.entrySet().forEach(entry -> System.out.printf("Key= %s, Value=%s\n", entry.getKey(), entry.getValue()));
    }
}
