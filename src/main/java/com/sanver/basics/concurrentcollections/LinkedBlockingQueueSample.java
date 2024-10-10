package com.sanver.basics.concurrentcollections;

import java.text.NumberFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * LinkedBlockingQueue and ConcurrentLinkedQueue are the two most frequently used concurrent queues in Java.
 * The LinkedBlockingQueue class implements the BlockingQueue interface, which provides the blocking nature to it.
 * <br>
 * A blocking queue indicates that the queue blocks the accessing thread if it is full (when the queue is bounded) or becomes empty.
 * If the queue is full, then adding a new element will block the accessing thread unless there is space available for the new element.
 * Similarly, if the queue is empty, then accessing an element blocks the calling thread:
 */
public class LinkedBlockingQueueSample {

    public static void main(String[] args) {
        testThreadSafety();
        testBlocking();
    }

    private static void testThreadSafety() {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        var iteration = 200_000;
        var write1 = CompletableFuture.runAsync((() -> {
            for (int i = 0; i < iteration; i++) {
                queue.add(i); // put and offer are also thread safe
            }
        }));

        var write2 = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < iteration; i++) {
                queue.add(i); // put and offer are also thread safe
            }
        });

        write1.join();
        write2.join();
        System.out.println("Queue size: " + NumberFormat.getInstance().format(queue.size()));
    }

    private static void testBlocking() {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);
        final int limit = 100;

        var read = CompletableFuture.runAsync(() -> {
            int value = 0;
            while (value < limit - 1) {
                try {
                    value = queue.take();
                    System.out.printf("%d removed. Queue size: %d%n", value, queue.size());// Blocks until queue has an element. Poll and remove methods won't block. If there are no elements, poll will return null and remove will throw a NoSuchElementException.
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        var write = CompletableFuture.runAsync((() -> {
            for(int value = 0; value < limit; value++){
                try {
                    queue.put(value);// Waits until queue is not full. add and offer methods won't block. If the queue is full, add method will throw an IllegalStateException and offer will return false and do not add the item to the queue.
                    System.out.printf("%d added. Queue size = %d%n", value, queue.size());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }));

        read.join();
        write.join();
    }
}
