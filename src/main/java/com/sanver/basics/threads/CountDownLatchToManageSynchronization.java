package com.sanver.basics.threads;

import org.apache.commons.lang3.time.StopWatch;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;

/**
 * This is just to show how concurrency works in general.
 * We shouldn't be using CountDownLatch for this purpose.
 * Run time can be checked and compared with synchronized block for example to see the inefficiency of using CountDownLatch for this purpose.
 */
public class CountDownLatchToManageSynchronization {
    private static final int OPERATION_COUNT = 10;
    private static final int INCREMENT_COUNT = 20_000;
    private static final int INCREMENT = 1;
    private static int sum = 0;

    public static void main(String[] args) {
        CountDownLatch[] latches = new CountDownLatch[OPERATION_COUNT];
        Arrays.parallelSetAll(latches, x -> new CountDownLatch(1)); // This is to manage pausing and resuming individual tasks.
        boolean[] finished = new boolean[OPERATION_COUNT]; // This is to check the completed status of each individual task.
        var numberFormat = NumberFormat.getInstance();

        IntConsumer increment = x -> {
            for (int i = 0; i < INCREMENT_COUNT; i++) {
                var latch = latches[x]; // Get the task specific latch
                uncheck(() -> latch.await()); // Wait for it to be opened.
                sum += INCREMENT; // Increment the value.
                var newLatch = new CountDownLatch(1);
                latches[x] = newLatch;

                synchronized (latch) {
                    latch.notify(); // Notify the manageLatches that the new latch has been set.
                }
            }
            finished[x] = true;
        };

        var stopWatch = new StopWatch();
        stopWatch.start();
        IntStream.range(0, OPERATION_COUNT).forEach(x -> CompletableFuture.runAsync(() -> increment.accept(x)));
        manageLatches(latches, finished);// We won't need any joins, since manageLatches will carry on until all tasks are finished (finished list contains no true item).
        stopWatch.stop();
        System.out.printf("Operations with CountDownLatch completed in    : %s%06d%n", stopWatch, stopWatch.getNanoTime() % 1_000_000);
        System.out.println("Sum = " + numberFormat.format(sum));

        stopWatch.reset();
        stopWatch.start();
        var lock = new Object();
        sum = 0;

        Runnable incrementWithSynchronizedBlock = () -> {
            for (int i = 0; i < INCREMENT_COUNT; i++) {
                synchronized (lock) {
                    sum += INCREMENT;
                }
            }
        };
        var futures = IntStream.range(0, OPERATION_COUNT).mapToObj(x -> CompletableFuture.runAsync(incrementWithSynchronizedBlock)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        stopWatch.stop();
        System.out.printf("Operations with synchronized block completed in: %s%06d%n", stopWatch, stopWatch.getNanoTime() % 1_000_000);
        System.out.println("Sum = " + numberFormat.format(sum));
    }

    private static void manageLatches(CountDownLatch[] latches, boolean[] finished) {
        while (!isFinished(finished)) {
            for (int i = 0; i < latches.length; i++) {
                if (finished[i]) {
                    continue;
                }

                var latch = latches[i];

                synchronized (latch) {
                    latch.countDown(); // This is inside the synchronized block to make sure that notify in the increment is not called before the wait in this block.
                    uncheck(() -> latch.wait());
                }
            }
        }
    }

    private static boolean isFinished(boolean[] finished) {
        for (boolean b : finished) {
            if (!b) {
                return false;
            }
        }

        return true;
    }
}
