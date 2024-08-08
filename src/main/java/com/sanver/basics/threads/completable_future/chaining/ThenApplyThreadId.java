package com.sanver.basics.threads.completable_future.chaining;

import java.util.concurrent.CompletableFuture;

import static com.sanver.basics.utils.Utils.sleep;

public class ThenApplyThreadId {
    public static void main(String[] args) {
        System.out.println("Main thread started. Thread id: " + Thread.currentThread().getId());
        var future = CompletableFuture.supplyAsync(() -> {
            System.out.println("SupplyAsync started. Thread id: " + Thread.currentThread().getId());
            sleep(1000);
            return "Hello";
        }).thenApply(s -> {
            System.out.println("ThenApply started. Thread id: " + Thread.currentThread().getId()); // thenApply runs the supplied function in the same thread where the previous stage (the CompletableFuture) was completed. This means that if the previous stage was completed in the main thread, the function will run on the main thread; if it was completed in a thread from a ForkJoinPool, the function will run on that thread.
            sleep(2000);
            System.out.println("ThenApply finished. Thread id: " + Thread.currentThread().getId());
            return s + " World";
        });
        sleep(100);
        System.out.println("Main thread finished. Thread id: " + Thread.currentThread().getId());
        future.join();
    }
}
