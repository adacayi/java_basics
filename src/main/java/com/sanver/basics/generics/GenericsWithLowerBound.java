package com.sanver.basics.generics;

import java.util.ArrayList;
import java.util.List;
import lombok.ToString;

public class GenericsWithLowerBound {

  @ToString
  static class A {

    String getValue() {
      return "Class A";
    }
  }

  @ToString
  static class B extends A {

    @Override
    String getValue() {
      return "Class B";
    }
  }

  @ToString
  static class C extends B {

    @Override
    String getValue() {
      return "Class C";
    }
  }

  public static void main(String[] args) {
    var list1 = addValue(new ArrayList<>(List.of(new Object())), new B());
    var list2 = addValue(new ArrayList<>(List.of(new A())), new B());
//    var list3 = addValue(new ArrayList<>(List.of(new Object())), new Object()); These won't compile
//    var list4 = addValue(new ArrayList<>(List.of(new A())), new A());
//    var list5 = addValue(new ArrayList<>(List.of(new B())), new A());
    var list6 = addValue(new ArrayList<>(List.of(new B())), new B());
    var list7 = addValue(new ArrayList<>(List.of(new C())), new B());
    var list8 = addValue(new ArrayList<>(List.of(new Object())), new C());
    var list9 = addValue(new ArrayList<>(List.of(new A())), new C());
    var list10 = addValue(new ArrayList<>(List.of(new B())), new C());
    var list11 = addValue(new ArrayList<>(List.of(new C())), new C());
    var list12 = addValue(new ArrayList<>(List.of(new String("Some String"))), new C());
    System.out.println(list1);
    System.out.println(list2);
    System.out.println(list6);
    System.out.println(list7);
    System.out.println(list8);
    System.out.println(list9);
    System.out.println(list10);
    System.out.println(list11);
    System.out.println(list12);
    List<? super B> list13 = List.of("Another string", new Object());
    //list13.add(new Object()); Although list13 has object element we can't call add with an object instance since the method can only take B or its subclasses
    // to be able to compile for all List<? super B> i.e. List<B>, List<A> and List<Object>
  }

  public static <T extends B> List<? super B> addValue(List<? super B> list, T value) {
    list.add(value);
    list.add(new C());
//    list.add(new A()); This won't compile, assume the list is of type List<B>, the add method won't accept A, but
//    it will accept B or C instances
//    list.add(new Object()); This won't compile, assume the list is of type List<B>, the add method won't accept
//    object, but it will accept B or C instance
    // In short list methods should work for all super types of B List<B> List<A> and List<Object>.
    // So value cannot be an instance of A or Object, since these can't be added to List<B>.
    return list;
  }
}
