package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ArrayListSample {

    public static void main(String[] args) {
        var list = new ArrayList<>(List.of(1, 3, 5, 6, 9)); // List.of results in an immutable list, but ArrayList is mutable.
        list.add(11);
        list.set(3, 7);

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
        ListIterator<Integer> listIterator = list.listIterator(list.size()); // This sets the iterator to the end of the list

        while (listIterator.hasPrevious()) {
            System.out.printf("%d- %s%n", listIterator.previousIndex(), listIterator.previous());
        }

        System.out.printf("%n%nList.listIterator() showing index with listIterator.nextIndex(): %n");
        var iterator2 = list.listIterator(); // This sets the iterator to the start of the list, similar to list.listIterator(0)
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

            if (previous == 7) {
                iterator3.remove();
            }

            System.out.println(previous);
        }

        System.out.printf("%nList after iterator.remove() %n%s%n", list);

        list.removeIf(x -> x == 9);
        System.out.printf("%nList after list.removeIf(x -> x == 9) %n%s%n", list);

        list.replaceAll(x -> x > 3 ? x * 2 : x);
        System.out.printf("%nList after list.replaceAll(x -> x > 3 ? x * 2 : x); %n%s%n", list);

        var subList = list.subList(1, 3);
        System.out.printf("%nlist.subList(1, 3): %s%n", subList);

        subList.set(1, 5);
        System.out.printf("%nAfter subList.set(1, 5)%n");
        print(subList, list);

        subList.add(7);
        System.out.printf("%nAfter subList.add(7)%n");
        print(subList, list);

        subList.remove(1);
        System.out.printf("%nAfter subList.remove(1)%n");
        print(subList, list);
    }

    private static void print(List<Integer> subList, List<Integer> list) {
        System.out.println("subList:" + subList);
        System.out.println("List   : " + list);
        System.out.println();
    }
}
