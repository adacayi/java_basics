package com.sanver.basics.arrays;

import java.util.Arrays;

public class CheckingArrayEquality {

    public static void main(String[] args) {
        int[] array1 = {2, 3, 1};
        int[] array2 = {2, 3, 1};
        int[][] array3 = {{2, 1}, {3, 2}};
        int[][] array4 = {{2, 1}, {3, 2}};
        System.out.println(array1 == array2);
        System.out.println(Arrays.equals(array1, array2));
        // System.out.println(Arrays.deepEquals(array1, array2)); This won't compile
        // since arrays compared are one dimensional
        System.out.println("\n" + (array3 == array4));
        System.out.println(Arrays.equals(array3, array4));
        System.out.println(Arrays.deepEquals(array3, array4));// This works since arrays compared are multidimensional
    }
}
