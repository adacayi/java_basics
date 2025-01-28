package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ToListSample {
    public static void main(String[] args) {
        int[] array = {4, 1, 2, 3};
        List<Integer> list = Arrays.stream(array).boxed().toList(); // toList is available since Java 16. The returned List is unmodifiable; calls to any mutator method will always cause UnsupportedOperationException to be thrown.
        System.out.println(list);
//        list.sort(Comparator.naturalOrder()); // This throws java.lang.UnsupportedOperationException, since toList() constructs an unmodifiable list.
        List<Integer> list2 = Arrays.stream(array).boxed().collect(Collectors.toList());// This returns a modifiable list
        list2.add(5);
        list2.sort(Comparator.naturalOrder());
        System.out.println(list2);
    }
}
