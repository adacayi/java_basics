package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WithCompletedFuture {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    //Returns a new CompletableFuture that is already completed with the given value.
    var completableFuture = CompletableFuture.completedFuture("initial value");
    System.out.println("completableFuture is completed: " + completableFuture.isDone());
    //Returns true if this invocation caused this CompletableFuture to transition to a completed state, else false
    var causedCompletion = completableFuture.complete("complete value");

    var result = completableFuture.get();
    System.out.println("Caused completion : " + causedCompletion);
    System.out.println("Result: " + result);

    System.out.println();

    var completableFuture2 = CompletableFuture.supplyAsync(() -> {
      sleep(1000);
      return "supplied value";
    });


    System.out.println("completableFuture2 is completed: " + completableFuture2.isDone());
    causedCompletion = completableFuture2.complete("complete value");
    result = completableFuture2.get();

    System.out.println("Caused completion : " + causedCompletion);
    System.out.println("Result: " + result);
  }
}
