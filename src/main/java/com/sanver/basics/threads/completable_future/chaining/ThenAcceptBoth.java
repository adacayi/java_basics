package com.sanver.basics.threads.completable_future.chaining;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.sanver.basics.utils.Utils.sleep;

public class ThenAcceptBoth {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var completableFuture1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return new ArrayList<>(List.of(3, 5, 7));
        });

        var completableFuture2 = CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            return new ArrayList<>(List.of(2, 4, 6));
        });

        var future = completableFuture1.thenAcceptBoth(completableFuture2, // thenAcceptBoth waits for both completable futures to complete
                (r1, r2) -> {
                    r1.addAll(r2);
                    System.out.println(r1);
                });

        var result = future.get();
        System.out.printf("The get method's return value: %s%n", result);
    }
}
