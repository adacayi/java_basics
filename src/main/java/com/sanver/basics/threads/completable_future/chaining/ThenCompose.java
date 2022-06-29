package com.sanver.basics.threads.completable_future.chaining;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThenCompose {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    //    The thenCompose() method is used for combining completable futures.
    //    If thenApply is used instead, we end up
    //    with nested CompletableFuture<CompletableFuture<>> instances
    var completableFuture = CompletableFuture.supplyAsync(() -> {
      sleep(3000);
      return List.of(3, 5, 6);
    });
    var future = completableFuture.thenCompose(x -> CompletableFuture.supplyAsync(() -> String.format(
        "Size of the result is %d\n",
        x.size()))); // If we used thenApply here instead of thenCompose, the returned value will be
    // CompletableFuture<CompletableFuture<String>>
    var result = future.get();
    System.out.printf("The get method's return value: %s\n", result);
  }
}
