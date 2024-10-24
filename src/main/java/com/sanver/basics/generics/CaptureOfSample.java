package com.sanver.basics.generics;

import java.util.ArrayList;
import java.util.List;

public class CaptureOfSample {

    public static void main(String[] args) {
        List<A> list = new ArrayList<>(List.of(new A(1), new B(2), new A(3)));
        var sub = subList(list);
//    sub.add(new A(4)); // These won't compile, since the compiler represents the returned type as List<capture of ? extends A>,
//    which is a new type for the compiler that just extends A, hence we cannot add A to that list and nor B.
//    sub.add(new B(5));

//        var constructor = sub.get(0).getClass().getConstructors()[0];
//        var newElement = constructor.newInstance(2); // This returns capture of ? and thus newElement will be inferred as an Object.
//        sub.add(newElement); // This won't work as well. Error: incompatible types: Object cannot be converted to capture#1 of ? extends A
// In fact, we cannot add any elements to this list, because capture of ? extends A is a new type which is not defined.
        // What we can do is copy this list to a List<A> and then we can add new elements to it:
        var subA = new ArrayList<A>(sub);
        subA.add(new A(4));
        subA.add(new A(5));
        System.out.println("List: " + list);
        System.out.println("sub : " + sub);
        System.out.println("subA: " + subA);

        List<A> list2 = new ArrayList<>(List.of(new A(1), new B(2), new C(3)));
        var subList = firstTwoElements(list2);
//        subList.add(new Object()); // This won't compile. Required type: capture of ? super B, so only the new type "capture of ? super B" or its subclasses will work, which are effectively B and any class extending B.
//        subList.add(new A(3)); // This won't compile. Required type: capture of ? super B, so only B and any class extending B will work.
        subList.add(new B(4));
        subList.add(new C(5));
        System.out.println(subList);
    }

    public static List<? extends A> subList(List<? extends A> list) {
        return list.subList(1, list.size());
    }

    private static List<? super B> firstTwoElements(List<? super B> list) {
        return list.subList(0, 2);
    }

    private static class A {
        private final int value;

        public A(final int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    private static class B extends A {
        public B(final int value) {
            super(value);
        }
    }

    private static class C extends B {
        public C(final int value) {
            super(value);
        }
    }
}
