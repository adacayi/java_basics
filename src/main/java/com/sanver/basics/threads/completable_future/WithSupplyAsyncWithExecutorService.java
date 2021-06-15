package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class WithSupplyAsyncWithExecutorService {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var executor = Executors.newFixedThreadPool(1);
    var first = CompletableFuture.supplyAsync(getSupplier(1), executor);
    var second = CompletableFuture.supplyAsync(getSupplier(2), executor);
    var firstResult = first.get();
    System.out.println(firstResult);
    var secondResult = second.get();
    System.out.println(secondResult);
    executor.shutdown();
  }

  public static Supplier<Integer> getSupplier(int i) {
    return () -> {
      System.out.printf("Process %d started.\n", i);
      sleep(2000);
      System.out.printf("Process %d finished.\n", i);
      return i;
    };
  }
}
