package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WithRunAsync {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var completableFuture = CompletableFuture.runAsync(getRunnable(1));
    sleep(3000);
    System.out.println("completableFuture's get method will be called");
    var result = completableFuture.get();
    System.out.printf("The get method's return value: %s%n", result);
  }

  public static Runnable getRunnable(int i) {
    return () -> {
      System.out.printf("Process %d  started. %s%n", i, getThreadInfo());
      sleep(2000);
      System.out.printf("Process %d finished. %s%n", i, getThreadInfo());
    };
  }
}