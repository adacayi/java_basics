package com.sanver.basics.threads.completable_future.chaining;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThenCompose {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    //    The thenCompose() method is similar to thenApply().
    //    However, thenCompose() will flatten and return a Future rather than a nested future in thenApply().
    //    So if the idea is to chain CompletableFuture methods then it’s better to use thenCompose().
    var completableFuture = CompletableFuture.supplyAsync(() -> {
      sleep(3000);
      return List.of(3, 5, 6);
    });
    var future = completableFuture.thenCompose(x -> CompletableFuture.supplyAsync(() -> String.format(
        "Size of the result is %d\n",
        x.size())));
    var result = future.get();
    System.out.printf("The get method's return value: %s\n", result);
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
