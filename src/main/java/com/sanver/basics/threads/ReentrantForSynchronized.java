package com.sanver.basics.threads;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * <h2>Reentrancy</h2>
 *
 * <p>The lock behind synchronized methods and blocks is reentrant. This means that
 * the current thread can acquire the same synchronized lock multiple times while already holding it.</p>
 * @see SynchronizedMethodSample
 * @see SynchronizedSample
 */
public class ReentrantForSynchronized {
    public static void main(String[] args) {
        var futures = IntStream.range(1, 4).mapToObj(x -> CompletableFuture.runAsync(() -> process(x))).toArray(CompletableFuture[]::new);
        // Notice that threads are different for each task, since each task started executing on a different thread but only one carried on executing the synchronized method while others are blocked.
        CompletableFuture.allOf(futures).join();
    }

    static synchronized void process(int task) {
        processPart(task, 1);
        synchronized (ReentrantForSynchronized.class) {
            processPart(task, 2);
            synchronized (ReentrantForSynchronized.class) {
                processPart(task, 3);
            }
        }
    }

    private static void processPart(int task, int part) {
        System.out.printf("Processing task %d part %d. %s%n",task, part, getThreadInfo());
        sleep(3_000);
    }
}
