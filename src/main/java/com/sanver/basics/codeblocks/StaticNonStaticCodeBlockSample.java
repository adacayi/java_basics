package com.sanver.basics.codeblocks;

class A {
    {
        System.out.println("Selamunaleykum");
    }

    static {
        System.out.println("This is static block in A"); // Static blocks are only run once
    }

    public A() {
        System.out.print("A is generated\n\n");
    }
}

class B extends A {
    static {
        System.out.println("This is static block in B");
    }

    {
        System.out.println("Ve aleykumselam");
    }

    public B() {
        System.out.print("B is generated\n\n");
    }
}

public class StaticNonStaticCodeBlockSample {
    public static void main(String... args) {
        new B();
        new A(); // Note that static block for A will not run, since when we generate an instance of B above, it ran, and static blocks only run once.
        new A();
        new B();
    }
}
