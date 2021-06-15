package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WithRunAsync {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var completableFuture = CompletableFuture.runAsync(getRunnable(1));
    sleep(3000);
    System.out.println("completableFuture's get method is called");
    var results = completableFuture.get();
    System.out.printf("The get method's return value: %s\n", results);
  }

  public static Runnable getRunnable(int i) {
    return () -> {
      System.out.printf("Process %d started.\n", i);
      sleep(2000);
      System.out.printf("Process %d finished.\n", i);
    };
  }
}