package com.sanver.basics.arrays;

import java.util.Arrays;

/**
 * Demonstrates the process of generating hash codes for arrays in Java using the standard
 * {@code hashCode}, {@code Arrays.hashCode}, and {@code Arrays.deepHashCode} methods.
 *
 * This class highlights:
 * <ul>
 *   <li>Comparison of hash codes for one-dimensional arrays using {@code hashCode} and {@code Arrays.hashCode}.</li>
 *   <li>Usage of {@code Arrays.deepHashCode} for computing hash codes of multidimensional arrays.</li>
 *   <li>Differences in hash code calculations for single-level, nested arrays and arrays of primitive and non-primitive types.</li>
 * </ul>
 *
 * <p><strong>Key Methods:</strong></p>
 * <ul>
 *   <li>{@code hashCode}: Generates the default hash code for an object.</li>
 *   <li>{@code Arrays.hashCode}: Computes a hash code based on one-dimensional array contents.</li>
 *   <li>{@code Arrays.deepHashCode}: Computes a hash code based on nested or string array contents.</li>
 * </ul>
 *
 * <ul>
 *   <li>Displays the hash codes for various types of arrays, including:
 *       <ul>
 *         <li>One-dimensional arrays of primitives.</li>
 *         <li>Multi-dimensional arrays (nested arrays).</li>
 *         <li>One-dimensional arrays of strings.</li>
 *       </ul>
 *   </li>
 *   <li>Highlights the differences in {@code hashCode}, {@code Arrays.hashCode}, and {@code Arrays.deepHashCode}
 *       across these cases.</li>
 * </ul>
 */
public class ArraysHashCode {
    public static void main(String[] args) {
        int[] array1 = {3, 2, 5, 1};
        int[][] array2 = {array1};
        String[] array3 = {"b", "a", "c"};
        var format = "Array: %-20s hashCode: %-8x Arrays.hashCode %-8x Arrays.deepHashCode %s%n";
        System.out.printf(format, Arrays.toString(array1), array1.hashCode(), Arrays.hashCode(array1), "NA"); // There is no overload of Arrays.deepHashCode for arrays of primitive types, since they are one dimensional.
        System.out.printf(format, Arrays.toString(array2), array2.hashCode(), Arrays.hashCode(array2), Integer.toString(Arrays.deepHashCode(array2), 16));
        System.out.printf(format, Arrays.toString(array3), array3.hashCode(), Arrays.hashCode(array3), Integer.toString(Arrays.deepHashCode(array3), 16)); // Note that Arrays.hashCode and Arrays.deepHashCode are same for this one dimensional array.
    }
}
