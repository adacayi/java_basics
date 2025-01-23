package com.sanver.basics.arrays;

import java.util.Arrays;

public class CheckingIfArrayIsMultidimensional {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 3};
        int[][] array2 = {{1, 2}, {3, 4}};
        Object[] array3 = {new Object[]{new Object(), new Object()}, new Object()};
        Object[] array4 = {new Object[]{new Object(), new Object()}};
        Object[][] array5 = {{new Object(), new Object()}};
        var format = "%-84s is %-3s a multidimensional array. Component type: %s%n";
        System.out.printf(format, Arrays.toString(array1), isMultidimensional(array1) ? "" : "not", getComponentType(array1));
        System.out.printf(format, Arrays.deepToString(array2), isMultidimensional(array2) ? "" : "not", getComponentType(array2));
        System.out.printf(format, Arrays.deepToString(array3), isMultidimensional(array3) ? "" : "not", getComponentType(array3));
        System.out.printf(format, Arrays.deepToString(array4), isMultidimensional(array4) ? "" : "not", getComponentType(array4));
        System.out.printf(format, Arrays.deepToString(array5), isMultidimensional(array5) ? "" : "not", getComponentType(array5));
    }

    public static boolean isMultidimensional(Object array) {
        return array.getClass().isArray() && array.getClass().getComponentType().isArray();
    }

    private static String getComponentType(Object array) {
        return array.getClass().getComponentType().toString();
    }
}
