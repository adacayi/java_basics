package com.sanver.basics.objects;

import java.util.Objects;

public class ObjectsSamples {
    public static void main(String... args) {
        Object a = null;
        Object b = new Object();
        Object c = null;
        Object d = b;
        System.out.printf("%d\n", Objects.hashCode(a));
        System.out.printf("%d\n", Objects.hashCode(b));
        System.out.printf("%s\n", Objects.equals(a, b));
        System.out.printf("%s\n", Objects.equals(a, c));
        System.out.printf("%s\n", Objects.equals(b, d));
    }
}
