package com.sanver.basics.collections;

import java.util.HashMap;
import java.util.Map;

public class HashMapSample {

	public static void main(String[] args) {
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("Ahmet", "Sanver");
		hashMap.put("Mustafa", null); // If we used Hashtable instead, null value would give an exception
		hashMap.put("Muhammed", "oldValue");
		System.out.println(hashMap.get("Ahmet"));
		System.out.println(hashMap.get("Salih"));// If there is no such key null is returned.
		System.out.print("Keys: ");
		System.out.println(String.join(", ", hashMap.keySet()));
		hashMap.forEach((k, v) -> System.out.printf("Key= %s, Value=%s\n", k, v));
		System.out.println(hashMap.put("Muhammed", "Sanver"));// If a key already exists the old value is returned with
																// put and the value is updated with the new value.
		hashMap.forEach((k, v) -> System.out.printf("Key= %s, Value=%s\n", k, v));

	}
}
