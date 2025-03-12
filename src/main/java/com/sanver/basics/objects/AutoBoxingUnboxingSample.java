package com.sanver.basics.objects;

public class AutoBoxingUnboxingSample {
    public static void main(String[] args) {

        // Autoboxing: Primitive to Wrapper Conversion
        int primitiveInt = 10;
        Integer wrappedInt = primitiveInt;  // int to Integer
        System.out.println("Autoboxing int to Integer: " + wrappedInt);

        double primitiveDouble = 20.5;
        Double wrappedDouble = primitiveDouble;  // double to Double
        System.out.println("Autoboxing double to Double: " + wrappedDouble);

        char primitiveChar = 'A';
        Character wrappedChar = primitiveChar;  // char to Character
        System.out.println("Autoboxing char to Character: " + wrappedChar);

        boolean primitiveBoolean = true;
        Boolean wrappedBoolean = primitiveBoolean;  // boolean to Boolean
        System.out.println("Autoboxing boolean to Boolean: " + wrappedBoolean);

        // Unboxing: Wrapper to Primitive Conversion
        Integer wrappedIntObj = Integer.valueOf(30);
        int unboxedInt = wrappedIntObj;  // Integer to int
        System.out.println("Unboxing Integer to int: " + unboxedInt);

        Double wrappedDoubleObj = Double.valueOf(40.7);
        double unboxedDouble = wrappedDoubleObj;  // Double to double
        System.out.println("Unboxing Double to double: " + unboxedDouble);

        Character wrappedCharObj = Character.valueOf('B');
        char unboxedChar = wrappedCharObj;  // Character to char
        System.out.println("Unboxing Character to char: " + unboxedChar);

        Boolean wrappedBooleanObj = Boolean.valueOf(false);
        boolean unboxedBoolean = wrappedBooleanObj;  // Boolean to boolean
        System.out.println("Unboxing Boolean to boolean: " + unboxedBoolean);

        // Autoboxing in Expressions
        // When one operand is a primitive, and the other is not, the other will be unboxed and then the operation is done.

        var result = unboxedInt + wrappedDoubleObj; // wrappedDoubleObj will be unboxed to double, and int + double will result in double.
        System.out.println("unboxedInt + wrappedDoubleObj: " + result);
        Double d = 3.0;
        Integer i = 3;
        byte b = 3;
        System.out.println("b == d : " + (b == d)); // d will be unboxed to double, this will return true
        System.out.println("d.equals(b) : " + (d.equals(b))); // b will be boxed into Byte, and since equals method first checks for type (obj instanceof Double),
        // and since b is not a Double, it will return false.
//        System.out.println("i == d : " + (i == d)); // There will be no unboxing, and it will return in a compile error, since i is an Integer while d is a Double, and cannot reference to same object.
        Integer sum = 50;   // Autoboxing
        sum = sum + 100;    // Unboxing sum to int, performing addition, and then autoboxing result to Integer
        System.out.println("Autoboxing/Unboxing in expressions: " + sum);

        // Passing Wrapper Objects to Methods expecting Primitives
        System.out.println("Passing Wrapper to method expecting primitive: " + addNumbers(Integer.valueOf(5), Integer.valueOf(15)));

        // Passing Primitives to Methods expecting Wrappers
        System.out.println("Passing primitive to method expecting Wrapper: " + multiplyNumbers(4, 5));
    }

    // Method expecting primitive parameters
    public static int addNumbers(int a, int b) {
        return a + b;  // Autoboxing and unboxing in method call
    }

    // Method expecting Wrapper parameters
    public static Integer multiplyNumbers(Integer a, Integer b) {
        return a * b;  // Unboxing and autoboxing in method call
    }
}

