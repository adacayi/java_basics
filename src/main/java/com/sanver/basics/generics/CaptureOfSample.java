package com.sanver.basics.generics;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CaptureOfSample {

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        List<A> list = new ArrayList<>(List.of(new A(1), new B(2), new C(3)));
        var format = "%-62s : %s%n";
        System.out.printf(format, "list = new ArrayList<>(List.of(new A(1), new B(2), new C(3)))", list);
        var sub = subList(list);
        System.out.printf(format, "sub = subList(list)", sub);
//    sub.add(new A(4));sub.add(new B(5)); // These won't compile, since the compiler represents the returned type as List<capture of ? extends A>,
//    which is a new type for the compiler that just extends A, hence we cannot add A to that list and nor B.

        List<B> list2 = new ArrayList<>(List.of(new B(3), new C(7)));
        System.out.printf(format, "list2 = new ArrayList<>(List.of(new B(3), new C(7)))", list2);
        List<B> sub2 = subList2(list2); // Notice that this returns List<B> instead of List<capture of ? extends A>, because of the definition <T extends A> List<T> subList2(List<T> list)
        System.out.printf(format, "sub2 = subList2(list2)", sub2);
        sub2.add(new C(4));
        System.out.printf(format, "sub2.add(new C(4))", sub2);

        B newElement = (B) sub.getFirst().getClass().getConstructors()[0].newInstance(5); // Since the first element is actually an instance of B, we can cast this to B.
//      sub.add(newElement); // This won't work as well. Error: incompatible types: B cannot be converted to capture#1 of ? extends A
//      In fact, we cannot add any elements to this list, because capture#1 of ? extends A is a type which an instance of it cannot be created.
//      What we can do is copy this list to a List<A> (since `capture#1 of ? extends A` extends A) and then we can add new elements to it:
        var subA = new ArrayList<A>(sub);
        System.out.printf(format, "subA = new ArrayList<A>(sub)", subA);
        subA.add(new A(4));
        subA.add(newElement);
        subA.add(new C(6));
        System.out.printf(format, "subA.add(new A(4)); subA.add(newElement); subA.add(new C(6))", subA);

        List<A> list3 = new ArrayList<>(List.of(new A(1), new B(2), new C(3)));
        System.out.printf(format, "list3 = new ArrayList<>(List.of(new A(1), new B(2), new C(3)))", list3);
        var sub3 = firstTwoElements(list3);
        System.out.printf(format, "sub3 = firstTwoElements(list3)", sub3);
//        sub3.add(new Object()); // This won't compile. Required type: capture of ? super B, so only the new type "capture of ? super B" or its subclasses will work, which are effectively B and any class extending B.
//        sub3.add(new A(3)); // This won't compile. Required type: capture of ? super B, so only B and any class extending B will work.
        sub3.add(new B(4));
        sub3.add(new C(5));
        System.out.printf(format, "sub3.add(new B(4)); sub3.add(new C(5))", sub3);
    }

    private static List<? extends A> subList(List<? extends A> list) {
        return list.subList(1, list.size());
    }

    private static <T extends A> List<T> subList2(List<T> list) { // This returns a List with the same generic type as its parameter. This is different from returning List<? extends A> because it preserves the exact type information, allowing for type-safe additions to the returned list as demonstrated in the code where sub2.add(new C(4)) is possible
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
            return "%s(%d)".formatted(this.getClass().getSimpleName(), value);
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
