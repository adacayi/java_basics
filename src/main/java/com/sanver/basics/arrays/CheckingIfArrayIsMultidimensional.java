package com.sanver.basics.arrays;

import java.util.Arrays;

public class CheckingIfArrayIsMultidimensional {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 3};
        int[][] array2 = {{1, 2}, {3, 4}};
        Object[] array3 = {new Object[]{new Object(), new Object()}, new Object()};
        Object[] array4 = {new Object[]{new Object(), new Object()}};
        Object[][] array5 = {{new Object(), new Object()}};
        System.out.printf("%s is %s a multidimensional array\n", Arrays.toString(array1), isMultidimensional(array1) ? "" : "not");
        System.out.printf("%s is %s a multidimensional array\n", Arrays.deepToString(array2), isMultidimensional(array2) ? "" : "not");
        System.out.printf("%s is %s a multidimensional array\n", Arrays.deepToString(array3), isMultidimensional(array3) ? "" : "not");
        System.out.printf("%s is %s a multidimensional array\n", Arrays.deepToString(array4), isMultidimensional(array4) ? "" : "not");
        System.out.printf("%s is %s a multidimensional array\n", Arrays.deepToString(array5), isMultidimensional(array5) ? "" : "not");
    }

    public static boolean isMultidimensional(Object array) {
        return array.getClass().isArray() && array.getClass().getComponentType().isArray();
    }
}
