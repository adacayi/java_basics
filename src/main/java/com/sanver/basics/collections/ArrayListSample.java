package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ArrayListSample {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(List.of("Ahmet", "Mustafa", "Veli", "Salih"));
        // You don't need to specify ArrayList type on the right since Java can infer it from the left hand-side.
        // i.e. there is no need to write ArrayList<String>(3) on the right.
        list.add("Ibrahim");
        list.set(2, "Muhammed");

        System.out.printf("List.forEach: %n");
        list.forEach(System.out::println);

        System.out.printf("%n%nList.get(i): %n");

        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

        System.out.printf("%n%nList.iterator(): %n");
        var iterator = list.iterator(); // This iterator does not have hasPrevious and previous methods.

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.printf("%n%nList.listIterator(list.size()) and listIterator.previous() showing index with listIterator.previousIndex(): %n");
        ListIterator<String> listIterator = list.listIterator(list.size()); // This sets the iterator to the end of the list

        while (listIterator.hasPrevious()) {
            System.out.printf("%d- %s%n", listIterator.previousIndex(), listIterator.previous());
        }

        System.out.printf("%n%nList.listIterator() showing index with listIterator.nextIndex(): %n");
        var iterator2 = list.listIterator(0); // This sets the iterator to the start of the list, similar to list.listIterator()
        while (iterator2.hasNext()) {
            System.out.printf("%d- %s%n", iterator2.nextIndex(), iterator2.next());
        }

        System.out.printf("%n%nList.listIterator(0): %n");
        var iterator3 = list.listIterator(0); // This sets the iterator to the start of the list, similar to list.listIterator()
        while (iterator3.hasNext()) {
            System.out.println(iterator3.next());
        }

        System.out.printf("%n%nList.listIterator(0).previous() with iterator.remove(): %n");
        while (iterator3.hasPrevious()) {
            var previous = iterator3.previous();

            if (previous.equals("Salih")) {
                iterator3.remove();
            }

            System.out.println(previous);
        }

        System.out.printf("%nFinal list after iterator.remove() %n%s%n", list);
    }
}
