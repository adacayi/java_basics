package com.sanver.basics.interfaces;

interface Payable {
    double MAX_RATE = 0.78; // This is automatically public, static and final. Another access modifier is
    // not permitted.
//    static { // Static or non-static blocks are not allowed in an interface
//        System.out.println("Interface initialized");
//    }

    static boolean isIncreaseRateValid(double increaseRate) {
        return increaseRate <= MAX_RATE;
    } // This is public by default, but can be made private as well. Protected is not allowed and package private is not possible, since the default accessibility without the modifier is public.

    void increaseRate(double increaseRate); // This is automatically public. We cannot have method declarations (methods that don't have any bodies) with private, protected or package-private access. We can have private methods though. No protected or package-private methods are allowed.

    // protected void increase(); // This won't compile, because only public is allowed for method declarations.
    // protected void increase(){} // This won't compile. Only public and private are allowed for method definitions and if the method definition is public, it must use default or static.
    // public void increase(){} // This won't compile as well, since it is a public method definition (method with body), it should either be a default method or a static one.

    default double getMaxRate() {
        return MAX_RATE;
    }

    double getRate();

    interface SomeInterface { // This is public and static, but not final, and cannot be made protected, package private or private.

    }

    class SomeClass { // This is public and static, but not final (but can be made final) and cannot be made protected, package private or private.

    }

    abstract class SomeAbstractClass { // This is public and static, but not final, and cannot be made protected, package private or private.

    }

    record Person(String name, int age){ // This is public, static and final, and cannot be made protected, package private or private.

    }

    enum Days { // This is public, static and final, and cannot be made protected, package private or private. If put, final keyword will result in a compile error though.
        MONDAY, TUESDAY
    }
}

class Pay implements Payable {

    private double rate = 0.2;

    @Override
    public void increaseRate(double increaseRate) {
        if (Payable.isIncreaseRateValid(increaseRate))
            rate += increaseRate;
    }

    @Override
    public double getRate() {
        return rate;
    }
}

public class InterfaceSample {

    public static void main(String[] args) {
        Pay pay = new Pay();
        double increaseRate = 0.3;
        System.out.println(increaseRate + " " + (Payable.isIncreaseRateValid(increaseRate) ? "Increase rate is valid"
                : "Increase rate is not valid"));
        System.out.println("Original rate is " + pay.getRate());
        pay.increaseRate(0.3);
        System.out.println("Increased rate is " + pay.getRate());
        System.out.println("Max rate is " + pay.getMaxRate());

        // Accessing static fields and calling static methods

//         new C().print(); // This won't compile, since a static method may only be called on its containing interface
//         B.printA(); // This won't compile, since a static method may only be called on its containing interface
        B.print();
        A.print();
        A.printA();
        System.out.println("new C().value2: " + new C().value2); // Unlike static methods, we can access static fields of an interface through the implementing class or extending interface
        System.out.println("B.value2: " + B.value2); // Unlike static methods, we can access static fields of an interface through the implementing class or extending interface
//        System.out.println(new C().value); // This will result in a compile error though, since reference to 'value' is ambiguous, both 'A. value' and 'B. value' match
        System.out.println("B.value: " + B.value); // This will work fine, since B.value hides A.value
    }

    interface A {
        int value = 5; // Note that this is public static final
        int value2 = 10; // Note that this is public static final

        static void printA() {
            System.out.println("printA method");
        }

        static void print() {
            System.out.println("A print");
        }
    }

    interface B extends A {
        String value = "hello"; // Note that this is public static final

        static void print() {
            System.out.println("B print");
        }
    }

    static class C implements A, B {

    }
}
