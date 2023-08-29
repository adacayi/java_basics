package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class ArrayListSample {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("Ahmet", "Mustafa", "Salih"));// You don't need to specify
        // ArrayList type on the right
        // since Java can
        // infer it from the left hand-side.
        // i.e. there is no need to write ArrayList<String>(3) on the right.
        list.add("Ibrahim");
        list.set(2, "Muhammed");

        list.forEach(System.out::println);

        System.out.println();

        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

        System.out.println();

        ListIterator<String> iterator = list.listIterator(list.size()); // This sets the iterator to the end of the list

        while (iterator.hasPrevious())
            System.out.println(iterator.previous());

        System.out.println();

        var iterator2 = list.listIterator(0);
        while (iterator2.hasNext()) {
            System.out.println(iterator2.next());
        }

        System.out.println();

        var iterator3 = list.listIterator();

        while (iterator3.hasNext()) {
            System.out.println(iterator3.next());
        }

        System.out.println();

        while (iterator3.hasPrevious()) {
            System.out.println(iterator3.previous());
        }
    }
}
