package com.sanver.basics.collections;

import java.util.Deque;
import java.util.LinkedList;

public class DequeSampleWithLinkedList {
    public static void main(String... args) {
        int i, n = 3, numbers[] = {2, 3, 1, 5, 3, 5, 6, 3, 9};
        Deque<Integer> deque = new LinkedList<>();
        System.out.printf("%d elemented consecutive sub arrays: \n", n);

        for (i = 0; i < n; i++)
            deque.add(numbers[i]);

        System.out.println(deque);

        for (; i < numbers.length; i++) {
            deque.removeFirst();
            deque.add(numbers[i]);
            System.out.println(deque);
        }
    }
}
