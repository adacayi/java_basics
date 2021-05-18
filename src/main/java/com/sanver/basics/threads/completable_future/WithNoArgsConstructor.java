package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WithNoArgsConstructor {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var completableFuture = new CompletableFuture<String>();
    //If not already completed, sets the value returned by get() and related methods to the given value.
    //Returns true if this invocation caused this CompletableFuture to transition to a completed state, else false
    var state = completableFuture.complete("return value");

    var result = completableFuture.get();
    System.out.println(state);
    System.out.println(result);
  }
}
