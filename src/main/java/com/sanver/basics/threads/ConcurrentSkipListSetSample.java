package com.sanver.basics.threads;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;

public class ConcurrentSkipListSetSample {
    private static final Set<Integer> values = new ConcurrentSkipListSet<>(); // Change this to HashSet to see the final set size being indeterminate because HashSet is not thread-safe and the elements of the set are not ordered according to string comparison but by hashcode and the capacity of the HashSet

    public static void main(String[] args) {
        var latch = new CountDownLatch(1);
        IntConsumer populate = i -> {
            uncheck(() -> latch.await());
            IntStream.range(0, 100).forEach(j -> values.add(100 * i + j));
        };

        var futures = IntStream.range(0, 10).mapToObj(i -> CompletableFuture.runAsync(() -> populate.accept(i))).toArray(CompletableFuture[]::new);
        latch.countDown();
        CompletableFuture.allOf(futures).join();
        System.out.printf("Size: %,d%n", values.size());
        System.out.println(values);
    }
}
