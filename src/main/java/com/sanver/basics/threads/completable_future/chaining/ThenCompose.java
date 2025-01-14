package com.sanver.basics.threads.completable_future.chaining;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class ThenCompose {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    //    The thenCompose() method is used for combining completable futures.
    //    If thenApply is used with CompletableFutures, we will end up with nested CompletableFuture instances (CompletedFuture<CompletableFuture<>>)
    Function<List<Integer>, CompletableFuture<String>> futureFunction= x -> CompletableFuture.supplyAsync(() -> String.format("Size of the result is %d%n", x.size()));
    var completableFuture = CompletableFuture.supplyAsync(() -> {
      sleep(3000);
      return List.of(3, 5, 6);
    });
    CompletableFuture<String> future = completableFuture.thenCompose(futureFunction::apply);
    var result = future.get();
    System.out.printf("The get method's return value: %s%n", result);

    var completableFuture2 = CompletableFuture.supplyAsync(() -> {
      sleep(3000);
      return List.of(1, 2, 3);
    });

    CompletableFuture<CompletableFuture<String>> future2 = completableFuture2.thenApply(futureFunction::apply); // We could have used completableFuture.thenApply(x->String.format("Size of the result is %d%n", x.size()) to end up with CompletableFuture<String>, but we cannot use the CompletableFuture provided by the futureFunction in that case.
    result = future2.get().get();
    System.out.printf("The get method's return value: %s%n", result);
  }
}
