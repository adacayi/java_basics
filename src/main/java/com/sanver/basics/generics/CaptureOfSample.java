package com.sanver.basics.generics;

import java.util.ArrayList;
import java.util.List;
import lombok.ToString;

public class CaptureOfSample {

  @ToString
  public static class A {

  }

  @ToString
  public static class B extends A {

  }

  public static void main(String[] args) {
    List<A> list = new ArrayList<>(List.of(new A(), new B()));
    var sublist = sublist(list);
//    sublist.add(new A()); These won't compile, since the compiler represents the returned type as ? capture of A list,
//    which is a new type for the compiler that just extends A, hence we cannot add A to that list and nor B.
//    sublist.add(new B());
  }

  public static List<? extends A> sublist(List<? extends A> list) {
    return list.subList(1, list.size());
  }
}
