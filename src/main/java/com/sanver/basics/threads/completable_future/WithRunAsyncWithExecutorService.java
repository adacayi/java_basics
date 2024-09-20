package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class WithRunAsyncWithExecutorService {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    var executor = Executors.newFixedThreadPool(1);
    var first = CompletableFuture.runAsync(getRunnable(1), executor);
    var second = CompletableFuture.runAsync(getRunnable(2), executor);
    var firstResult = first.get();
    System.out.println("First result: " + firstResult);
    var secondResult = second.get();
    System.out.println("Second result: " + secondResult);
    executor.shutdown();
  }

  public static Runnable getRunnable(int i) {
    return () -> {
      System.out.printf("Process %d  started. %s%n", i, getThreadInfo());
      sleep(2000);
      System.out.printf("Process %d finished. %s%n", i, getThreadInfo());
    };
  }
}
