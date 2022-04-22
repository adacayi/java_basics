package com.sanver.basics.generics;

import java.io.File;
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
    List<? super B> list1 = List.of("Another string", new A(), new File(""), new Object()); // We can put anything here
    list1.add(new B());
    list1.add(new C());
    //list1.add(new Object());
    //list1.add(new A());
    // Although list1 has A and Object elements we can't call add with an A instance or Object instance
    // since what we add should be able to be added to all lists with super types of B, i.e. they need to be able to
    // be added to List<B>, List<A> and List<Object>. This is satisfied only by B or subclasses of B.

    addValue(list1, new C());
  }

  public static <T extends B> void addValue(List<? super B> list, T value) {
    var element = list.get(0); // The element will be inferred as an instance of an Object.
    list.add(value);// Instead of List<? super B> list in the parameters, if we declared it as List<? extends B>
    // this line won't compile, because the list sent might be List<C> but the value can be an instance of B
    list.add(new C());// Instead of List<? super B> list in the parameters, if we declared it as List<? extends B>
    // this line won't compile, because the list sent might be List<D> where D also extends B,
    // but C might not necessarily be an instance of D

//    list.add(new A()); This won't compile, assume the list is of type List<B>, the add method won't accept A, but
//    it will accept B or C instances
//    list.add(new Object()); This won't compile, assume the list is of type List<B>, the add method won't accept
//    object, but it will accept B or C instance
    // In short list methods should work for all super types of B List<B> List<A> and List<Object>.
    // So value cannot be an instance of A or Object, since these can't be added to List<B>.
  }
}
