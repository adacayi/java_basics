package com.sanver.basics.basicoperators;

public class PrimitiveTypeConversions {
    public static void main(String[] args) {
        int a = 5;
        System.out.println("int a = 5; a: " + a);
        long b = a + 5;
        System.out.println("long b = a + 5; b: " + b);
        double c = b / 3;
        System.out.println("double c = b / 3; c: " + c);
        Integer d = 3 * a;
//        Long e = a; // This casting won't work. java: incompatible types: int cannot be converted to java.lang.Long
        System.out.println("Integer d = 3 * a; d: " + d);
//        Long e = d; // This casting won't work. java: incompatible types: java.lang.Integer cannot be converted to java.lang.Long
//        Long e1 = (Long) d; // This won't work. java: incompatible types: java.lang.Integer cannot be converted to java.lang.Long
        long e = d; // Notice conversion from boxed type to primitive is possible (Integer to long in this example)
        System.out.println("long e = d; e: " + e);
        char f = 65;
        System.out.println("char f = 65; f: " + f);
        int g = f + 3;
        System.out.println("int g = f + 3; g: " + g);
        long h = f + 7;
        System.out.println("long h = f + 7; h: " + h);
        double i = f + 20;
        System.out.println("double i = f + 20; i: " + i);
        // byte j = f + 12; // This gives the error java: incompatible types: possible lossy conversion from int to byte, since 12 is regarded as int and the sum is regarded as int
        byte j = (byte) (f + 3);
        System.out.println("byte j = (byte) (f + 3); j: " + j);
    }
}
