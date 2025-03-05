package com.sanver.basics.objects;

import java.util.Arrays;

public class WideningAndNarrowingConversions {
    public static void main(String[] args) {
        boolean bool = true;
        // Implicit widening conversions
        byte b = 2; // b = bool or b = (byte)bool will result in a compile error
        char c = 300; // Note that char c = b does not compile, because char is non-negative while byte can be negative, thus will need and explicit conversion char c = (char) b;
        // if b is final though, char c = b will work, since the compiler knows b is within the char range. This only works for byte, char, short and int.
        // e.g. final long l = 5; char c = l; will not work, although l is final and within the range of char. Check implicit narrowing section in this class.
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
        // float f1 = 1.0; // This won't compile as well, since 1.0 is a double and thus there is no implicit narrowing.
        // float f1 = 12e5;// This won't compile as well, since 12e5 is a double and thus there is no implicit narrowing.
//        final int i1 = 127;
//        byte b1 = i1; // This will work

        // Explicit narrowing
        i = 128;
        b = (byte) i; // This converts 128 to byte, resulting in -128 because 128 (10000000 in binary) gets interpreted as -128 in byte's signed representation
        b = (byte) 1000;// This converts 1000 to byte, resulting in -24 because 1000 (1111101000 in binary) gets truncated to 11101000 (by getting the last 8 bits, since byte has 8 bits) which is interpreted as -24 in byte's signed representation


        // Wrapper classes
//        Integer i1 = b; Integer i2 = (Integer) b;// These won't compile. Java does not perform implicit widening conversion between wrapper types.
//        Also, explicit casting of a primitive type to another wrapper type is not allowed.
//        Integer i1= (Integer)i; // This is allowed though, since i will be boxed to Integer, then casted to Integer.
//        Integer i1= Byte.valueOf("1"); Integer i2 = (Integer)Byte.valueOf("1") // This won't compile as well. We cannot cast one wrapper type to another.
        Integer i1 = (int) b; // This works, because ints ((int)b in our example) can be boxed automatically to Integer.

        int i2 = Byte.valueOf((byte) 0); // Java allows unboxing and implicit widening in a single step.
//         b = (byte)Integer.valueOf(2);// This does not work. Integer.valueOf(2) won't be automatically unboxed to int here,
//         and we cannot explicitly convert a wrapper type to a narrower primitive type. java.lang.Integer cannot be converted to byte.
        b = (byte) (int) Integer.valueOf(2); // This works.
        Double d1 = (double) Byte.valueOf((byte) 2); // This also works, where we do an explicit conversion from Byte to double and then an autoboxing is done.
        // b = (byte)Double.valueOf(2); // This does not work
        b = (byte) (double) Double.valueOf(2);// This works
//        Integer i2 = Byte.valueOf((byte)2); // Java does not allow direct assignment between different wrapper types, even though Byte can be unboxed to byte and byte can be widened to int.
//        Integer i2 = (Integer)Byte.valueOf((byte)2); // Java does not allow explicit conversion between different wrapper types. java.lang.Byte cannot be converted to java.lang.Integer

//        Double d2 = 15; // This won't compile. When boxed, 15 would be boxed to an Integer and Java does not perform implicit widening conversion between wrapper types.
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
//        d2 = d2 + 3 + 5; // This works though, since d2 + 3 will result in a double, and then + 5 will result in another double and finally can be auto boxed to Double.
        probe(d); // Since there is no probe with double parameter, and no probe method with a wider primitive type,
        // double will be boxed into a Double and class Double extends Number.
        // Therefore, a Double can be passed to the method that takes Number.
        // A Double can also be passed to a method that takes Object,
        // but Number is more specific than Object therefore probe(Number) will be called.
        probe(b); // Since there is no probe with byte parameter, auto-widening takes place and probe(short) will be called.
        // You can comment out probe(short) to see the next widening is to int and so on.
        // If there are no probe methods with a wider primitive type then boxing is done and probe(Byte) is called.
        // Comment out the probe methods with primitive parameters to see that.
        // Note that we also have a probe(long... longs) method. This will be called lastly, if there are no matches found for the boxed type. Comment out probe(Object) and probe(Number) to see that.
        probe(Integer.valueOf(1)); // In this case it first looks for probe(Integer), and if not found looks for probe(super class of Integer) and runs probe(Number),
        // if not, does auto-unboxing and calls for probe(int). If probe(int) does not exist, it uses auto-widening and calls for probe(long).
        // Note that we also have a probe(long... longs) method. This will be called if there are no matches found for the auto-widening type.
        // In the final case, if we also had a probe(Integer... ints) method, there is no priority between the two thus results in a compile error: reference to probe is ambiguous.
        // Comment out relevant functions to test the behavior.
    }

    static void probe(Object o) {
        System.out.println("Object " + o);
    }

    static void probe(Number n) {
        System.out.println("Number " + n);
    }

//    static void probe(Double d) {
//        System.out.println("Double " + d);
//    }

    static void probe(Long l) {
        System.out.println("Long " + l);
    }

//    static void probe(Byte b) {
//        System.out.println("Byte " + b);
//    }

//    static void probe(double d) {
//        System.out.println("double " + d);
//    }

    static void probe(float f) {
        System.out.println("float " + f);
    }

    static void probe(long l) {
        System.out.println("long " + l);
    }

    static void probe(int i) {
        System.out.println("int " + i);
    }

    static void probe(short s) {
        System.out.println("short " + s);
    }

    static void probe(char c) {
        System.out.println("char " + c);
    }

    static void probe(long... longs) {
        System.out.println("long... " + Arrays.toString(longs));
    }

//    static void probe(Integer... ints) {
//        System.out.println("Integer... " + Arrays.toString(ints));
//    }
}
