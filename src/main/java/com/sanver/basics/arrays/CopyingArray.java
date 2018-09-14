package com.sanver.basics.arrays;

import java.util.Arrays;

public class CopyingArray {

    public static void main(String[] args) {
        int[] a, b = {3, 2, 1};
        int[][] b1 = {{0, 1, 3}, {2, 4}, {5, 2}};
        int[][] a1;
        a = Arrays.copyOf(b, 5);
        b[1] = 4;
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));
        a1 = Arrays.copyOf(b1, 4);
        b1[0][0] = 7;// Note that a1 changes as well when changing an element of an inner array of b1.
        printArrays(a1, b1);
        a1 = b1.clone();
        System.out.println(a1==b1);
        b1[0][0] = 8;// Note that a1 changes as well when changing an element of an inner array of b1.
        printArrays(a1, b1);
        System.arraycopy(b1, 0, a1, 0, b1.length);
        b1[0][0] = 9;// Note that a1 changes as well when changing an element of an inner array of b1.
        printArrays(a1, b1);
//        a1 = arrayDeepCopyOf(b1, 4);  // This gives runtime error. [Ljava.lang.Object; cannot be cast to [[I
        // Search it further to develop a deep copy method that copies the inner arrays as well.
    }

    private static void printArrays(int[][] b1, int[][] a1) {
        System.out.println(Arrays.deepToString(a1));
        System.out.println(Arrays.deepToString(b1));
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] arrayDeepCopyOf(T[] original, int newLength) {
        T[] copy = (T[]) new Object[newLength];
        Class<?> eClass;
        int min = Math.min(original.length, newLength);

        for (int i = 0; i < min; i++) {
            eClass = original[i].getClass();

            if (eClass.isArray()) {
                if (eClass == byte[].class)
                    copy[i] = (T) Arrays.copyOf((byte[]) original[i], ((byte[]) original[i]).length);
                else if (eClass == short[].class)
                    copy[i] = (T) Arrays.copyOf((short[]) original[i], ((short[]) original[i]).length);
                else if (eClass == int[].class)
                    copy[i] = (T) Arrays.copyOf((int[]) original[i], ((int[]) original[i]).length);
                else if (eClass == long[].class)
                    copy[i] = (T) Arrays.copyOf((long[]) original[i], ((long[]) original[i]).length);
                else if (eClass == char[].class)
                    copy[i] = (T) Arrays.copyOf((char[]) original[i], ((char[]) original[i]).length);
                else if (eClass == float[].class)
                    copy[i] = (T) Arrays.copyOf((float[]) original[i], ((float[]) original[i]).length);
                else if (eClass == double[].class)
                    copy[i] = (T) Arrays.copyOf((double[]) original[i], ((double[]) original[i]).length);
                else if (eClass == boolean[].class)
                    copy[i] = (T) Arrays.copyOf((boolean[]) original[i], ((boolean[]) original[i]).length);
                else { // element is an array of object references
                    copy[i] = (T) arrayDeepCopyOf((Object[]) original[i], ((Object[]) original[i]).length);
                }
            } else
                copy[i] = original[i];
        }

        return copy;
    }
}
