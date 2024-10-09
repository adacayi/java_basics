package com.sanver.basics.threads;

import java.text.NumberFormat;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class SynchronizedSample {

    static int value;

    public static void main(String[] args) {
        int limit = 1_000_000;
        int threadCount = 10;
        var lock = new Object();

        Runnable increment = () -> {
            for (int i = 0; i < limit; i++) {
                synchronized (lock) { // Remove synchronized to see the result might not be equal to limit * threads
                    value++;
                }
            }
        };

        var futures = IntStream.range(0, threadCount).mapToObj(x -> CompletableFuture.runAsync(increment)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        var format = NumberFormat.getInstance();
        System.out.println("Result: " + format.format(value));
    }
}
