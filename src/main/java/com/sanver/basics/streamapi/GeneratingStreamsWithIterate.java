package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;
import static com.sanver.basics.utils.Utils.sleep;

public class GeneratingStreamsWithIterate {

    public static void main(String[] args) {
        IntUnaryOperator generator = x -> {
            System.out.println("Iterating " + x);
            sleep(1000);
            return x + 1;
        };
        measure(() -> {
            var result = IntStream.iterate(1, generator).limit(5).toArray();
            System.out.printf("%s%n%n", Arrays.toString(result));
        });
        System.out.printf("%nNote that the seed is the first element and 4 other values are generated through the iterate operator. The time elapsed shows that as well. It took around 4 seconds.%n%n");

        sleep(7_000);
        measure(() -> IntStream.iterate(1, generator).skip(4).limit(5).forEach(System.out::println));
        System.out.printf("%nNote that seed is skipped and for the other 3 skipped values iterate operator is still executed, resulting in the execution time of around (1 skipped seed (0 second) + 3 skipped + 5 taken = 8) 8 seconds.%n");

        sleep(7_000);
        System.out.printf("%nBelow is the same iterate method with limit and skip order changed to show the order affects the result.%n%n");
        measure(() -> IntStream.iterate(1, generator).limit(5).skip(4).forEach(System.out::println)); // This is to show that skip and limit order changes the result
    }
}
