package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class TakeWhileSample {
    public static void main(String[] args) {
        // Example 1: Taking elements while they are less than 5
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 0, 6, 7, 8, 9, 10);
        List<Integer> result = numbers.stream().takeWhile(n -> n < 5).toList();
        System.out.println("Example 1 (Take while less than 5): " + result); // Output: [1, 2, 3, 4]

        // Example 2: Empty stream
        result = IntStream.empty().boxed().takeWhile(n -> n < 5).toList();
        System.out.println("Example 2 (Empty stream): " + result); // Output: []

        // Example 3: All elements match the predicate
        numbers = Arrays.asList(1, 2, 3, 4);
        result = numbers.stream().takeWhile(n -> n < 5).toList();
        System.out.println("Example 3 (All elements match): " + result); // Output: [1, 2, 3, 4]

        // Example 4: No elements match the predicate
        numbers = Arrays.asList(5, 6, 7, 8);
        result = numbers.stream().takeWhile(n -> n < 5).toList();
        System.out.println("Example 4 (No elements match): " + result); // Output: []
    }
}