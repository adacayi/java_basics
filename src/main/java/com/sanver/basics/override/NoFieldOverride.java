package com.sanver.basics.override;

public class NoFieldOverride {
    class A {
        public String value;
    }

    class B extends A {
        public int value;
    }

    public static void main(String... args) {
        NoFieldOverride sample = new NoFieldOverride();
        B b = sample.new B();
        b.value = 3;
        ((A) b).value = "Ahmet";
        System.out.printf("b.value: %d\n((A)b).value: %s", b.value, ((A) b).value);
    }
}
