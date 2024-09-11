package com.sanver.basics.override;

public class NoFieldOverride {
    static class A {
        public String value;
    }

    static class B extends A {
        public int value;
    }

    public static void main(String... args) {
        B b = new B();
        b.value = 3;
        ((A) b).value = "Ahmet";
        System.out.printf("b.value: %d%n((A)b).value: %s", b.value, ((A) b).value);
    }
}
