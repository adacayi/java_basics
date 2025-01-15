package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

public class HandleExceptionsWithMultipleFutures {
    private static final AtomicInteger count = new AtomicInteger(0);
    private static final AtomicInteger idCount = new AtomicInteger(0);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Supplier<String> supplier = () -> {
            var id = idCount.addAndGet(1);
            System.out.printf("%d: started. %s%n", id, getThreadInfo());
            sleep(7000);
            if (id == 2 || id == 3) {
                System.out.printf("%d: Exception will be thrown in thread %s%n", id, getThreadInfo());
                throw new IllegalArgumentException("%d: Some error occurred in thread %s".formatted(id, getThreadInfo()));
            }
            System.out.printf("%d: completed. Thread: %s%n", id, getThreadInfo());
            return "%d: completed".formatted(id);
        };

        var future1 = CompletableFuture.supplyAsync(supplier);
        var future2 = CompletableFuture.supplyAsync(supplier);
        var future3 = CompletableFuture.supplyAsync(supplier);
        sleep(5000);
        var future4 = CompletableFuture.supplyAsync(supplier);
        var future5 = CompletableFuture.supplyAsync(supplier);

        CompletableFuture<Void> combined = CompletableFuture.allOf(future1, future2, future3, future4, future5); // This combined completable future would return null when combined.get() is called, if all the threads were executed without an exception.

        // Note that handle is executed only once and r parameter will be null and e parameter will be either null
        // (if there is no exception in any of the futures) or will be the first exception thrown from one of the futures.
        // All futures are executed regardless of some of them throwing exceptions.
        // handle and thenAccept are executed after all completable futures are executed (regardless of them throwing exceptions).

        CompletableFuture<Void> finalCompletable = combined.handle(
                (r, e) -> {
                    System.out.println("Handle started. " + getThreadInfo());
                    sleep(5000);
                    if (e == null) {
                        System.out.println("No error occurred. Return value is " + r);
                        count.addAndGet(1);
                        return -1;
                    }
                    System.out.println("Error occurred: " + e);
                    return count.addAndGet(1);
                }).thenAccept(x -> {
            System.out.println("ThenAccept started. " + getThreadInfo());
            sleep(5000);
            System.out.printf("ThenAccept finished. The return value is %d. %s", x, getThreadInfo());
        });

        finalCompletable.get(); // No exception will be thrown, since it is handled.

        // If combined.get() is called instead of finalCompletable.get(), it throws ExecutionException only wrapping the first exception.
        // Interestingly, combined.get() will also wait for handle and thenAccept to finish.
    }
}
