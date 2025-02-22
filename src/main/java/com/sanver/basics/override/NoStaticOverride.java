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
            // We can have different return types for static methods in interfaces though.
            System.out.println("Returning type for C");
            return "C";
        }
    }

    interface D {
        static int getValue() {
            return 1;
        }
    }

    interface E extends D {
        static String getValue() { // Different return type is allowed in a static method with the same name of a method in a base interface.
            return "E";
        }
    }

    static class F implements D, E {

    }

    public static void main(String[] args) {
        A.getType();
        B.getType();
        C.getType();
        C c = new C();
        c.getType();
        ((A) c).getType();
        F f = new F();
//        System.out.println(f.getValue()); // This will result in a compile error,
//        because when a class implements an interface, it only inherits the interface's instance methods (default and abstract methods), not static methods.
//        To call a static method from an interface, you must use the interface name directly (e.g. D.getValue()).
        System.out.println(D.getValue());
        System.out.println(E.getValue());
    }
}
