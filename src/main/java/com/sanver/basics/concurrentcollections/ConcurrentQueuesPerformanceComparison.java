package com.sanver.basics.concurrentcollections;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;
import static com.sanver.basics.utils.Utils.sleep;
import static com.sanver.basics.utils.Utils.warmup;

public class ConcurrentQueuesPerformanceComparison {

    private static final int COUNT = 30_000_000;
    private static final int PARALLELISM = Runtime.getRuntime().availableProcessors() * 2;
    private static final int ITEM_COUNT = PARALLELISM * (COUNT / PARALLELISM + 1);

    public static void main(String[] args) {
        warmup();
        concurrentLinkedQueue();
        concurrentLinkedDeque();
        linkedBlockingQueue();
        arrayBlockingQueue();
        priorityBlockingQueue();
        linkedTransferQueue();
    }

    private static void concurrentLinkedQueue() {
        var queue = new ConcurrentLinkedQueue<String>();
        testPerformance(queue, "ConcurrentLinkedQueue");
    }

    private static void concurrentLinkedDeque() {
        var queue = new ConcurrentLinkedDeque<String>();
        testPerformance(queue, "ConcurrentLinkedDeque");
    }

    private static void linkedBlockingQueue() {
        var queue = new LinkedBlockingQueue<String>(ITEM_COUNT);
        testPerformance(queue, "LinkedBlockingQueue");
    }

    private static void arrayBlockingQueue() {
        var queue = new ArrayBlockingQueue<String>(ITEM_COUNT);
        testPerformance(queue, "ArrayBlockingQueue");
    }

    private static void priorityBlockingQueue() {
        var queue = new PriorityBlockingQueue<String>(ITEM_COUNT);
        testPerformance(queue, "PriorityBlockingQueue");
    }

    private static void linkedTransferQueue() {
        var queue = new LinkedTransferQueue<String>();
        testPerformance(queue, "LinkedTransferQueue");
    }

    private static void testPerformance(Queue<String> queue, String type) {
        int count = COUNT;
        int parallelism = PARALLELISM;
        System.out.printf("%nChecking  write performance for %s. Adding   %,d items asynchronously.%n", type, count);
        sleep(2000);
        var block = count / parallelism + 1;
        measure(() -> IntStream.range(0, parallelism).parallel().forEach(i -> IntStream.range(0, block).forEach(j -> queue.add(String.valueOf(i * block + j)))));
        sleep(2000);
        System.out.printf("Checking remove performance for %s. Removing %,d items asynchronously.%n", type, count);
        var items = ConcurrentHashMap.<String>newKeySet();
        measure(() -> IntStream.range(0, parallelism).parallel().forEach(i -> IntStream.range(0, block).forEach(j -> {
            var removed = queue.remove();
            items.add(removed);
        })));

        IntStream.range(0, parallelism).parallel().forEach(i -> IntStream.range(0, block).forEach(j -> {
            var item = String.valueOf(i * block + j);
            if (!items.contains(item)) {
                throw new AssertionError("Queue must contain " + item);
            }
        }));
        sleep(1000);
    }
}
