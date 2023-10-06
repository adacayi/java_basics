package com.sanver.basics.generics;

public class PrimitiveAcceptance {
    private static <T> void calculate(T[] array) {
        System.out.println(array.length);
    }

    private static void calculate(byte[] array) {
        System.out.println(array.length);
    }

    private static void calculate(short[] array) {
        System.out.println(array.length);
    }

    private static void calculate(int[] array) {
        System.out.println(array.length);
    }

    private static void calculate(long[] array) {
        System.out.println(array.length);
    }

    private static void calculate(char[] array) {
        System.out.println(array.length);
    }

    private static void calculate(float[] array) {
        System.out.println(array.length);
    }

    private static void calculate(double[] array) {
        System.out.println(array.length);
    }

    private static void calculate(boolean[] array) {
        System.out.println(array.length);
    }

    public static void main(String... args) {
        int[] array = {3, 5};
        calculate(array);
        // If there were only calculate(T[] array) method,
        // calculate(array); call would give a compile-time error.
        // We need overloads of the method for primitive types.
    }
}
