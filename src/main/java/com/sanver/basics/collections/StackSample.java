package com.sanver.basics.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class StackSample {
    public static void main(String... args) {
        System.out.println("This code will show if the paranthesis in a given statement is proper");
        System.out.print("Enter statement : ");
        String line;

        try (Scanner scanner = new Scanner(System.in)) {
            do
                System.out.println(isClosedProperly(line = scanner.nextLine()));
            while (!line.isEmpty());
        }
    }

    public static boolean isClosedProperly(String s) {
        char k;
        char[] array = s.toCharArray();
        int i, n = array.length;
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> closures = new HashMap<>(3, 1);
        closures.put('(', ')');
        closures.put('[', ']');
        closures.put('{', '}');

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
