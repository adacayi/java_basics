package com.sanver.basics.objects;

/**
 * <h2>Inner Classes</h2>
 * <p>An <strong>inner class</strong> is a specific type of nested class that is <strong>non-static</strong> and has an implicit reference
 * to an instance of its <strong>enclosing class</strong>. Because of this reference, an inner class can access the instance variables
 * and methods of its enclosing class. This also means that you need an instance of the enclosing class to create an
 * instance of an inner class.</p>
 *
 * @see NestedClassSample
 */
public class InnerClassSample {
    String name;

    public static void main(String[] args) {
        var a = new InnerClassSample().new A();
        var b = a.new B();
        var c = b.new C();
        c.setNames("Adam");
        c.printNames();
    }

    class A {
        String name;

        class B {
            String name;

            class C {
                String name;

                void setNames(String name) {
                    InnerClassSample.this.name = name;
                    A.this.name = "A " + name;
                    B.this.name = "B " + name;
                    this.name = "C " + name;
                }

                void printNames() {
                    A.this.printNames();
                    System.out.println("name field for class B: " + B.this.name);
                    System.out.println("name field for class C: " + name);
                }
            }
        }

        void printNames() {
            System.out.println("name field for class InnerClassSample: " + InnerClassSample.this.name);
            System.out.println("name field for class A: " + A.this.name);
        }
    }
}
