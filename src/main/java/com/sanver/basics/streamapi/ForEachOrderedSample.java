package com.sanver.basics.streamapi;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

public class ForEachOrderedSample {
    public static void main(String[] args) {
        IntConsumer print = i -> {
            sleep(1000);
            System.out.printf("%d Thread id: %d%n", i, Thread.currentThread().getId());
        };
        IntStream.range(0, 10).parallel().forEach(print);
        System.out.println();
        IntStream.range(0, 10).parallel().forEachOrdered(print);
    }
}
