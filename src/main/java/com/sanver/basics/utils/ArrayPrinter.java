package com.sanver.basics.utils;

import java.util.Arrays;

public class ArrayPrinter {
    /**
     * This method is for printing out single dimensional arrays.
     * If an array is multidimensional, printArray(Object[] array) method is called as the appropriate overload.
     * @param array The array that will be printed out
     */
    public static void printArray(Object array) {
        var aClass = array.getClass();

        if (!aClass.isArray()) {
            throw new IllegalArgumentException("Argument is not an array");
        }

        if (aClass == byte[].class)
            System.out.println(Arrays.toString((byte[]) array));
        else if (aClass == short[].class)
            System.out.println(Arrays.toString((short[]) array));
        else if (aClass == int[].class)
            System.out.println(Arrays.toString((int[]) array));
        else if (aClass == long[].class)
            System.out.println(Arrays.toString((long[]) array));
        else if (aClass == char[].class)
            System.out.println(Arrays.toString((char[]) array));
        else if (aClass == float[].class)
            System.out.println(Arrays.toString((float[]) array));
        else if (aClass == double[].class)
            System.out.println(Arrays.toString((double[]) array));
        else if (aClass == boolean[].class)
            System.out.println(Arrays.toString((boolean[]) array));
        else {
            printArray((Object[]) array); // We call the other overload instead of using Arrays.toString() to convert the array to string. The reason behind is, array might be a multidimensional array that is casted to object (e.g. Object array = (Object) new int[][]{ {1,2}, {3,4} }.
        }
    }

    /**
     * This method is printing out multidimensional arrays and is also called for single dimensional Object[] arrays as well.
     * @param array The array that will be printed out
     */
    public static void printArray(Object[] array) {
        if (!array.getClass().isArray()) {
            throw new IllegalArgumentException("Argument is not an array");
        }
        System.out.println(Arrays.deepToString(array));
    }

    /**
     * This method is to print out multiple arrays, each in a separate line
     * @param arrays The arrays to be printed
     */
    public static void printArrays(Object... arrays) {
        for (var array : arrays) {
            if (array instanceof Object[]) {
                printArray((Object[]) array);
            } else {
                printArray(array);
            }
        }
    }
}
