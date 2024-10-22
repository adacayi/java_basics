package com.sanver.basics.concurrentcollections;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.IntStream;

/**
 * ConcurrentLikedDeque is a lock-free implementation of Deque and uses and efficient lock-free algorithm.
 */
public class ConcurrentLinkedDequeSample {
    public static void main(String[] args) {
        var deque = new ConcurrentLinkedDeque<Integer>(); // Replace this with ArrayDeque or LinkedList to see unexpected behavior like ArrayIndexOutOfBoundsException or size less than the expected size.
        var threadCount = 10;
        var elementCount = 1000;

        Runnable add = () -> IntStream.range(0, elementCount).forEach(deque::add);
        var futures = IntStream.range(0, threadCount).mapToObj(i -> CompletableFuture.runAsync(add)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.printf("Deque size: %,d", deque.size());
    }
}
