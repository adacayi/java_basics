package com.sanver.basics.objects;

import java.util.Arrays;
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
        String[] first = {"Ahmet", "Mustafa", "Muhammed"}, second = {"Ahmet", "Mustafa", "Muhammed"};
        System.out.printf("%s\n", Objects.equals(first, second));
        System.out.printf("%s\n", Objects.deepEquals(first, second));
        System.out.printf("%s %s\n", Objects.hashCode(first), Objects.hashCode(second));
        System.out.printf("%s %s\n", Arrays.hashCode(first), Arrays.hashCode(second));
    }
}
