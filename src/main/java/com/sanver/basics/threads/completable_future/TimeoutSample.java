package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

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
    sleep(6000);
    // Even there is a timeout on future2 and future3, it won't prevent the code
    // from execution, also there won't be any exceptions thrown until get is called.
    // Note that timed-out futures won't prevent the main thread to exit.
    // Hence their execution won't be completed if main thread finishes.
    // You can try by removing the sleep code above.
    // You won't be able to see the completed messages for future2 and future3 in that case.
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
  }
}
