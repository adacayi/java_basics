package com.sanver.basics.algorithms;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class MergeSort {
    public static void main(String[] args) {
        int[] array = {7, 2, 1, 6, 8, 5, 3, 4};
        System.out.println("Initial array: " + Arrays.toString(array));
        mergeSort(array);
        System.out.println("Sorted array : " + Arrays.toString(array));
    }

    public static void mergeSort(int[] array) {
        ForkJoinPool.commonPool().invoke(new Sort(array, 0, array.length - 1));
    }

    static class Sort extends RecursiveAction {
        private final int[] array;
        private final int start;
        private final int end;

        public Sort(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (start >= end) {
                return;
            }

            int middle = (start + end) / 2;
            var left = new Sort(array, start, middle);
            var right = new Sort(array, middle + 1, end);
            ForkJoinTask.invokeAll(List.of(left, right));
            merge(middle);
        }

        private void merge(int middle) {
            int[] left = new int[middle - start + 1];
            int[] right = new int[end - middle];
            System.arraycopy(array, start, left, 0, left.length);
            System.arraycopy(array, middle + 1, right, 0, right.length);
            int leftIndex = 0;
            int rightIndex = 0;
            int i = start;

            while (leftIndex < left.length && rightIndex < right.length) {
                if (left[leftIndex] > right[rightIndex]) {
                    array[i++] = right[rightIndex++];
                } else{
                    array[i++] = left[leftIndex++];
                }
            }

            while(leftIndex < left.length) {
                array[i++] = left[leftIndex++];
            }

            while (rightIndex < right.length) {
                array[i++] = right[rightIndex++];
            }
        }
    }
}

