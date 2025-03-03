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
        System.out.println("C.value2: " + C.value2); // Unlike static methods, we can access static fields of an interface through the implementing class or extending interface
        // If there was a value2 in B or in another interface that C implements (look at the commented out D interface, and make C implement D), then this would result in a compile error because of the ambiguity.
        System.out.println("B.value2: " + B.value2); // Unlike static methods, we can access static fields of an interface through the implementing class or extending interface
//        System.out.println(new C().value); // This will result in a compile error though, since reference to 'value' is ambiguous, both 'A. value' and 'B. value' match
        System.out.println("B.value: " + B.value); // This will work fine, since B.value hides A.value
        var c = new C();
        c.process();
        c.process2();
        ((B) c).process2();
        c.process3();
        c.process4();
        c.process5();
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

        static void process() {
            System.out.println("Processing a");
        }

        static void process2() {
            System.out.println("Processing a 2");
        }

        void process3();

        default void process4() {
            System.out.println("Processing A 4");
        }

        private void process5() {
            System.out.println("Processing A 5");
        }
    }

    interface B extends A {
        String value = "hello"; // Note that this is public static final

        static void print() {
            System.out.println("B print");
        }

        void process(); // Note that we can have a non-static method with same name in the sub-interface
        default void process2(){ // Note that we can have a default method with same name in the sub-interface
            System.out.println("Processing B 2");
        }

//        static void process3() { // Overriding a non-static method with a static method is not allowed.
//            System.out.println("Processing B 3");
//        }

//        static void process4() { }// This is not allowed as well.

        void process4(); // This is allowed.

//         private void process4(); // This is not allowed, since although an interface can have private methods, you still cannot override a public method with a private method.

        void process5(); // This is allowed.
//        default void process5() { // This is allowed as well.
//            System.out.println("Processing B 5");
//        }
    }

//    interface D{
//        int value2 =20;
//    }

    static class C implements A, B {
        public void process() {
            System.out.println("Processing C");
        }

        public void process2() { // Note that this overrides process2 in B
            System.out.println("Processing C 2");
        }

        public void process3() {
            System.out.println("Processing C 3");
        }

        public void process4() {
            System.out.println("Processing C 4");
        }

        public void process5() {
            System.out.println("Processing C 5");
        }
    }
}
