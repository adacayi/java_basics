package com.sanver.basics.arrays;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CheckingArrayEquality {
    public static void main(String[] args) {
        int[] first = IntStream.range(0, 10).toArray();
        int[] second = IntStream.range(0, 10).toArray();
        int[] third = IntStream.range(3, 7).toArray();

        int[][] fourth = {{1, 2}, {3, 4, 5, 6}};
        int[][] fifth = {{1, 2}, {3, 4, 5, 6}};

        String[] strings1 = {"1", "2", "3"};
        String[] strings2 = {"1", "2", "3"};

        System.out.println("first == second: " + (first == second));
        System.out.println("Arrays.equals(first, second): " + Arrays.equals(first, second));
        System.out.println("Arrays.equals(first, third): " + Arrays.equals(first, third));
        System.out.println("Arrays.equals(first, 3, 7, third, 0 , third.length): " + Arrays.equals(first, 3, 7, third, 0, third.length));
        System.out.println("Arrays.equals(fourth, fifth): " + Arrays.equals(fourth, fifth));
        System.out.println("Arrays.deepEquals(fourth, fifth): " + Arrays.deepEquals(fourth, fifth));
        // Arrays.deepEquals(first, second); // This won't compile since there is no overload for deepEquals method for primitive arrays.
        System.out.println("strings1 == strings2: " + (strings1 == strings2));
        System.out.println("Arrays.equals(strings1, strings2): " + Arrays.equals(strings1, strings2));
        System.out.println("Arrays.deepEquals(strings1, strings2): " + Arrays.deepEquals(strings1, strings2));
    }

}
