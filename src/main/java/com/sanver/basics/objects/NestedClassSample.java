package com.sanver.basics.objects;

/**
 * <p>In Java, the terms <strong>nested class</strong> and <strong>inner class</strong> are related but not identical.</p>
 *
 * <h2>Nested Classes</h2>
 * <p>In Java, any class defined within another class is called a <strong>nested class</strong>.
 * Nested classes can be either:</p>
 * <ul>
 *     <li><strong>Static nested classes</strong> (e.g., {@code B} in your example)</li>
 *     <li><strong>Non-static nested classes</strong> (also known as <strong>inner classes</strong>) (e.g., {@code C} in your example)</li>
 * </ul>
 *
 * <h2>Inner Classes</h2>
 * <p>An <strong>inner class</strong> is a specific type of nested class that is <strong>non-static</strong> and has an implicit reference
 * to an instance of its enclosing class. Because of this reference, an inner class can access the instance variables
 * and methods of its enclosing class. This also means that you need an instance of the enclosing class to create an
 * instance of an inner class.</p>
 */

public class NestedClassSample {
    public static class A {
        private static int aStatic = 5;
        private int a;

        A(int a) {
            this.a = a;
        }

        public static void main(String[] args) {
            var a = new A(3);
//        var b = a.new B(4);        // We cannot instantiate B this way, since B is a static class.
//        var b = new A(1).new B(5); // Similar to the above approach, this is also not possible, since B is a static class
            var b = new B(6);
            var b2 = new A.B(7);
//        var c = new A.C(7); // This is not possible here since we need an instance of A to be able to instantiate C and since this method is static we don't have it here. Check A's non-static print method where this approach is allowed.
            var c = a.new C(7);
            var c2 = new A(12).new C(13); // This instantiation is possible with non-static class C.
            a.print();
            b.print(); // Notice that aStatic will be 35, not 30 because it is changed in a.print() most recently.
            b2.print();
            c.print();
            c2.print();
        }

        void print() {
            System.out.printf("Printing A(a = %d) started%n", a);
            new B(6).print();
            new A.B(7).print();
//        new A(1).new B(8).print(); // We cannot instantiate B this way, since B is a static class.
            new C(9).print();     // Since we are in a non-static method of A, this instantiation of C is possible
            new A.C(10).print();  // Since we are in a non-static method of A, this instantiation of C is also possible, which uses the A instance.
            new A(2).new C(11).print();
            System.out.printf("Printing A(a = %d) finished%n%n", a);
        }

        static class B {
            private int b;

            B(int b) {
                this.b = b;
                aStatic = b * 5;
            }

            void print() {
                System.out.printf("B(b = %d) class print:%n", b);
                System.out.printf("aStatic: %d b: %d%n%n", aStatic, b);
//            System.out.printf("a: %d%n", a); // static class B does not have access to non-static field "a" of A.
            }
        }

        class C {
            private int c;

            C(int c) {
                this.c = c;
            }

            void print() {
                System.out.printf("C(c = %d) class print with A instance A(a = %d): %n", c, a);
                System.out.printf("a: %d c: %d%n%n", a, c);
            }
        }
    }
}