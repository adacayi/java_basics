package com.sanver.basics.arrays;

import java.util.Arrays;

public class CheckingIfArrayIsMultidimensional {
    public static void main(String[] args) {
        int[] array1 = {1, 2, 3};
        int[][] array2 = {{1, 2}, {3, 4}};
        Object[] array3 = {new Object[]{new Object(), new Object()}, new Object()};
        Object[] array4 = {new Object[]{new Object(), new Object()}};
        Object[][] array5 = {{new Object(), new Object()}};
        var format = "%s is %s a multidimensional array%n";
        System.out.printf(format, Arrays.toString(array1), isMultidimensional(array1) ? "" : "not");
        System.out.printf(format, Arrays.deepToString(array2), isMultidimensional(array2) ? "" : "not");
        System.out.printf(format, Arrays.deepToString(array3), isMultidimensional(array3) ? "" : "not");
        System.out.printf(format, Arrays.deepToString(array4), isMultidimensional(array4) ? "" : "not"); // Even though the only element in array4 is an Object[], since  array4.getClass().getComponentType() returns Object, instead of Object[] isMultidimensional will return false. So, if you use this approach to figure out if an array is multidimensional or not and this returns false, it does not mean that it can contain arrays, as shown in this example.
        System.out.printf(format, Arrays.deepToString(array5), isMultidimensional(array5) ? "" : "not");
    }

    public static boolean isMultidimensional(Object array) {
        return array.getClass().isArray() && array.getClass().getComponentType().isArray();
    }
}
