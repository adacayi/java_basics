package com.sanver.basics.threads;

import org.apache.commons.lang3.time.StopWatch;

import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
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
        List<CountDownLatch> latches = IntStream.range(0, OPERATION_COUNT).mapToObj(x -> new CountDownLatch(1)).collect(Collectors.toList()); // This is to manage pausing and resuming individual tasks.
        List<Boolean> finished = IntStream.range(0, OPERATION_COUNT).mapToObj(x -> false).collect(Collectors.toList()); // This is to check the completed status of each individual task. We cannot just check the individual future, since when we checked it might not have been completed yet, but the loop might have finished, which results in a wait in the manageLatches method, which won't be notified.

        IntConsumer increment = x -> {
            for (int i = 0; i < INCREMENT_COUNT; i++) {
                var latch = latches.get(x); // Get the task specific latch
                uncheck(() -> latch.await()); // Wait for it to be opened.
                sum += INCREMENT; // Increment the value.

                synchronized (latch) {
                    var newLatch = new CountDownLatch(1);
                    latches.set(x, newLatch);
                    latch.notifyAll(); // Notify manageLatches so that it opens the next latch.
                    if (i == INCREMENT_COUNT - 1) {
                        finished.set(x, true); // This needs to be in the synchronized block, otherwise manageLatches might call latch.wait before this.
                    }
                }
            }
        };

        var stopWatch = new StopWatch();
        stopWatch.start();
        IntStream.range(0, OPERATION_COUNT).forEach(x -> CompletableFuture.runAsync(() -> increment.accept(x)));
        manageLatches(latches, finished);// We won't need any joins, since manageLatches will carry on until all tasks are finished (finished list contains no true item).
        stopWatch.stop();
        System.out.printf("Operations with CountDownLatch completed in    : %s%06d%n", stopWatch, stopWatch.getNanoTime() % 1_000_000);
        System.out.println("Sum = " + NumberFormat.getInstance().format(sum));

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
        System.out.println("Sum = " + NumberFormat.getInstance().format(sum));
    }

    private static void manageLatches(List<CountDownLatch> latches, List<Boolean> finished) {
        while (finished.stream().anyMatch(x -> !x)) {
            for (int i = 0; i < latches.size(); i++) {
                var latch = latches.get(i);
                synchronized (latch) {
                    latch.countDown(); // This is inside the synchronized block to make sure that notifyAll in the increment is not called before the wait in this block.
                    if (finished.get(i) == false) { // This is to make sure that we don't wait for a latch that is already finished processing.
                        uncheck(() -> latch.wait());
                    }
                }
            }
        }
    }
}
