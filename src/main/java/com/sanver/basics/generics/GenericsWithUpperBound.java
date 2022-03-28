package com.sanver.basics.generics;

import java.util.List;
import java.util.stream.Collectors;

public class GenericsWithUpperBound {

  static class A {

    String getValue() {
      return "Class A";
    }
  }

  static class B extends A {

    @Override
    String getValue() {
      return "Class B";
    }
  }

  public static void main(String[] args) {
    System.out.println(getValue(List.of(new A())));
    System.out.println(getValue(List.of(new B())));
  }

  public static List<String> getValue(List<? extends A> list) {
    return list.stream().map(A::getValue).collect(Collectors.toList());
  }
}
