package com.sanver.basics.collections;

import java.util.Arrays;
import java.util.LinkedList;

public class DequeSampleWithLinkedList {
    public static void main(String... args) {
        int i;
        int n = 3;
        int[] numbers = {2, 3, 1, 5, 3, 5, 6, 3, 9};
        var deque = new LinkedList<Integer>(); // Unlike ArrayDeque, LinkedList supports null elements and access/add/remove to any element directly, instead of just the first and last elements.
        // LinkedList is not thread-safe like ArrayDeque. We can use Collections.synchronizedList() to convert it to a synchronized list, but we lose access to methods specific to Deque interface like addFirst.

        System.out.printf("Size %d consecutive sub arrays: %n", n);

        for (i = 0; i < n; i++)
            deque.add(numbers[i]);

        System.out.println(deque);

        for (; i < numbers.length; i++) {
            deque.removeFirst(); // This method is equivalent to remove and pop
            deque.addLast(numbers[i]); // This method is equivalent to add
            System.out.println(deque);
        }

        deque.clear();
        System.out.println("Demonstrating the stack behavior: "); // Note: If you need methods that emphasize stack-like behavior (LIFO), then stick with push()/pop(). These are more intuitive for developers familiar with stack operations. If you want to emphasize the deque or queue-like nature of your code, then addFirst()/removeFirst() is clearer.
        deque.push(10); // This method is equivalent to addFirst
        deque.push(9);
        deque.push(8);
        System.out.println(deque.pop()); // This method is equivalent to removeFirst
        System.out.println(deque.pop());
        System.out.println(deque.pop());

        // Deque can contain null elements, support for access/add/remove at any location.
        System.out.println();
        deque = new LinkedList<>(Arrays.asList(4, 0, null, 7, 1));
        System.out.println(deque);
        deque.set(1, 3);
        deque.add(3, 2);
        System.out.println(deque);
        deque.remove(4);
        System.out.println(deque);
        System.out.println(deque.get(1));
    }
}
