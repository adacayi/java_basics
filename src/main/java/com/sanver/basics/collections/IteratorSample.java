package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.List;

public class IteratorSample {
    public static void main(String[] args) {
        var list = new ArrayList<>(List.of(1, 2, 3, 5, 6, 7));
        var iterator = list.iterator();

        while (iterator.hasNext()) {
            System.out.printf("%d ", iterator.next());
        }
        System.out.println();

        iterator = list.iterator(); // If we did not renew this, then the following code below won't print any items.

        iterator.forEachRemaining(x -> System.out.printf("%d ", x));
        System.out.println();

        iterator = list.iterator();

        while (iterator.hasNext()) {
            var item = iterator.next();

            if (item % 2 == 0) {
                iterator.remove(); // Unlike Enumeration, Iterator allows for element removal from a collection.
                // Removes the element in the lastRet (index of last element returned) and sets the cursor to lastRet.
            } else {
                System.out.printf("%d ", item);
            }
        }
        System.out.println();

        iterator = list.iterator();

        while (iterator.hasNext()) {
            list.add(3);
            System.out.printf("%d ", iterator.next()); // iterator.next() will throw a ConcurrentModificationException,
            // since the list is modified after the iterator has been created.
            // This is achieved by modCount field in the list and the expectedModCount field in the iterator.
            // When an iterator is generated, the expectedModCount is set to modCount and when the list is modified,
            // modCount is incremented. When iterator.next() is called, expectedModCount and modCount are compared,
            // if they are not equal, then the ConcurrentModificationException is thrown.
        }
    }
}
