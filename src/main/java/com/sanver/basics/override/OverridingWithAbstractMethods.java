package com.sanver.basics.override;

public class OverridingWithAbstractMethods {
    interface D {
        default String getName() {
            return "D";
        }
    }

    interface E extends D {
        String getName();
    }

    static class A {
        int getValue() {
            return 1;
        }
    }

    abstract static class B extends A {
        abstract int getValue();
    }

    static class C extends B {
        int getValue() { // We need to implement this method, although there is an implementation in the base class A.
            return 2;
        }
    }

    static class F implements E {

        @Override
        public String getName() { // We need to implement this method, although there is a default implementation in the interface D.
            return "";
        }
    }

}
