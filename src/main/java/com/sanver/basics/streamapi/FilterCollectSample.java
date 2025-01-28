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
        System.out.println();

        var result = IntStream.range(0, 10)
                .filter(x -> {
                    System.out.printf("Filtering if %d is divisible by 3: %s%n", x, x % 3 == 0);
                    return x % 3 == 0;
                }).map(x -> {
                    System.out.printf("Mapping %d to %d%n", x, x / 3);
                    return x / 3;
                }).toArray();

        System.out.printf("%nResult after filtering numbers between 0 to 9 by checking if they are divisible by 3 and mapping the values to 1/3: %s%n", Arrays.toString(result));
    }
}
