package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * This example is to demonstrate a {@code CompletableFuture} without a thread, thus explaining why {@code cancel()} method does not
 * interrupt the underlying thread, because there might not be one in the first place.
 */
public class CompletableFutureWithoutAThread {
    public static void main(String[] args) {
        CompletableFuture<String> future = new CompletableFuture<>();
        try (var executor = Executors.newFixedThreadPool(1)) {
            executor.execute(() -> {
                System.out.println("Execution  started. " + getThreadInfo());
                sleep(7_000);
                System.out.println("Execution finished. " + getThreadInfo());
                sleep(3_000);
                future.complete("Completion value");
            });
            System.out.println("Execution  invoked. " +  getThreadInfo());
            var result = future.join();
            System.out.println("Completion value: " + result);
        }
    }
}
