package com.sanver.basics.generics;

import lombok.ToString;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
        // Assume we have a function that maps objects to strings, and another function that maps integers to string,
        // and we want to write a method that uses those functions to print values of elements of a List<Integer>.
        Function<Object, String> toString = "Object: %s"::formatted;
        Function<Integer, String> integerToString = "Number: %d"::formatted;
        var list = List.of(1, 2, 3);
        printInts(list, toString);
        printInts(list, integerToString);

        List<? super B> list1 = new ArrayList<>(List.of("Another string", new A(), new File("file.txt"), new Object())); // We can put any object here, since it accepts super types of B, where Object is also a super type of B and everything is an object.
        list1.add(new B());
        list1.add(new C());
        //list1.add(new Object());
        //list1.add(new A());
        // Although list1 has A and Object elements we can't call add with an A instance or Object instance
        // since what we add should be able to be added to all lists with super types of B, i.e. they need to be able to
        // be added to List<B>, List<A> and List<Object>. This is satisfied only by B or subclasses of B.

        addValue(list1, new C());
        System.out.println(list1);
    }

    /*
     This method should be able to use any function that can map an Integer to a String.
     If we just defined toString parameter type as Function<Integer, String> or Function<? extends Integer, String>,
     then the above toString function in the main method will give a compile error for printInts(List.of(1, 2, 3), toString);.
     If we defined Function<?, String> toString or Function<? extends Object, String>, then toString.apply(item) in the below code will give a compile error.
     If we defined it as Function<Object, String>, then we won't be able to pass a Function<Integer, String>.
     The way to achieve it is to define it as Function<? super Integer, String> which means printInts accepts any
     Function that can accept a super type of Integer (Integer, Number, Serializable, Object etc.) and return String.
     That argument's apply method will accept only Integer or its subtypes, since the toString argument might be a Function<Number, String> or Function<Object, String> or Function<any other super type of Integer, String>.
     Only Integer and its subtypes will work for all possible Function types passed as the toString argument, hence other types will get a compilation error.
    */
    public static void printInts(List<Integer> list, Function<? super Integer, String> toString) {
        for (var item : list) {
            var value = toString.apply(item);
            System.out.println(value);
        }
    }

    public static <T extends B> void addValue(List<? super B> list, T value) {
        var first = list.get(0); // The first will be inferred as an instance of an Object.
        // Object first = list.get(0) will be compiled, but A first = list.get(0) or B first = list.get(0) will not compile,
        // since the list argument passed can be a List<Object> and its elements don't need to be A or B.
        list.add(value);// This will accept only B or its subtypes, since the list argument might be of type List<B>, List<A> or List<Object>.
        // Only B and its subtypes can be added to all these different lists, hence other types will get a compilation error.
        // If we declared the list parameter as List<? extends B> instead,
        // this line won't compile, because the list sent might be List<C> but the value can be an instance of B.

        list.add(new C());// Instead of List<? super B> list in the parameters, if we declared it as List<? extends B>
        // this line won't compile, because the list sent might be List<D> where D also extends B,
        // but C might not necessarily be an instance of D.

        // list.add(new A()); This won't compile, assume the list is of type List<B>, the add method won't accept A, but
        // it will accept B or C instances
        // list.add(new Object()); This won't compile, assume the list is of type List<B>, the add method won't accept
        // object, but it will accept B or C instance.
    }
}
