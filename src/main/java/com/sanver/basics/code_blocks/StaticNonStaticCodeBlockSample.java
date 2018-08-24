package com.sanver.basics.code_blocks;

class A {
    {
        System.out.printf("Selamunaleykum\n");
    }

    static {
        System.out.printf("This is static block in A\n");
    }

    public A() {
        System.out.printf("A is generated\n\n");
    }
}

class B extends A {
    static {
        System.out.printf("This is static block in B\n");
    }

    {
        System.out.printf("Ve aleykumselam\n");
    }

    public B() {
        System.out.printf("B is generated\n\n");
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
