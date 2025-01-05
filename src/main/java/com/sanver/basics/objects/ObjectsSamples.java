package com.sanver.basics.objects;

import java.util.Arrays;
import java.util.Objects;

public class ObjectsSamples {
    public static void main(String... args) {
        Object a = null;
        Object b = new Object();
        Object c = null;
        Object d = b;
        String[] first = {"Ahmet", "Mustafa", "Muhammed"};
        String[] second = {"Ahmet", "Mustafa", "Muhammed"};

        var format1 = "%-20s: %d%n";
        var format2 = "%-20s: %s%n";
        var format3 = "%-33s: %s%n";
        var format4 = "%-49s: %s %s%n";

        System.out.printf(format1, "Objects.hashCode(a)", Objects.hashCode(a));
        System.out.printf(format1, "Objects.hashCode(b)", Objects.hashCode(b));
        System.out.printf(format2, "Objects.equals(a, b)", Objects.equals(a, b));
        System.out.printf(format2, "Objects.equals(a, c)", Objects.equals(a, c));
        System.out.printf(format2, "Objects.equals(b, d)", Objects.equals(b, d));
        System.out.printf(format3, "Objects.equals(first, second)", Objects.equals(first, second));
        System.out.printf(format3, "Objects.deepEquals(first, second)", Objects.deepEquals(first, second));
        System.out.printf(format3, "Arrays.equals(first, second)", Arrays.equals(first, second));
        System.out.printf(format3, "Arrays.deepEquals(first, second)", Arrays.deepEquals(first, second));
        System.out.printf(format4, "Objects.hashCode(first), Objects.hashCode(second)", Objects.hashCode(first), Objects.hashCode(second));
        System.out.printf(format4, "Arrays.hashCode(first), Arrays.hashCode(second)", Arrays.hashCode(first), Arrays.hashCode(second));
    }
}
