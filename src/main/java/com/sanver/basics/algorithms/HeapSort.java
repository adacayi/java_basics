package com.sanver.basics.algorithms;

import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        int[] array = {7, 2, 1, 6, 8, 5, 3, 4};
        System.out.println("Initial array: " + Arrays.toString(array));
        heapSort(array);
        System.out.println("Sorted array : " + Arrays.toString(array));
    }

    /**
     * Returns the index of the parent of the item with the given key
     *
     * @param key The key of the item, the parent of which needs to be found
     * @return The key of the parent of the item with the given key
     */
    private static int parent(int key) {
        return (key - 1) / 2;
    }

    // Returns the left child of the item with the given key

    /**
     * Returns the index of the left child of the item with the given key
     *
     * @param key The key of the item, the left child of which needs to be found
     * @return The key of the left child of the item with the given key
     */
    private static int left(int key) {
        return key * 2 + 1;
    }

    /**
     * Returns the index of the right child of the item with the given key
     *
     * @param key The key of the item, the right child of which needs to be found
     * @return The key of the right child of the item with the given key
     */
    private static int right(int key) {
        return key * 2 + 2;
    }

    /**
     * Swaps the two elements of the array with the given indexes.
     *
     * @param array  The array with elements needs to be swapped.
     * @param first  Index of the first element to be swapped with the second.
     * @param second Index of the second element to be swapped with the first.
     */
    private static void swap(int[] array, int first, int second) {
        int temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }

    /**
     * Assuming the given array is a complete binary tree, with the given size, places the item with the key in the subtree, so that it is less than or equal to its children in the end.
     *
     * @param array  Array representing a complete binary tree
     * @param key    The key of the item which will be placed so that it adheres to a min-heap binary heap (it is less than or equal to its children)
     * @param length The size of the array (the size of the represented binary tree), which might not be the same as the length of the array if some elements were removed and placed at the end of the array.
     */
    private static void heapify(int[] array, int key, int length) {
        if (length < 2 || key < 0 || key >= length || array == null) {
            return;
        }

        int left;
        int right;
        int max;
        int lastInternalNodeKey = parent(length - 1);

        while (key <= lastInternalNodeKey) { // Check only nodes with at least one child
            max = key;
            left = left(key);
            right = right(key);

            if (array[left] > array[max]) { // No need to check if left < length, since key <= lastInternalNodeKey asserts that the node has at least a left child.
                max = left;
            }

            if (right < length && array[right] > array[max]) {
                max = right;
            }

            if (key == max) {
                break;
            }

            swap(array, key, max);
            key = max;
        }
    }

    /**
     * Removes the root of the binary heap by swapping it with the last item of the current heap
     *
     * @param array       Array representing a binary heap
     * @param currentSize The size of the current binary heap to work with
     * @return The root of the binary heap
     */
    private static int removeRoot(int[] array, int currentSize) {
        if (currentSize < 1 || array == null || array.length == 0) {
            throw new IllegalStateException("No root exists");
        }

        swap(array, 0, currentSize - 1);
        heapify(array, 0, currentSize - 1);

        return array[currentSize - 1];
    }

    /**
     * Sorts the array by treating it as a complete binary tree representation, using heapify from the bottom of the internal nodes (nodes that have at least one child) to make it a max-heap binary heap and then removing the root by swapping it with the last element of the array and heapify the root to make it a max-heap again.
     * Since the root will be the max element of the max-heap, each time we remove the root and swap it with the last item of the remaining array, we essentially sort the array in-place by moving the largest elements one by one to the end of the array.
     *
     * @param array Array that will be heap sorted.
     */
    private static void heapSort(int[] array) {
        if (array == null) {
            return;
        }

        int length = array.length;

        if (length < 2) {
            return;
        }

        int lastInternalNodeKey = parent(length - 1); // This is the last internal node's key. i.e. after this node all the nodes are leaf.

        for (int i = lastInternalNodeKey; i >= 0; i--) {
            heapify(array, i, length);
        }

        for (int currentSize = length; currentSize > 1; currentSize--) {
            removeRoot(array, currentSize); // Remove the root by swapping it with the last item of the binary heap. Current size is the size of the binary heap we want to operate with. After each remove, the size of the binary heap will be reduced by one and the largest element of the current binary heap will be at the end of that heap, eventually sorting the array from last element to the first.
        }
    }
}
