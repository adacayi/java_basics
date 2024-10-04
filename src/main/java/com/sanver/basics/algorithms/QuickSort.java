package com.sanver.basics.algorithms;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int[] array = {7, 2, 1, 6, 8, 5, 3, 4};
        System.out.println("Initial array: " + Arrays.toString(array));
        quickSort(array, 0, array.length - 1);
        System.out.println("Sorted array : " + Arrays.toString(array));
    }

    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(array, low, high);
            quickSort(array, low, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, high);
        }
    }

    public static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low;
        int temp;

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
            }
        }

        temp = array[i];
        array[i] = pivot;
        array[high] = temp;

        return i;
    }
}
