package com.sanver.basics.arrays;

import java.util.Arrays;
import java.util.Comparator;

public class SortingSubArray {

    public static void main(String[] args) {
        Integer[] array = {2, 3, 1, 5, 8, 7};
        System.out.println(Arrays.toString(array));
        Arrays.sort(array, 0, 4, Comparator.comparing((Integer i) -> i).reversed());
        System.out.println(Arrays.toString(array));
    }

}
