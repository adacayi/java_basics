package com.sanver.basics.overload;

import java.util.Arrays;

public class OverloadSample {
    public OverloadSample() {
        System.out.println("Constructor with no parameters");
    }

    public OverloadSample(String... args) { // Constructor with varargs is allowed
        System.out.println("Constructor with varargs");
    }

    //    public OverloadSample(String[] args) {} // This has the same method signature with the above public OverloadSample(String... args) constructor

    public static void main(String[] args) {
        var overloadSample = new OverloadSample();
        overloadSample.print(new C());
        overloadSample.print(new B());
//        overloadSample.print(new B(), new B()); // This will result in a compile error because there are two methods with print(A a, B b) and print(B b, A a), that are applicable and accessible, and neither is more specific than the other, causing an ambiguity.
//        overloadSample.print(new C(), new C()); // This will result in a compile error, for the same reason as above.
        overloadSample.print(new B(), new B(), new C()); // This works even though there are two methods print(B b1, B b2, A a) and print(B b, A a1, A a2), that are applicable and accessible, print(B b1, B b2, A a) is more specific.
    }

    void print(A value) {
        System.out.println("A printed. " + value);
    }

    void print(B value) {
        System.out.println("B printed. " + value);
    }
//    public <T extends B> void print(T value){} // This has the same signature as the public void print(B value) and will cause a compile error
//    public <T super B> void print(T value){} // super is not allowed here

    void print(C value) {
        System.out.println("C printed. " + value);
    }

    void print(B value, String... params) {
        System.out.println("Printing B with parameters");
        System.out.println(value + " " + Arrays.toString(params));
    }

    void print(A a, B b) {
        System.out.printf("A: %s B: %s%n", a, b);
    }

    void print(B b, A a) {
        System.out.printf("B: %s A: %s%n", b, a);
    }

    void print(B b1, B b2, A a) {
        System.out.printf("B1: %s B2: %s A: %s%n", b1, b2, a);
    }

    void print(B b, A a1, A a2) {
        System.out.printf("B: %s A1: %s A2: %s%n", b, a1, a2);
    }

    static class A {
        @Override
        public String toString() {
            return this.getClass().getSimpleName();
        }
    }

    static class B extends A {
    }

    static class C extends B {
    }
}
