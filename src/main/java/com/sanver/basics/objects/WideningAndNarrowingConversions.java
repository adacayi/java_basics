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
        // Note that this only works for types int, short, char, byte. It doesn't work for long, double or float.
        // e.g. final long l = 1; int i = l; This won't compile.
//        final int i1 = 127;
//        byte b1 = i1; // This will work

        // Explicit narrowing
        i = 128;
        b = (byte) i; // This converts 128 to byte, resulting in -128 because 128 (10000000 in binary) gets interpreted as -128 in byte's signed representation
        b = (byte) 1000;// This converts 1000 to byte, resulting in -24 because 1000 (1111101000 in binary) gets truncated to 11101000 (by getting the last 8 bits, since byte has 8 bits) which is interpreted as -24 in byte's signed representation

//        Integer i1 = b; Integer i2 = (Integer) b;// These won't compile. Java does not perform implicit or explicit widening conversion to non-primitive (wrapper) types.
        Integer i1 = (int) b; // This works, because int can be boxed automatically to Integer.
        int i2 = Byte.valueOf((byte) 0); // Java allows unboxing and implicit widening in a single step.
//         b = (byte)Integer.valueOf(2);// This does not work. We cannot explicitly convert a wrapper type to a narrower primitive type. java.lang.Integer cannot be converted to byte.
        l = (long) Integer.valueOf(2); // This works. We don't need to do this explicitly though, since unboxing automatically and implicit widening is possible as shown above.
        Double d1 = (double) Byte.valueOf((byte) 2); // This also works, where we do an explicit conversion from Byte to double and then an autoboxing is done.
        // b = (byte)Double.valueOf(2); // This does not work
        b = (byte) (double) Double.valueOf(2);// This works
//        Integer i2 = Byte.valueOf((byte)2); // Java does not allow direct assignment between different wrapper types, even though Byte can be unboxed to byte and byte can be widened to int.
//        Integer i2 = (Integer)Byte.valueOf((byte)2); // Java does not allow explicit conversion between different wrapper types. java.lang.Byte cannot be converted to java.lang.Integer

//        Double d2 = 15; // This won't compile. Java does not perform implicit or explicit widening conversion to non-primitive (wrapper) types.
        Double d2 = 15.0;
        System.out.println("Double d2 = 15.0;");
        System.out.println("d2 <= 15: " + (d2 <= 15)); // Although d2 = 15 does not work, d2 <= 15 works like d2 < 12, d2 > 15, because java unboxes the Double to double and makes the comparison afterward.
        d2 += 10; // This works, because of auto-unboxing and auto-boxing. Behind the scenes, Java:
//        First unboxes the Double to a primitive double
//        Performs the addition, which results in a double primitive, since double + int results in double.
//        Auto-boxes the result back into a new Double object
//        Assigns the reference to d2
        System.out.println("d2 += 10: " + d2);
//        d2 = 3 + 5; // This won't work. Java does not perform implicit or explicit widening conversion to non-primitive (wrapper) types.
    }
}
