package com.sanver.basics.override;

/**
 * In Java, field overriding is not possible in the same way that method overriding is.
 * Field variables (or attributes) in Java are bound at compile-time based on the reference type, not the runtime type of the object.
 * This means that if you declare a field in a superclass and then declare a field with the same name in a subclass, they don't actually override each other;
 * instead, the subclass field simply hides the superclass field.
 * This is known as <b>field hiding</b>, not overriding.
 */
public class NoFieldOverride {
    static class A {
        public int value;

        public A(int value) {
            this.value = value;
        }

        void print() {
            System.out.printf("A.value = %d%n", value);
        }
    }

    static class B extends A {
        int value;
        public B(int value, int value1) {
            super(value);
            this.value = value1;
        }

        void print() {
            System.out.printf("A.value = %d B.value = %d%n", super.value, value); // This is to show how to access a field of a super class
        }
    }

    static class C extends B {
        String value; // This is to demonstrate the behavior with same variable name and different type

        public C(int value, int value1, String value2) {
            super(value, value1);
            this.value = value2;
        }

        @Override
        void print() {
            System.out.printf("A.value = %d, B.value = %d C.value = %s%n", ((A)this).value, super.value, this.value); // we cannot use super.super.value to access value field of class A, but we can use ((A)this).value instead.
        }
    }

    public static void main(String... args) {
        A a = new A(3);
        B b = new B(3, 5);
        A b2 = new B(3, 5);
        A c = new C(3, 5, "Ahmet");
        System.out.println(a.value);
        System.out.println(b.value);
        System.out.println(b2.value);
        System.out.println(((B) b2).value);
        System.out.printf("c.value = %d ((B)c).value = %s ((C)c).value = %s%n", c.value, ((B)c).value, ((C)c).value);
        c.print();
    }
}
