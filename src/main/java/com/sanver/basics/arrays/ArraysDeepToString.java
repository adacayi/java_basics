package com.sanver.basics.arrays;

import java.util.Arrays;

public class ArraysDeepToString {

    public static void main(String[] args) {
        long[][] a1 = new long[3][4];
        long[] a2 = {2, 3, 1};
        System.out.printf("%s\n", Arrays.deepToString(a1));
        a1[1] = a2;
        System.out.printf("%s\n", Arrays.deepToString(a1));
    }
}
