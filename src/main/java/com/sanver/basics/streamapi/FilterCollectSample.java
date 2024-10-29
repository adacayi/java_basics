package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.stream.IntStream;

public class FilterCollectSample {

    public static void main(String[] args) {
        int length = 20;
        var numbers = IntStream.rangeClosed(1, length).toArray();
        int[] even;
        int[] odd;

        even = Arrays.stream(numbers).filter(x -> x % 2 == 0).toArray();
        odd = Arrays.stream(numbers).filter(x -> x % 2 == 1).toArray();
        System.out.println("Even numbers: " + Arrays.toString(even));
        System.out.println("Odd  numbers: " + Arrays.toString(odd));
    }
}
