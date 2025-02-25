package com.sanver.basics.codeblocks;

class A {
    static String staticValue = "Initial"; // This line will be executed before the static block, because it is written before the static block, otherwise the static block would be executed first.

    static {// Static blocks are only run once. Look at the ClassInitialization code to see the details about what triggers running the static block.
        System.out.println("Static block A. Static value = " + staticValue);
    }

    String value = "Initial non-static"; // This line will be executed before the non-static block, because it is written before the non-static block, otherwise the non-static block will be executed.

    {
        System.out.println("non-static block A. Value = " + value);
    }

    public A(String value) {
        System.out.printf("Constructor A. Value = %s%n%n", value);
        this.value = value;
    }
}

class B extends A {
    static {
        System.out.println("Static block B. Static value = " + staticValue);
    }

    {
        System.out.println("non static block B. value = " + value);
    }

    public B(String value) {
        super(value);
        System.out.printf("Constructor B. Value = %s%n%n", value);
    }
}

class C extends B{
    static String staticValue = "Initial C"; // This line will be executed before the static block, because it is written before the static block, otherwise the static block would be executed first.

    static {// Static blocks are only run once, either triggered by calling a static method or a constructor of the class
        System.out.println("Static block C. Static value = " + staticValue);
//        System.out.println(staticValue2); // We cannot access staticValue2 at this point, because it is defined below.
    }

    static String staticValue2 = "Static Value 2 in C";

    String value; // This will be assigned to its default value before the non-static block is executed, because it is written before the non-static block, otherwise the non-static block will be executed.

    {
        System.out.println("non-static block C. Value = " + value);
//        System.out.println(value2); // We cannot access value2 at this point, because it is defined below.
    }

    String value2;

    public C(String value) {
        super(value);
        System.out.printf("Constructor C. Value = %s%n%n", value);
        this.value = value;
    }

    static void print() {
        System.out.printf("static print method, static value = %s%n%n", staticValue);
    }
}

public class StaticNonStaticCodeBlockSample {
    public static void main(String... args) {
        new B("Hi");
        new A("Hello"); // Note that static block for A will not run, since when we generate an instance of B above, it ran, and static blocks only run once.
        C.print(); // This triggers the static block execution for C.
        new C("Welcome");
    }
}
