package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.sanver.basics.utils.ArrayPrinter.printArray;

public class ArraysaslistSample {

    public static void main(String[] args) {
        // Arrays.asList returns a list that is backed by the array and changes made to the array will be visible in the returned list, and changes made to the list will be visible in the array.
        Integer[] numbers2 = {null, 1, 6};
        var listBackedByArray = Arrays.asList(numbers2); // Changes made to the array will be visible in the returned list, and changes made to the list will be visible in the array.
        listBackedByArray.set(1, 5);
        // listBackedByArray.add(3) This is not supported since Arrays.asList returns a fixed size list
        numbers2[2] = 7;
        printArray(numbers2);
        System.out.println(listBackedByArray);

        // Quick initialisation of an ArrayList
        List<Integer> numbers = new ArrayList<>(Arrays.asList(2, 3, 4, 5, 6)); // We could not change the size of the returned list if it was just Arrays.asList(2, 3, 4, 5, 6), since this returns a fixed size list
        numbers.add(7);
        System.out.println(numbers);

        // Difference with List.of
        Integer[] numbers3 = {3, 1, 6};
        var list = List.of(numbers3); // This will return an immutable list. Also, List.of does not except null elements, while Arrays.asList does.
//        list.set(1, 5); This is not supported since the list is immutable
//        list.add(3); This is not supported since the list is immutable
        numbers3[2] = 7;
        printArray(numbers3);
        System.out.println(list); // notice the list is not changed.

    }
}
