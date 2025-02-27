package com.sanver.basics.arrays;

import java.util.Arrays;

/**
 * Demonstrates covariance in arrays in Java.
 * <p>
 * Covariance in arrays means that if {@code Sub} is a subtype of {@code Super}, then the array type
 * {@code Sub[]} is considered a subtype of {@code Super[]}. This allows assigning an array of subtypes to a
 * variable of a supertype array. However, this feature comes with a runtime risk. If you attempt to store
 * an element of an incompatible type into an array obtained through covariance, an {@code ArrayStoreException}
 * will be thrown at runtime.
 * </p>
 * <p>
 * Covariance applies only to arrays of reference types. It does not apply to arrays of primitive types.
 * For example, {@code int[]} is not considered a subtype of {@code Number[]}, even though {@code Integer} is a subtype of
 * {@code Number}.
 * </p>
 * <p>
 * Generics on the other hand, are invariant. Meaning {@code List<Sub>} is not a subtype of {@code List<Super>}.
 * </p>
 * <p>
 * Key concepts demonstrated:
 * <ul>
 *   <li><b>Covariance</b>: Subtyping relationship between arrays of reference types.</li>
 *   <li><b>Runtime Type Checking</b>: {@code ArrayStoreException} when violating array type compatibility.</li>
 *   <li><b>Primitive Type Arrays</b>: Covariance does not apply to arrays of primitive types.</li>
 *   <li><b>Reference Type Arrays</b>: Covariance applies to arrays of reference types.</li>
 * </ul>
 * </p>
 */
public class ArrayCovariance {

    public static void main(String[] args) {
        // Array covariance
        B[] bs = new B[]{new B("B1"), new B("B2")};
        A[] as = bs; // Allowed due to covariance

        System.out.println("Accessing elements of the array created as B[] through as");
        System.out.println(Arrays.toString(as));

        // Runtime check fails, ArrayStoreException is thrown
        try {
            as[0] = new C("C1"); // Runtime exception: ArrayStoreException
        } catch (ArrayStoreException e) {
            System.out.println("Caught ArrayStoreException: " + e.getMessage());
        }

        // Example with primitive types: No covariance
        // int[] is not a subtype of Number[], even though Integer is a subtype of Number
        // Number[] numbers = new int[]{1, 2, 3}; // Compile-time error

        // Example of no autoboxing/unboxing and widening
//         Integer[] numbers = new int[]{}; // Compile-time error
//         int[] numbers = new byte[]{}; // Compile-time error
//        Double numbers = new Integer[]{}; // Compile-time error.

        // Example with different types
        Integer[] integers = new Integer[]{1, 2, 3};
        Number[] numbers = integers; // This is allowed.

        try {
            numbers[0] = 2.5; // This will result in an ArrayStoreException.
        } catch (ArrayStoreException e) {
            System.out.println("Caught ArrayStoreException: " + e.getMessage());
        }
    }

    static class A {
        String name;

        public A(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "A " + name;
        }
    }

    static class B extends A {
        public B(String name) {
            super(name);
        }

        @Override
        public String toString() {
            return "B " + name;
        }
    }


    static class C extends A {
        public C(String name) {
            super(name);
        }

        @Override
        public String toString() {
            return "C " + name;
        }
    }
}