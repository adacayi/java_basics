package com.sanver.basics.override;

public class OverrideSample2 {
    static class A{
        A() {
            print();
        }

        void print() {
            System.out.println("A constructor run");
        }
    }

    static class B extends A{
        int i = 4;

        @Override
        void print() {
            System.out.println("Printing i: " + i);
        }
    }

    public static void main(String[] args) {
        A a = new B(); // This will call the A() constructor, before setting its instance member values, which in turn calls the print method, but since it is overridden, it will call the print method of B,
        // and since i is not assigned to 4 yet, it will be its default value 0.
        a.print();
    }
}
