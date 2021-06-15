package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WithCompletedFuture {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    //Returns a new CompletableFuture that is already completed with the given value.
    var completableFuture = CompletableFuture.completedFuture("return value");
    //Returns true if this invocation caused this CompletableFuture to transition to a completed state, else false
    var state = completableFuture.complete("something else");

    var result = completableFuture.get();
    System.out.println(state);
    System.out.println(result);

    var completableFuture2 = CompletableFuture.supplyAsync(() -> {
      sleep(1000);
      return "supplied value";
    });

    state = completableFuture2.complete("another value");
    result = completableFuture2.get();

    System.out.println(state);
    System.out.println(result);
  }
}
