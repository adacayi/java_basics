package com.sanver.basics.stream_api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ToMapSample {

  static class Person {

    private String name;
    private String lastName;

    public Person(String name, String lastName) {
      this.name = name;
      this.lastName = lastName;
    }
  }

  public static void main(String[] args) {
    var people = List.of(
        new Person("Muhammed", "Ali"),
        new Person("Ahmet", "Yildiz"),
        new Person("Salih", "Yildiz"),
        new Person("Metin", "Yildiz"));
    var map1 = people.stream().collect(Collectors.toMap(x -> x.name, x -> x.lastName));
//    var map2=people.stream().collect(Collectors.toMap(x->x.lastName,x->x.name)); This would give an error because
//    of multiple values for a given key
    var map2 = people
        .stream()
        .collect(Collectors.toMap(x -> x.lastName,
            x -> x.name,
            (x1, x2) -> x2,
            LinkedHashMap::new)); // this will return a LinkedHashMap and also resolve the conflicts by getting the
    // last value.
    System.out.println(map1);
    System.out.println(map2);
  }
}
