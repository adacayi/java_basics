package com.sanver.basics.objects;

public class WideningAndNarrowingConversions {
    public static void main(String[] args) {
        boolean bool = true;
        // Implicit widening conversions
        byte b = 2; // b = bool or b = (byte)bool will result in a compile error
        char c = 300; // Note that char c = b does not compile, because char is non-negative while byte can be negative, thus will need and explicit conversion char c = (char) b;
        short s = b; // Note that short s = c won't compile and vice versa since char is unsigned while short is signed.
        int i = b;
        i = c;
        i = s;
        long l = b;
        l = c;
        l = s;
        l = i;
        float f = b;
        f = c;
        f = s;
        f = i;
        f = l;
        double d = b;
        d = c;
        d = s;
        d = i;
        d = l;
        d = f;

        // Implicit narrowing
        // You can assign a source variable that is a compile time constant to a target variable of different type if the value held by source variable fits into the target variable.

        final char c1 = 127;
        b = c1; // since c1 is final, the compiler knows it can be assigned to a byte.

        // Explicit narrowing
        i = 128;
        b = (byte)i; // This converts 128 to byte, resulting in -128 because 128 (10000000 in binary) gets interpreted as -128 in byte's signed representation
        b = (byte)1000;// This converts 1000 to byte, resulting in -24 because 1000 (1111101000 in binary) gets truncated to 11101000 (by getting the last 8 bits, since byte has 8 bits) which is interpreted as -24 in byte's signed representation
    }
}
