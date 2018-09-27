package com.sanver.basics.algorithms;

public class BinarySearch {
    public int search(int[] array, int key) {
        return search(array, 0, array.length, key);
    }

    public int search(int[] array, int beginIndex, int endIndex, int key) {
        int low = beginIndex, high = endIndex - 1, mid;

        while (low <= high) {
            mid = (low + high) >>> 1;
            if (array[mid] < key)
                low = mid + 1;
            else if (array[mid] > key)
                high = mid - 1;
            else
                return mid;
        }

        return -(low + 1);
    }
}
