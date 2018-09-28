package com.sanver.basics.algorithms;

public class BinarySearch {
    public int search(int[] array, int key) {
        return search(array, 0, array.length, key);
    }

    public int search(int[] array, int fromIndex, int toIndex, int key) {
        int low = fromIndex, high = toIndex - 1, mid, midVal;

        while (low <= high) {
            mid = (low + high) >>> 1;
            midVal = array[mid];

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else
                return mid;
        }

        return -(low + 1);
    }

    public int searchFirst(int[] array, int key) {
        return searchFirst(array, 0, array.length, key);
    }

    public int searchFirst(int[] array, int beginIndex, int endIndex, int key) {
        int low = beginIndex, high = endIndex - 1, mid, midVal, result = -1;

        while (low <= high) {
            mid = (low + high) >>> 1;
            midVal = array[mid];

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else {
                high = mid - 1;
                result = mid;
            }
        }

        if (result != -1)
            return result;

        return -(low + 1);
    }

    public int searchLast(int[] array, int key) {
        return searchLast(array, 0, array.length, key);
    }

    public int searchLast(int[] array, int beginIndex, int endIndex, int key) {
        int low = beginIndex, high = endIndex - 1, mid, midVal, result = -1;

        while (low <= high) {
            mid = (low + high) >>> 1;
            midVal = array[mid];

            if (midVal < key)
                low = mid + 1;
            else if (midVal > key)
                high = mid - 1;
            else {
                low = mid + 1;
                result = mid;
            }
        }

        if (result != -1)
            return result;

        return -(low + 1);
    }
}
