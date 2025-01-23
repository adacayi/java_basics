package com.sanver.basics.arrays;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.generatePermutations;

public class ArraysDeepToString {

    public static void main(String[] args) {
        long[][] a1 = new long[3][4];
        long[] a2 = {2, 3, 1};
        System.out.println(Arrays.deepToString(a1));
        a1[1] = a2;
        System.out.println(Arrays.deepToString(a1));

        Integer[][][] a3 = new Integer[3][3][3]; // if we had new Integer[3][3][] then it will result in null elements in the second dimension, instead of a reference to a new Integer[3]. Our setAll method assumes that we can access the array by a3[i][j][k], thus we need this initialization.
        setAll(a3, a3.length, list -> IntStream.range(0, list.size()).map(i -> (int) Math.pow(10, list.size() - 1f - i) * list.get(i)).sum());
        System.out.println(Arrays.deepToString(a3));
    }

    private static void setAll(Object[] array, int dimension, Function<List<Integer>, Object> calculateValueForIndexes) {
        if (array == null || array.length == 0) {
            return;
        }

        var indexesList = generatePermutations(dimension, dimension - 1);
        indexesList.forEach(indexes -> arraySet(array, indexes, calculateValueForIndexes.apply(indexes)));
    }

    private static void arraySet(Object[] array, List<Integer> indexes, Object value) {
        if (array == null) {
            throw new NullPointerException("Array must not be null");
        }
        if (indexes == null || indexes.isEmpty()) {
            throw new IllegalArgumentException("Invalid index provided");
        }

        if (indexes.size() == 1) {
            array[indexes.getFirst()] = value;
        } else {
            arraySet((Object[]) array[indexes.getFirst()], indexes.subList(1, indexes.size()), value);
        }
    }
}
