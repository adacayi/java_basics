package com.sanver.basics.collections;

import java.util.ArrayDeque;
import java.util.Deque;

public class DequeSample {
    public static void main(String... args) {
        int i;
        int n = 3;
        int[] numbers = {2, 3, 1, 5, 3, 5, 6, 3, 9};
        // ArrayDeque uses an array to store the elements where it starts adding elements to the start of the array for addLast and to the end of the end of the array for addFirst, so that removeFirst and removeLast does not cause shift to any elements in the array.
        // e.g. var deque = new ArrayDeque<Integer>(); deque.addFirst(1); deque.addFirst(2); deque.addLast(3); deque.addLast(4); will result in an array elements[3, 4, null, ..., 2, 1] of size 17, with head pointing to the 15th element and tail pointing to 2, and iteration cursor will start from head and increase until it reaches the length of the elements array and then go to 0 and increase again until it reaches the tail.
        // When removing an element from the last of the deque, it will set tail to tail - 1 and elements[tail] = null, and for removeFirst, it will set elements[head] = null and set head to head + 1, without any shifts needed.
        // Note: ArrayDeque does not support null elements. Use LinkedList instead if you want to store null elements in a Deque.
        // ArrayDeque also does not support adding or removing elements to a specified index, use LinkedList for this purpose.
        // ArrayDeque is not thread-safe as well unlike the legacy Stack and Vector classes. Use ConcurrentLinkedDeque instead for thread safety.
        Deque<Integer> deque = new ArrayDeque<>();

        System.out.printf("Size %d consecutive sub arrays: %n", n);

        for (i = 0; i < n; i++) {
            deque.add(numbers[i]); // This method is equivalent to addLast
        }

        System.out.println(deque);

        for (; i < numbers.length; i++) {
            deque.removeFirst(); // This method is equivalent to remove and pop
            deque.addLast(numbers[i]); // This method is equivalent to add
            System.out.println(deque);
        }

        deque.clear();
        System.out.println("Demonstrating the stack behavior: "); // Note: If you need methods that emphasize stack-like behavior (LIFO), then stick with push()/pop(). These are more intuitive for developers familiar with stack operations. If you want to emphasize the deque or queue-like nature of your code, then addLast()/removeFirst() or add/remove is clearer.
        deque.push(10); // This method is equivalent to addFirst
        deque.push(9);
        deque.push(8);
        System.out.println(deque.pop()); // This method is equivalent to remove and removeFirst
        System.out.println(deque.pop());
        System.out.println(deque.pop());
    }
}
