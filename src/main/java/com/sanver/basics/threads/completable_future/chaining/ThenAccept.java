package com.sanver.basics.threads.completable_future.chaining;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThenAccept {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var completableFuture = CompletableFuture.supplyAsync(() -> {
      sleep(3000);
      return List.of(3, 5, 6);
    });
    var future = completableFuture.thenAccept(x -> System.out.printf("Size of the result is %d\n", x.size()));
    var result = future.get();
    System.out.printf("The get method's return value is: %s", result);
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
