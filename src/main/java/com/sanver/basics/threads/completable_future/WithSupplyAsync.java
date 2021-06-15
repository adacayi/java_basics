package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class WithSupplyAsync {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var completableFuture = CompletableFuture.supplyAsync(getSupplier(1));
    sleep(3000);
    System.out.println("completableFuture's get method is called");
    var result = completableFuture.get();
    System.out.println(result);
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
