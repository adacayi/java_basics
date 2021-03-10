package com.sanver.basics.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class LinkedHashMapSample {
  //LinkedHashMap preserves the order of records inserted

  public static void main(String[] args) {
    var orderedAsInput = new LinkedHashMap<String,String>();
    orderedAsInput.put("3","1");
    orderedAsInput.put("2","5");
    orderedAsInput.put("4","2");
    var hashMap = new HashMap<String, String>();
    hashMap.put("3","1");
    hashMap.put("2","5");
    hashMap.put("4","2");

    System.out.println(orderedAsInput);
    System.out.println(hashMap);
  }
}
