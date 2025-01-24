package com.sanver.basics.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;

import static com.sanver.basics.utils.Utils.deepCopy;

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

    public static <T> T[] arrayDeepCopyOf(T[] original, int newLength) {
        return arrayDeepCopyOf(original, newLength, new HashSet<>());
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] arrayDeepCopyOf(T[] original, int newLength, HashSet<Object[]> dejaVu) {
        if (original == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }

        dejaVu.add(original);
        T[] copy = (original.getClass() == Object[].class) ? (T[]) new Object[newLength] : (T[]) Array.newInstance(original.getClass().getComponentType(), newLength);
        int min = Math.min(original.length, newLength);

        for (int i = 0; i < min; i++) {
            copy[i] = (T) switch (original[i]) {
                case null -> null;
                case byte[] a -> Arrays.copyOf(a, a.length);
                case short[] a -> Arrays.copyOf(a, a.length);
                case int[] a -> Arrays.copyOf(a, a.length);
                case long[] a -> Arrays.copyOf(a, a.length);
                case char[] a -> Arrays.copyOf(a, a.length);
                case float[] a -> Arrays.copyOf(a, a.length);
                case double[] a -> Arrays.copyOf(a, a.length);
                case boolean[] a -> Arrays.copyOf(a, a.length);
                case Byte[] a -> Arrays.copyOf(a, a.length);
                case Short[] a -> Arrays.copyOf(a, a.length);
                case Integer[] a -> Arrays.copyOf(a, a.length);
                case Long[] a -> Arrays.copyOf(a, a.length);
                case Character[] a -> Arrays.copyOf(a, a.length);
                case Float[] a -> Arrays.copyOf(a, a.length);
                case Double[] a -> Arrays.copyOf(a, a.length);
                case Boolean[] a -> Arrays.copyOf(a, a.length);
                case Byte a -> a;
                case Short a -> a;
                case Integer a -> a;
                case Long a -> a;
                case Character a -> a;
                case Float a -> a;
                case Double a -> a;
                case Boolean a -> a;
                case Object[] a -> {
                    if (dejaVu.contains(a)) {
                        throw new IllegalStateException("Circular reference detected in array: " + Arrays.deepToString(a));
                    }

                    yield arrayDeepCopyOf(a, a.length, dejaVu);
                }
                default -> deepCopy(original[i]);
            };
        }

        dejaVu.remove(original);
        return copy;
    }
}
