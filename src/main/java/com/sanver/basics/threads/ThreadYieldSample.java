package com.sanver.basics.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;
import static com.sanver.basics.utils.Utils.sleepNano;

/**
 * <p>The {@code Thread.yield()} method is a static method of the {@link Thread} class
 * that hints to the thread scheduler that the current thread is willing to yield
 * its execution time.
 *
 * <p><b>Key Characteristics of {@code Thread.yield()}:</b>
 * <ul>
 *   <li><b>Voluntary Yield:</b>
 *   <p>The calling thread suggests giving up its CPU time voluntarily. Other threads
 *   of the same or higher priority may be scheduled to run.</p>
 *   </li>
 *   <li><b>Non-Guarantee:</b>
 *   <p>The {@code Thread.yield()} call is a hint to the thread scheduler. There is no
 *   guarantee that the thread scheduler will act on it. The thread might continue running.</p>
 *   </li>
 *   <li><b>Platform-Dependent Behavior:</b>
 *   <p>The actual behavior of {@code Thread.yield()} depends on the thread scheduler and
 *   the underlying operating system. On some platforms, {@code Thread.yield()} might have no effect at all.</p>
 *   </li>
 *   <li><b>Does Not Block:</b>
 *   <p>{@code Thread.yield()} does not cause the thread to enter a waiting or blocked state.
 *   The thread remains in the runnable state and is eligible to be scheduled again.</p>
 *   </li>
 * </ul>
 */
public class ThreadYieldSample {
    public static final int ITERATION_COUNT = 100_000_000;
    private static int task1Sum;
    private static int task2Sum;

    private static void doWork() {
        sleepNano(10);
    }

    public static void main(String[] args) {
        try (var pool = Executors.newFixedThreadPool(1)) {
            pool.execute(() -> {
                System.out.println("Task 1 started.  " + getThreadInfo());
                Thread.yield(); // Thread.yield() does not result in the other process to carry on.
                sleep(3000);
                System.out.println("Task 1 finished. " + getThreadInfo());
            });

            pool.execute(() -> {
                System.out.println("Task 2 started.  " + getThreadInfo());
                Thread.yield();
                sleep(3000);
                System.out.println("Task 2 finished. " + getThreadInfo());
            });
        }

        try (var pool = Executors.newFixedThreadPool(2)) {
            CompletableFuture.runAsync(() -> {
                for (int i = 0; i < ITERATION_COUNT; i++) {
                    doWork();
                    task1Sum++;
                }
            }, pool);

            CompletableFuture.runAsync(() -> {
                Thread.yield(); // From the output seems like this has no effect.
                for (int i = 0; i < ITERATION_COUNT; i++) {
                    doWork();
                    task2Sum++;
                }
            }, pool);
            sleep(2000);
            System.out.printf("Task 1 sum: %,d%n", task1Sum); // CompletableFuture is still running. This is to see how much progress has been made in the first task.
            System.out.printf("Task 2 sum: %,d%n", task2Sum); // CompletableFuture is still running. This is to see how much progress has been made in the second task.
        }
    }
}
