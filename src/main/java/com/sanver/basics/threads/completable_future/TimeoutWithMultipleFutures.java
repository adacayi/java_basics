package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeoutWithMultipleFutures {

  private static AtomicInteger count = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException {
    Runnable runnable = () -> {
      var id = count.addAndGet(1);
      System.out.printf("%d: started. Thread: %d\n", id, Thread.currentThread().getId());

      if (id == 2 || id == 3) {
        sleep(2000);
      }

      System.out.printf("%d: completed. Thread: %d\n", id, Thread.currentThread().getId());
    };

    var future1 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    var future2 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    var future3 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    sleep(1000);
    var future4 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    var future5 = CompletableFuture.runAsync(runnable).orTimeout(1500, TimeUnit.MILLISECONDS);
    var combined = CompletableFuture.allOf(future1, future2, future3, future4, future5);
    // Note that handle is executed only once and s parameter will be null and t parameter will be either null
    // (if there is no exception in any of the futures) or will be the first exception thrown from one of the futures.
    // All futures are executed regardless of some of them throwing exceptions.
    // When get method is called, it throws ExecutionException only wrapping the first TimeoutException.
    // Note that get doesn't wait for the result of the timed-out futures, although they continue to run

    combined.handle((s, t) -> {
      var id = Thread.currentThread().getId();
      if (t != null) {
        System.out.printf("%d: Some error happened: %s\n", id, t);
        return 0;
      }
      System.out.printf("%d: Completed successfully.\n", id);
      return 1;
    });
    try {
      combined.get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    sleep(5000);
  }
}
