package com.sanver.basics.override;

public class NoStaticOverride {
    static class A {
        static void writeStatic() {
            System.out.println("A static method");
        }
    }

    static class B extends A {
    }

    static class C extends A {
        static void writeStatic() {
            System.out.println("C static method");
        }
    }

    public static void main(String[] args) {
        A.writeStatic();
        B.writeStatic();
        C.writeStatic();
        C c = new C();
        c.writeStatic();
        ((A)c).writeStatic();
    }
}
