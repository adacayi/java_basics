package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeoutSample {

  private static final AtomicInteger count = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    Runnable runnable = () -> {
      var id = count.addAndGet(1);
      System.out.printf("%d:   started. %s%n", id, getThreadInfo());
      var waitTime = id * 1000;
      sleep(waitTime);
      System.out.printf("%d: completed. %s%n", id, getThreadInfo());
    };

    var future1 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    var future2 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    var future3 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    sleep(6000);
    // Even there is a timeout on future2 and future3, it won't prevent the code
    // from execution, also there won't be any exceptions thrown until get is called.

    future1.get();

    try {
      future2.get();
    } catch (ExecutionException e) { // The exception is an ExecutionException wrapping a TimeoutException
      System.out.println("future2 timed out. " + e);
    }

    try {
      future3.get();
    } catch (ExecutionException e) {
      System.out.println("future3 timed out. " + e);
    }
  }
}
