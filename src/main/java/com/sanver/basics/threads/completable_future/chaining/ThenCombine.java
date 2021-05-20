package com.sanver.basics.threads.completable_future.chaining;

import static com.sanver.basics.utils.ThreadUtils.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ThenCombine {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var completableFuture1 = CompletableFuture.supplyAsync(() -> {
      sleep(3000);
      return List.of(3, 5, 7);
    });
    var completableFuture2 = CompletableFuture.supplyAsync(() -> new ArrayList<>(List.of(2, 4, 6)));
    var future = completableFuture1.thenCombine(completableFuture2,
        (r1, r2) -> {
          r2.addAll(r1);
          return r2;
        });
    var result = future.get();
    System.out.printf("The get method's return value: %s\n", result);
  }
}
