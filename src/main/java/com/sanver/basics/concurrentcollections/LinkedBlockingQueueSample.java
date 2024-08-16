package com.sanver.basics.concurrentcollections;

import static com.sanver.basics.utils.LambdaExceptionUtil.rethrowRunnable;
import static com.sanver.basics.utils.Utils.sleep;

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

  static int value = 0;

  public static void main(String[] args) {
    testThreadSafety();
    testBlocking();
  }

  private static void testThreadSafety() {
    BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    var iteration = 200_000;
    var write1 = CompletableFuture.runAsync((rethrowRunnable(() -> {
      for(int i = 0; i < iteration; i++){
        queue.put(i);
      }
    })));

    var write2 = CompletableFuture.runAsync((rethrowRunnable(() -> {
      for(int i = 0; i < iteration; i++){
        queue.put(i);
      }
    })));

    write1.join();
    write2.join();
    System.out.println("Queue size: " + NumberFormat.getInstance().format(queue.size()));
  }

  private static void testBlocking() {
    BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    var read = CompletableFuture.runAsync(rethrowRunnable(() -> {
      int value;

      while (true) {
          value = queue.take();// Blocks until queue has an element.
          System.out.println(value + " removed");
      }
    }));

    var write = CompletableFuture.runAsync((rethrowRunnable(() -> {
      while (true) {
        sleep(1000);

        value++;
        queue.put(value);// Waits until queue is not full
        System.out.println(value + " added");
      }
    })));


    read.join();
    write.join();
  }
}
