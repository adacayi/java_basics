package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static com.sanver.basics.utils.Utils.sleep;

public class HandleExceptionsWithMultipleFuturesAndExecutor {

    private static AtomicInteger count = new AtomicInteger(0);
    private static AtomicInteger idCount = new AtomicInteger(0);

    public static void main(String[] args) {
        Supplier<String> supplier = () -> {
            var id = idCount.addAndGet(1);
            System.out.printf("%d: started. Thread: %d%n", id, Thread.currentThread().getId());
            sleep(2000);
            if (id == 2 || id == 3) {
                throw new RuntimeException(String.format("Some error occurred in thread %d", Thread.currentThread().getId()));
            }
            System.out.printf("%d: completed. Thread: %d%n", id, Thread.currentThread().getId());
            return "completed";
        };

        var executor = Executors.newFixedThreadPool(2);
        var future1 = CompletableFuture.supplyAsync(supplier, executor);
        var future2 = CompletableFuture.supplyAsync(supplier, executor);
        var future3 = CompletableFuture.supplyAsync(supplier, executor);
        var future4 = CompletableFuture.supplyAsync(supplier, executor);
        var future5 = CompletableFuture.supplyAsync(supplier, executor);

        var combined = CompletableFuture.allOf(future1, future2, future3, future4, future5);

        var handled = combined.handle(
                (r, e) -> {
                    if (e == null) {
                        System.out.println("No error occurred. Return value is " + r);
                        count.addAndGet(1);
                        return -1;
                    }
                    System.out.println("Error occurred: " + e);
                    return count.addAndGet(1);
                }).thenAccept(x -> System.out.println("The return value is " + x));

        handled.join();
    }
}
