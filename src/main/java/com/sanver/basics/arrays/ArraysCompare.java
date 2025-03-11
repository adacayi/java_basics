package com.sanver.basics.arrays;

import java.util.Arrays;

/**
 * Compares two {@code int} arrays lexicographically.
 *
 * <p>If the two arrays share a common prefix then the lexicographic
 * comparison is the result of comparing two elements, as if by
 * {@link Integer#compare(int, int)}, at an index within the respective
 * arrays that is the prefix length.
 * Otherwise, one array is a proper prefix of the other and, lexicographic
 * comparison is the result of comparing the two array lengths.
 *
 * <p>A {@code null} array reference is considered lexicographically less
 * than a non-{@code null} array reference.  Two {@code null} array
 * references are considered equal.
 *
 * <p>The comparison is consistent with {@link Arrays#equals(int[], int[]) equals},
 * more specifically the following holds for arrays {@code a} and {@code b}:
 * <pre>{@code
 *     Arrays.equals(a, b) == (Arrays.compare(a, b) == 0)
 * }</pre>
 *
 * @apiNote
 * <p>This method behaves as if (for non-{@code null} array references):
 * <pre>{@code
 *     int i = Arrays.mismatch(a, b);
 *     if (i >= 0 && i < Math.min(a.length, b.length))
 *         return Integer.compare(a[i], b[i]);
 *     return a.length - b.length;
 * }</pre>
 *
 * @since 9
 */
public class ArraysCompare {

    public static final String FORMAT = "%-30s : %s%n";

    public static void main(String[] args) {
        noPrefix();
        oneNull();
        bothNull();
        commonPrefix();
        oneEmpty();
        bothEmpty();
    }

    private static void noPrefix() {
        System.out.printf("No prefix%n%n");
        int[] arr1 = {1, 2};
        int[] arr2 = {3, 4};
        System.out.printf(FORMAT, "int[] arr1 = {1, 2}", Arrays.toString(arr1));
        System.out.printf(FORMAT, "int[] arr2 = {3, 4}", Arrays.toString(arr2));
        print(arr1, arr2);
    }

    private static void commonPrefix() {
        System.out.printf("%nCommon prefix%n%n");
        int[] arr1 = {1, 2, 5};
        int[] arr2 = {1, 2, 4};
        System.out.printf(FORMAT, "int[] arr1 = {1, 2, 5}", Arrays.toString(arr1));
        System.out.printf(FORMAT, "int[] arr2 = {1, 2, 4}", Arrays.toString(arr2));
        print(arr1, arr2);
    }

    private static void oneNull() {
        System.out.printf("%nOne null%n%n");
        int[] arr1 = {1, 2};
        int[] arr2 = null;
        System.out.printf(FORMAT, "int[] arr1 = {1, 2}", Arrays.toString(arr1));
        System.out.printf(FORMAT, "int[] arr2 = null", Arrays.toString(arr2));
        print(arr1, arr2);
    }

    private static void bothNull() {
        System.out.printf("%nBoth null%n%n");
        int[] arr1 = null;
        int[] arr2 = null;
        System.out.printf(FORMAT, "int[] arr1 = null", Arrays.toString(arr1));
        System.out.printf(FORMAT, "int[] arr2 = null", Arrays.toString(arr2));
        print(arr1, arr2);
    }

    private static void properPrefix() {
        System.out.printf("%nProper prefix%n%n");
        int[] arr1 = {1, 2};
        int[] arr2 = {1, 2, 3, 4};
        System.out.printf(FORMAT, "int[] arr1 = {1, 2}", Arrays.toString(arr1));
        System.out.printf(FORMAT, "int[] arr2 = {1, 2, 3, 4}", Arrays.toString(arr2));
        print(arr1, arr2);
    }

    private static void oneEmpty() {
        System.out.printf("%nOne empty%n%n");
        int[] arr1 = {};
        int[] arr2 = {3, 4};
        System.out.printf(FORMAT, "int[] arr1 = {}", Arrays.toString(arr1));
        System.out.printf(FORMAT, "int[] arr2 = {3, 4}", Arrays.toString(arr2));
        print(arr1, arr2);
    }

    private static void bothEmpty() {
        System.out.printf("%nBoth empty%n%n");
        int[] arr1 = {};
        int[] arr2 = {};
        System.out.printf(FORMAT, "int[] arr1 = {}", Arrays.toString(arr1));
        System.out.printf(FORMAT, "int[] arr2 = {}", Arrays.toString(arr2));
        print(arr1, arr2);
    }

    private static void print(int[] arr1, int[] arr2) {
        System.out.printf(FORMAT, "Arrays.compare(arr1, arr2)", Arrays.compare(arr1, arr2));
        System.out.printf(FORMAT, "Arrays.compare(arr2, arr1)", Arrays.compare(arr2, arr1));
    }
}
