package com.sanver.basics.collections;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import static com.sanver.basics.utils.ArrayUtils.printArray;

public class ToArrayAndToListSample {

    public static void main(String[] args) {
        List<Integer> numberList = new ArrayList<>();
        numberList.add(7);
        Integer[] values = {2, 3, 6};
        List<Integer> valueList = new ArrayList<>(Arrays.asList(values));// Converting an array to a list.
        // Arrays.asList(values) returns a list
        // backed by the array. Hence its size is
        // fixed and when we try to add a new item
        // it throws an exception. So we generated
        // an ArrayList from asList result.
        valueList.add(8);
        numberList.addAll(valueList);
        Integer[] largerResult = new Integer[numberList.size() + 4];
        Arrays.parallelSetAll(largerResult, x -> 1);
        Integer[] resultArray = numberList.toArray(values);
        // If the list fits in the specified array, it is returned therein. Otherwise, a
        // new array is allocated with the runtime type of the specified array and the
        // size of this list.
        // If the list fits in the specified array with room to spare (i.e., the array
        // has more elements than the list), the element in the array immediately
        // following the end of the list is set to null.

        // toArray with no parameter returns object array which needs
        // casting.
        System.out.println(values == resultArray);
        printArray(resultArray);
        resultArray = numberList.toArray(largerResult);
        System.out.println(largerResult == resultArray);
        printArray(resultArray);
    }
}
