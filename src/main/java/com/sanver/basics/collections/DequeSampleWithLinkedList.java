package com.sanver.basics.collections;

import java.util.Deque;
import java.util.LinkedList;

public class DequeSampleWithLinkedList {
    public static void main(String... args) {
        int i;
        int n = 3;
        int[] numbers = {2, 3, 1, 5, 3, 5, 6, 3, 9};
        Deque<Integer> deque = new LinkedList<>();

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
    }
}
