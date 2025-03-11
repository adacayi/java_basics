package com.sanver.basics.arrays;

import java.util.Arrays;

/**
 * Demonstrates the usage of {@link Arrays#mismatch(int[], int[])} and explains
 * its behavior in various scenarios.
 * <p>
 * The {@code Arrays.mismatch(int[], int[])} method in Java is used to find the
 * index of the first mismatch between two int arrays. It compares the arrays
 * element by element, starting from the first element.
 * </p>
 * <p>
 * <b>How it works:</b>
 * <ul>
 *   <li>
 *     <b>Element-wise Comparison:</b> The method compares corresponding
 *     elements from each array.
 *   </li>
 *   <li>
 *     <b>First Mismatch:</b> If a mismatch is found (i.e.,
 *     {@code arr1[i] != arr2[i]}), the method returns the index {@code i} where
 *     the mismatch occurred.
 *   </li>
 *   <li>
 *     <b>Prefix Check:</b> If one array is a prefix of the other (i.e., all
 *     elements of the shorter array match the beginning of the longer array),
 *     the method returns the length of the shorter array.
 *   </li>
 *   <li>
 *     <b>Equality:</b> If both arrays are of the same length and all their
 *     elements are equal, the method returns -1.
 *   </li>
 *   <li>
 *      <b>Null Arrays:</b> If either array is null, a NullPointerException will be thrown.
 *   </li>
 * </ul>
 * </p>
 * <p>
 * <b>Return Values:</b>
 * <ul>
 *   <li><b>Non-negative Integer:</b> The index of the first mismatch if a
 *   mismatch is found.</li>
 *   <li><b>-1:</b> If the two arrays are equal.</li>
 *   <li><b>Integer within the range of array lengths:</b> If one of the arrays is a prefix of the other, index will be the length of the shorter array.</li>
 * </ul>
 * </p>
 * <p>
 *     <b>Note:</b> Unlike Arrays.compare, Arrays.mismatch throws a NullPointerException, if any of the arrays is null.
 * </p>
 */
public class ArraysMismatch {
    public static void main(String[] args) {
        String format = "%-45s : %d%n";

        // Example 1: Equal arrays
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 3};
        System.out.printf(format, "Arrays.mismatch({1, 2, 3}, {1, 2, 3})", Arrays.mismatch(arr1, arr2)); // Output: -1

        // Example 2: arr1 and arr2 mismatch at index 1
        arr1 = new int[]{1, 2, 3};
        arr2 = new int[]{1, 4, 3};
        System.out.printf(format, "Arrays.mismatch({1, 2, 3}, {1, 4, 3})", Arrays.mismatch(arr1, arr2)); // Output: 1

        // Example 3: arr1 and arr2 mismatch at index 0
        arr1 = new int[]{5, 2, 3};
        arr2 = new int[]{1, 4, 3};
        System.out.printf(format, "Arrays.mismatch({5, 2, 3}, {1, 4, 3})", Arrays.mismatch(arr1, arr2)); // Output: 0

        // Example 4: arr1 is a prefix of arr2
        arr1 = new int[]{1, 2};
        arr2 = new int[]{1, 2, 3};
        System.out.printf(format, "Arrays.mismatch({1, 2}, {1, 2, 3})", Arrays.mismatch(arr1, arr2)); // Output: 2

        // Example 5: arr2 is a prefix of arr1
        arr1 = new int[]{1, 2, 3};
        arr2 = new int[]{1, 2};
        System.out.printf(format, "Arrays.mismatch({1, 2, 3}, {1, 2})", Arrays.mismatch(arr1, arr2)); // Output: 2

        // Example 6: Empty arrays
        arr1 = new int[]{};
        arr2 = new int[]{};
        System.out.printf(format, "Arrays.mismatch({}, {})", Arrays.mismatch(arr1, arr2)); // Output: -1

        // Example 7: Empty vs non-empty (empty is a prefix of non-empty)
        arr1 = new int[]{};
        arr2 = new int[]{1, 2};
        System.out.printf(format, "Arrays.mismatch({}, {1, 2})", Arrays.mismatch(arr1, arr2)); // Output: 0

        // Example 8: Non-empty vs empty (empty is a prefix of non-empty)
        arr1 = new int[]{1, 2};
        arr2 = new int[]{};
        System.out.printf(format, "Arrays.mismatch({1, 2}, {})", Arrays.mismatch(arr1, arr2)); // Output: 0

        // Example 9: null array.
        try {
            arr1 = null;
            arr2 = new int[]{1, 2};
            System.out.printf(format, "Arrays.mismatch(null, {1, 2})", Arrays.mismatch(arr1, arr2));
        } catch (NullPointerException e) {
            System.out.printf("NullPointerException when Arrays.mismatch(null, {1, 2})%n");
        }

        // Example 10: null array.
        try {
            arr1 = new int[]{1, 2};
            arr2 = null;
            System.out.printf(format, "Arrays.mismatch({1, 2}, null)", Arrays.mismatch(arr1, arr2));
        } catch (NullPointerException e) {
            System.out.printf("NullPointerException when Arrays.mismatch({1, 2}, null)%n");
        }

        // Example 11: different elements and different lengths
        arr1 = new int[]{1, 2, 3, 4};
        arr2 = new int[]{5, 6, 7};
        System.out.printf(format, "Arrays.mismatch({1, 2, 3, 4}, {5, 6, 7})", Arrays.mismatch(arr1, arr2)); // Output: 0
    }
}