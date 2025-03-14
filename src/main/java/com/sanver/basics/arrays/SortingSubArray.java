package com.sanver.basics.arrays;

import java.util.Arrays;
import java.util.Comparator;

import static com.sanver.basics.utils.ArrayUtils.printArray;

public class SortingSubArray {

    public static void main(String[] args) {
        Integer[] array = {2, 3, 1, 5, 8, 7};
        printArray(array);
        Arrays.sort(array, 0, 4, Comparator.<Integer>naturalOrder().reversed());
        printArray(array);
    }
}
