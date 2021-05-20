package com.sanver.basics.threads.completable_future.chaining;

import static com.sanver.basics.utils.ThreadUtils.sleep;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThenApply {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var completableFuture = CompletableFuture.supplyAsync(() -> {
      sleep(3000);
      return List.of(3, 5, 6);
    });
    var future = completableFuture.thenApply(x -> String.format("Size of the result is %d\n", x.size()));
    var result = future.get();
    System.out.printf("The get method's return value: %s\n", result);
  }
}
