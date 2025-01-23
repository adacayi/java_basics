package com.sanver.basics.utils;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayUtils {
    private ArrayUtils() {

    }

    /**
     * This method is for printing out a single array.
     * @param array The array that will be printed out
     */
    public static void printArray(Object array) {
        var aClass = array.getClass();

        if (!aClass.isArray()) {
            throw new IllegalArgumentException("Argument is not an array");
        }

        if (aClass == byte[].class)
            System.out.println(Arrays.toString((byte[]) array));
        else if (aClass == short[].class)
            System.out.println(Arrays.toString((short[]) array));
        else if (aClass == int[].class)
            System.out.println(Arrays.toString((int[]) array));
        else if (aClass == long[].class)
            System.out.println(Arrays.toString((long[]) array));
        else if (aClass == char[].class)
            System.out.println(Arrays.toString((char[]) array));
        else if (aClass == float[].class)
            System.out.println(Arrays.toString((float[]) array));
        else if (aClass == double[].class)
            System.out.println(Arrays.toString((double[]) array));
        else if (aClass == boolean[].class)
            System.out.println(Arrays.toString((boolean[]) array));
        else {
            System.out.println(Arrays.deepToString((Object[])array));
        }
    }

    /**
     * This method is to print out multiple arrays, each in a separate line
     * @param arrays The arrays to be printed
     */
    public static void printArrays(Object... arrays) {
        for (var array : arrays) {
            printArray(array);
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
