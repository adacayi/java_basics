package com.sanver.basics.collections;

import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * The {@code Stack} class in Java is synchronized because it extends {@code Vector},
 * which provides synchronized methods. However, using {@code Stack} for thread-safe
 * operations is generally discouraged due to the following reasons:
 *
 * <ul>
 *   <li>Synchronization is applied at the method level, which can cause performance
 *       bottlenecks in multi-threaded environments.</li>
 *   <li>More efficient alternatives exist, such as:</li>
 *   <ul>
 *     <li>{@code Deque} (e.g., {@code ArrayDeque}) {@code LinkedList} for a non-thread-safe stack implementation.</li>
 *     <li>{@code ConcurrentLinkedDeque} or {@code ArrayBlockingQueue} {@code LinkedBlockingQueue} for thread-safe stack alternatives.</li>
 *   </ul>
 * </ul>
 *
 */
public class StackSample {
    public static void main(String... args) {
        System.out.println("This code will show if the paranthesis in a given statement is proper");
        System.out.print("Enter statement : ");
        String line;

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                line = scanner.nextLine();
                System.out.println(isClosedProperly(line));
            }
            while (!line.isEmpty());
        }
    }

    public static boolean isClosedProperly(String s) {
        char k;
        char[] array = s.toCharArray();
        int i;
        int n = array.length;
        var stack = new Stack<Character>();
        var closures = Map.of('(', ')', '[', ']', '{', '}');

        for (i = 0; i < n; i++) {
            k = array[i];

            if (closures.containsKey(k))
                stack.push(k);
            else if (closures.containsValue(k) && (stack.empty() || k != closures.get(stack.pop())))
                return false;
        }

        return stack.empty();
    }
}
