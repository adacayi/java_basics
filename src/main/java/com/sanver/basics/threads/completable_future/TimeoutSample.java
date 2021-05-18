package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeoutSample {

  private static AtomicInteger count = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException {
    Runnable runnable = () -> {
      var id = count.addAndGet(1);
      System.out.printf("%d: started. Thread: %d\n", id, Thread.currentThread().getId());
      var waitTime = id * 1000;
      sleep(waitTime);
      System.out.printf("%d: completed. Thread: %d\n", id, Thread.currentThread().getId());
    };

    var future1 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    var future2 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    var future3 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    // sleep(10000); If this block is put here, even there is a timeout on future2 and future3, it won't prevent code
    // from execution, also there won't be any exceptions thrown, since ExecutionException is thrown when get is called.
    try {
      future1.get();
    } catch (ExecutionException e) {
      System.out.println("future1 timed out");
    }

    try {
      future2.get();
    } catch (ExecutionException e) {
      System.out.println("future2 timed out");
    }

    try {
      future3.get();
    } catch (ExecutionException e) {
      System.out.println("future3 timed out");
    }
    // We can see that even if we get a timeout, the runnables are executed completely if the main thread is still
    // alive.
    sleep(10000);
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
