package com.sanver.basics.collections;

import java.util.Arrays;
import java.util.List;

public class ArraysAsListListOfDifferences {
    public static void main(String[] args) {
        Integer[] values = {3, 2, 5};
        var list = Arrays.asList(values); // We cannot add or remove elements from this list, but we can change its items.
//        list.remove(0); // This results in an UnsupportedOperationException.
        var list1 = List.copyOf(list); // this results in an immutable list.
//        list1.sort(null); // This results in an UnsupportedOperationException.
        list.sort(null);
        System.out.println(list);
        System.out.println(list1);
    }
}
