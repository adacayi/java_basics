package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.List;

public class ListIteratorSample {
    public static void main(String[] args) {
        var list = new ArrayList<>(List.of(1, 2, 5, 7));
        var listIterator = list.listIterator();
        var previous = list.get(0);

        while (listIterator.hasNext()) {
            var item = listIterator.next(); // ListIterator.next() returns the item in the cursor index and increments the cursor by one

            if(item - previous > 1) {
                listIterator.previous(); // ListIterator.previous() returns the item in cursor - 1 index decrements the cursor by one.
                // The cursor is now moved back to the item's index, hence we can insert an element just before it with the ListIterator.add method.

                for (int i = 1 ; i < item - previous; i++) {
                    listIterator.add(item - i); // This adds the element at the cursor position like list.add(item, cursor) and increments the cursor by one
                    listIterator.previous();
                }

                previous = previous + 1;
            } else {
                System.out.printf("%d ", item);
                previous = item;
            }
        }

        System.out.println();
        System.out.println(list);

        System.out.printf("%nMultiplying elements by 10%n");

        while (listIterator.hasPrevious()) {
            var item = listIterator.previous(); // ListIterator.previous() returns the item in cursor - 1 index decrements the cursor by one.
            listIterator.set(item * 10); // Sets the value of the item at the cursor index.
        }

        System.out.println(list);

        System.out.printf("%nPrinting indexes%n");
        while(listIterator.hasNext()){
            var index = listIterator.nextIndex(); // This returns the cursor value
            var item = listIterator.next();
            System.out.printf("%d: %d%n", index, item);
        }

        System.out.printf("%nPrinting indexes in reverse order%n");

        while(listIterator.hasPrevious()){
            var index = listIterator.previousIndex(); // This returns the cursor value - 1
            var item = listIterator.previous();
            System.out.printf("%d: %d%n", index, item);
        }
    }
}
