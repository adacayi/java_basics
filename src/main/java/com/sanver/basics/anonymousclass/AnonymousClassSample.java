package com.sanver.basics.anonymousclass;

public class AnonymousClassSample {
    public static void main(String[] args) {
        A a = new A() {
            static boolean i = true; // This is to show we can have static fields in an Anonymous class. It can have any accessor (public, protected, package private (no accessor) or private).
            // Also, this will hide the `i` in the super class.
            private int c = 3; // This is to show that we can have non-static fields in an Anonymous class. It can have any accessor (public, protected, package private (no accessor) or private)

            void myPrint() { // This is to show that we can define new methods in the anonymous class. It can have any accessor (public, protected, package private (no accessor) or private)
                System.out.printf("My print i: %s super.i: %d%n", i, super.i);
            }

            @Override
            public Integer print(String message) {
                myPrint();
                return message.length() / c;
            }
        };

        var result = a.print("Anonymous print");
        System.out.println("Result: " + result);
    }

    static class A {
        int i = 5;

        protected Number print(String message) {
            System.out.println(message);
            return 1;
        }
    }
}
