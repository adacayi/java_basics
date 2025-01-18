package com.sanver.basics.concurrentcollections;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * Demonstrates the usage of various concurrent queue implementations in Java.
 * These queues are part of the {@code java.util.concurrent} package and are designed
 * for high-performance, thread-safe operations without requiring explicit synchronization.
 *
 * <p>Java provides several concurrent queue implementations, each with different properties:</p>
 *
 * <ul>
 *     <li>{@link java.util.concurrent.ConcurrentLinkedQueue} - A non-blocking, thread-safe queue based on a linked node structure. It follows FIFO (First-In-First-Out) ordering.</li>
 *     <li>{@link java.util.concurrent.ConcurrentLinkedDeque} - A highly concurrent, thread-safe deque (double-ended queue) allowing elements to be added or removed from both ends. It provides non-blocking methods for most operations but may use internal locking for certain bulk operations.</li>
 *     <li>{@link java.util.concurrent.LinkedBlockingQueue} - A blocking queue based on linked nodes, with an optional user-specified capacity. If no capacity is provided, it defaults to Integer.MAX_VALUE, making it practically unbounded but still technically limited. Threads block when retrieving from an empty queue or inserting into a full one.</li>
 *     <li>{@link java.util.concurrent.ArrayBlockingQueue} - A bounded blocking queue backed by an array. Unlike {@code LinkedBlockingQueue}, which uses separate locks for put and take operations, {@code ArrayBlockingQueue} uses a single lock, which may lead to higher contention but can improve cache locality.</li>
 *     <li>{@link java.util.concurrent.PriorityBlockingQueue} - A blocking queue that orders elements according to their natural ordering or a specified comparator, similar to {@code PriorityQueue} but thread-safe.</li>
 *     <li>{@link java.util.concurrent.DelayQueue} - A blocking queue where elements can only be taken when their delay has expired. Used for scheduling tasks.</li>
 *     <li>{@link java.util.concurrent.SynchronousQueue} - A queue with no internal capacity, meaning each insertion must wait for a corresponding removal. In fairness mode, threads are scheduled in FIFO order, but the queue’s capacity remains zero at all times.</li>
 *     <li>{@link java.util.concurrent.LinkedTransferQueue} - An optimized, highly scalable queue that supports {@code transfer(E e)}, allowing producers to hand off elements directly to waiting consumers. This can improve efficiency in scenarios requiring guaranteed handoff and is also effective in general producer-consumer designs.</li>
 * </ul>
 *
 * <p>Each of these queues has its own characteristics regarding blocking behavior, ordering, and performance.
 * Choosing the right queue depends on the specific requirements of a concurrent application.</p>
 */
public class ConcurrentQueues {
    public static void main(String[] args) {
        concurrentLinkedQueue();
        concurrentLinkedDeque();
        linkedBlockingQueue();
        arrayBlockingQueue();
        priorityBlockingQueue();
        delayQueue();
        synchronousQueue();
        linkedTransferQueue();
        // There are no java.util methods for immutable Queue.
        // You can use Collections.unmodifiableCollection(), but it returns a Collection, not a Queue.
        // Also, there are no methods in Collections to make a queue synchronized and also returns a queue.
        // You can use Collections.synchronizedCollection, but it returns a Collection.
    }

    private static void concurrentLinkedQueue() {
        var queue = new ConcurrentLinkedQueue<String>();
        addValues(queue);
        System.out.printf("%nConcurrentLinkedQueue: %s%n", queue);
        testConcurrency(queue, "ConcurrentLinkedQueue");
    }

    private static void concurrentLinkedDeque() {
        var queue = new ConcurrentLinkedDeque<String>();
        addValues(queue);
        System.out.printf("%nConcurrentLinkedDeque: %s%n", queue);
        testConcurrency(queue, "ConcurrentLinkedDeque");
    }

    private static void linkedBlockingQueue() {
        var queue = new LinkedBlockingQueue<String>();
        addValues(queue);
        System.out.printf("%nLinkedBlockingQueue: %s%n", queue);
        testConcurrency(queue, "LinkedBlockingQueue");
    }

    private static void arrayBlockingQueue() {
        var queue = new ArrayBlockingQueue<String>(1000);
        addValues(queue);
        System.out.printf("%nArrayBlockingQueue (This is a bounded queue. Capacity is determined when initialized. capacity: %,d): %s%n", queue.remainingCapacity() + queue.size(), queue);
        testConcurrency(queue, "ArrayBlockingQueue");
    }

    private static void priorityBlockingQueue() {
        var queue = new PriorityBlockingQueue<String>();
        addValues(queue);
        System.out.printf("%nPriorityBlockingQueue (Orders elements according to their natural order or by a specified comparator) : %s%n", queue);
        testConcurrency(queue, "PriorityBlockingQueue");
    }

    private static void delayQueue() {
        System.out.printf("%nSince DelayQueue requires its elements to implement Delayed interface, we skip this one for simplicity.%nFeel free to check implementation%n");
        sleep(3000);
    }

    private static void synchronousQueue() {
        var queue = new SynchronousQueue<String>();
        System.out.printf("%nValues will be added to SynchronousQueue but each insertion must wait for a corresponding removal%n");
        CompletableFuture.runAsync(() -> uncheck(()->{
            queue.put("c"); // We need to use put and take for blocking to work
            queue.put("B");
            queue.put("a");
            queue.put("A");
            printInsertOrder();
        }));
        sleep(2000);
        System.out.println("Add operation should be blocked, since there is no removal yet. Removal will start now.");
        IntStream.range(0, 4).forEach(i -> {
            sleep(3000);
            System.out.printf("%s removed%n", uncheck(queue::take));
        });
        System.out.println("Remove finished");
        sleep(3000);
        System.out.printf("%nSynchronousQueue (each insertion must wait for a corresponding removal) : %s%n", queue);
    }

    private static void linkedTransferQueue() {
        var queue = new LinkedTransferQueue<String>();
        addValues(queue);
        System.out.printf("%nLinkedTransferQueue: %s%n", queue);
        testConcurrency(queue, "LinkedTransferQueue");
    }

    private static void addValues(Queue<String> queue) {
        queue.add("c");
        queue.add("B");
        queue.add("a");
        queue.add("A");
        printInsertOrder();
    }

    private static void printInsertOrder() {
        System.out.printf("""
                %nInsert order:
                
                queue.add("c");
                queue.add("B");
                queue.add("a");
                queue.add("A");
                """);
    }

    private static void testConcurrency(Queue<String> queue, String type) {
        queue.clear();
        sleep(7000);
        System.out.printf("%nTesting concurrency for %s%n", type);
        sleep(3000);
        System.out.println("Putting 1,000 values asynchronously");
        var count = 1000;
        IntStream.range(0, count).parallel().forEach(i -> queue.add(String.valueOf(i)));
        sleep(2000);
        System.out.printf("Final size: %,d%n", queue.size());
        IntStream.range(0, count).forEach(x -> {
            if (!queue.contains(String.valueOf(x))) {
                throw new AssertionError("Queue should contain " + x);
            }
        });
        sleep(3000);
    }
}
