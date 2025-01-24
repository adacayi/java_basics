package com.sanver.basics.arrays;

import java.lang.reflect.Array;

import static com.sanver.basics.utils.ArrayUtils.printArrays;

public class ArrayNewInstanceSample {
    public static void main(String[] args) {
        int[][][] array1 = (int[][][]) Array.newInstance(int[][].class, 5);
        int[][][] array2 = (int[][][]) Array.newInstance(int.class, 3, 5, 2);
        printArrays(array1, array2);
    }
}
