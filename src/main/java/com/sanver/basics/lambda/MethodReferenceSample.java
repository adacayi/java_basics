package com.sanver.basics.lambda;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class MethodReferenceSample {
    public static void main(String[] args) {
        // 1. Static method reference
        Function<String, Number> parser = Integer::parseInt; // It should be a static method with return type Number or a subclass of Number and with one parameter with String or its super class.
        // So we can use MethodReferenceSample::myParser as well.
        System.out.println(parser.apply("42")); // Output: 42
        parser = MethodReferenceSample::myParser;
        System.out.println(parser.apply("421")); // Output: 3

        // 2. Instance method of a particular object
        String greeting = "Hi";
        Consumer<String> greeter = greeting::concat; // It should be an instance method of greeting, with any return type and String or its super class as parameter.
        greeter.accept(" there"); // No direct output, but concatenates
        greeter = new MethodReferenceSample()::concat;

        // 3. Instance method of an arbitrary object
        BiFunction<MethodReferenceSample, Integer, CharSequence> toString = MethodReferenceSample::toString; // Functional interface method's first parameter must be the arbitrary object's class.
        // In our case BiFunction<String,Integer, CharSequence> has the method CharSequence apply(MethodReferenceSample, Integer).
        // Thus, the method reference class must be MethodReferenceSample.
        // The method must have Integer or any super class as its parameter and return type should be CharSequence or any subclass
        System.out.println(toString.apply(new MethodReferenceSample(),12)); // Output: 12

        // 4. Constructor reference
        Function<Integer, int[]> arrayMaker = int[]::new;
        int[] arr = arrayMaker.apply(5);
        System.out.println(Arrays.toString(arr)); // Output: [0, 0, 0, 0, 0]
    }

    static int myParser(CharSequence chars) {
        return chars.length();
    }

    String concat(CharSequence initial) {
        return "concat " + initial;
    }

    String toString(Number number) {
        return number + "";
    }
}
