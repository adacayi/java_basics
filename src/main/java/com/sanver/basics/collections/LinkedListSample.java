package com.sanver.basics.collections;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class LinkedListSample {

    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>(Arrays.asList("Ahmet", "Mustafa", "Muhammed"));
        list.addFirst("Ibrahim");
        list.forEach(System.out::println);

        System.out.println();

        ListIterator<String> iterator = list.listIterator(list.size());

        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }

        System.out.println();

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        System.out.println();

        System.out.println("list.getFirst(): " + list.getFirst());
        System.out.println("list.getLast() : " + list.getLast());
    }
}
