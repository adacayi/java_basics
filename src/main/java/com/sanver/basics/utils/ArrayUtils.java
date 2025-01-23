package com.sanver.basics.utils;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayUtils {
    private ArrayUtils() {

    }

    public static void printArray(byte[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void printArray(short[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void printArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void printArray(long[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void printArray(char[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void printArray(float[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void printArray(double[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void printArray(boolean[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void printArray(Object[] array) {
        System.out.println(Arrays.deepToString(array));
    }

    /**
     * This method is to print out multiple arrays, each in a separate line
     *
     * @param arrays The arrays to be printed
     */
    public static void printArrays(Object... arrays) {
        for (var array : arrays) {
            switch (array) {
                case byte[] a -> printArray(a);
                case short[] a -> printArray(a);
                case int[] a -> printArray(a);
                case long[] a -> printArray(a);
                case char[] a -> printArray(a);
                case float[] a -> printArray(a);
                case double[] a -> printArray(a);
                case boolean[] a -> printArray(a);
                case Object[] a -> printArray(a);
                default -> System.out.printf("%s is not an array%n", array);
            }
        }
    }

    public static <T> T[] arrayDeepCopyOf(T[] original) {
        return arrayDeepCopyOf(original, original.length);

    }

    @SuppressWarnings("unchecked")
    public static <T> T[] arrayDeepCopyOf(T[] original, int newLength) {
        T[] copy = (original.getClass() == Object[].class) ? (T[]) new Object[newLength] : (T[]) Array.newInstance(original.getClass().getComponentType(), newLength);

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
                copy[i] = original[i]; // This actually needs to be a deep copy of an object if we want the array objects to be different if they are not primitive types
        }

        return copy;
    }
}
