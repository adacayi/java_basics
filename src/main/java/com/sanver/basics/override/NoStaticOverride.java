package com.sanver.basics.override;

public class NoStaticOverride {
    static class A {
        static Object getType() {
            System.out.println("Returning type for A");
            return "A";
        }
    }

    static class B extends A {
    }

    static class C extends A {
        static String getType() { // Note that we can use covariant return types of the hidden method `Object getType()`,
            // but unlike hidden fields, even though technically possible,
            // by design choice return types that are not of instance of the hidden method's return type are not allowed.
            // i.e. static int getType(){return 1} or static void getType(){} are not allowed.
            System.out.println("Returning type for C");
            return "C";
        }
    }

    public static void main(String[] args) {
        A.getType();
        B.getType();
        C.getType();
        C c = new C();
        c.getType();
        ((A) c).getType();
    }
}
