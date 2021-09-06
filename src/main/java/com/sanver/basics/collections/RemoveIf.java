package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemoveIf {

  public static void main(String[] args) {
    var list = new ArrayList<>(List.of(1, 2, 3));
    var set = new HashSet<>(Set.of(3, 4, 6));
    list.removeIf(x -> x < 3);
    set.removeIf(x -> x > 5);
    System.out.println(list);
    System.out.println(set);
  }
}
