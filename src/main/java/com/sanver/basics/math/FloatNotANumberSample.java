package com.sanver.basics.math;

public class FloatNotANumberSample {
    public static void main(String[] args) {
        float a = 6 / 0f;   // This will result in Infinity
        // float a = 6 / 0;   // This would result in an ArithmeticException
        float b = 0 / 0f;   // This will result in NaN
        // float b = 0 / 0;   // This will result in an ArithmeticException
        float c = 5;

        printDetails("6 / 0f", a);
        printDetails("0 / 0f",b);
        printDetails("5", c);
    }

    private static void printDetails(String operation, float a) {
        System.out.printf("Is (%s = %f) NaN? %s%n", operation, a, Float.isNaN(a));  // true
        System.out.printf("Is (%s = %f) infinite? %s%n", operation, a, Float.isInfinite(a));  // true
    }


}
