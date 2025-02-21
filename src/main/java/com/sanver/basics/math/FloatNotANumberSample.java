package com.sanver.basics.math;

public class FloatNotANumberSample {
    public static void main(String[] args) {
        float a = 6 / 0f;   // This will result in Infinity
        // float a = 6 / 0;   // This would result in an ArithmeticException since 6 / 0 is an int division which does not have an Infinite or -Infinite result.
        float b = -1 / 0f; // This will result in -Infinity
        float c = 0 / 0f;   // This will result in NaN
        // float b = 0 / 0;   // This will result in an ArithmeticException
        float d = 5;
        Float e = null;
        Float f = 1f;

        printDetails("6 / 0f", a);
        printDetails("-1 / 0f", b);
        printDetails("0 / 0f",c);
        printDetails("5", d);
//        printDetails("1f / null", f/e); // This would result in a NullPointerException rather than a NaN
    }

    private static void printDetails(String operation, float a) {
        System.out.printf("Is (%s = %f) NaN? %s%n", operation, a, Float.isNaN(a));  // true
        System.out.printf("Is (%s = %f) infinite? %s%n", operation, a, Float.isInfinite(a));  // true
    }


}
