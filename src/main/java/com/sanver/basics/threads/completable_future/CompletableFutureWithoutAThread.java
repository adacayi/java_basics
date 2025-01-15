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
                System.out.printf("Execution  started. %8s%s%n", "", getThreadInfo());
                sleep(7_000);
                System.out.printf("Execution finished. %8s%s%n", "", getThreadInfo());
                sleep(3_000);
                future.complete("result");
                System.out.println("future.complete() executed. " + getThreadInfo());
            });
            System.out.printf("Execution  invoked. %8s%s%n", "", getThreadInfo());
            var result = future.join(); // This will be blocked until future.complete is executed.
            sleep(10);// This is just to make sure the print after future.complete is printed first, to better demonstrate the code is blocked in main because of future.join() until future.complete() is executed.
            System.out.printf("Result: %-19s %s%n", result, getThreadInfo());
        }
    }
}
