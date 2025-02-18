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
        Integer sum = 50;   // Autoboxing
        sum = sum + 100;    // Unboxing sum, performing addition, and then autoboxing result
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

