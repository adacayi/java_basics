package com.sanver.basics.codeblocks;

class A {
    {
        System.out.println("Selamunaleykum");
    }

    static {
        System.out.println("This is static block in A");
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
        new A();
        new B();
        new A();
        new B();
    }
}
