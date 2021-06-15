package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class HandleExceptions {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Supplier<String> supplierWithException = () -> {
      sleep(3000);
      throw new RuntimeException("Some error occurred");
    };

    Supplier<List<Integer>> supplier = () -> {
      sleep(3000);
      return List.of(1, 3, 5);
    };

    var completableFutureWithException = CompletableFuture
        .supplyAsync(supplierWithException)
        .handle((s, t) -> t == null ? s : "Some error occurred");
    var completableFutureWithoutException = CompletableFuture
        .supplyAsync(supplier)
        .handle((s, t) -> t == null ? s : "Some error occurred");
    var exceptionResult = completableFutureWithException.get();
    var result = completableFutureWithoutException.get();
    System.out.println(exceptionResult);
    System.out.println(result);
  }
}
