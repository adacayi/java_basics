package com.sanver.basics.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class AtomicIntegerSample {
    public static final AtomicInteger sum = new AtomicInteger(0); // Change this to int and increment sum by 3 inside the for loop to see the result might not be equal to 3,000,000.

    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i = 0; i < 100_000; i++) {
                sum.incrementAndGet(); // Increment by 1
                sum.accumulateAndGet(3, (prev, x) -> prev + 2 * x); // Increment by 6. The first operand in the binary operator (prev) is the AtomicInteger, and the second one (x) is the first parameter of the accumulateAndGet method (i.e. 3 in this example). Thus, this means incrementing the value by 6.
            }
        };

        var futures = IntStream.range(0, 10).mapToObj(i -> CompletableFuture.runAsync(runnable)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.printf("Sum = %,d%n", sum.get());
    }
}
