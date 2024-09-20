package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeoutWithMultipleFutures {

  private static final AtomicInteger count = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException {
    Runnable runnable = () -> {
      var id = count.addAndGet(1);
      System.out.printf("%d:   started. %s%n", id, getThreadInfo());
      sleep(15_000);

      if (id == 2 || id == 3) {
        sleep(10_000);
      }

      System.out.printf("%d: completed. %s%n", id, getThreadInfo());
    };

    var future1 = CompletableFuture.runAsync(runnable).orTimeout(16_500, TimeUnit.MILLISECONDS);
    sleep(3000);
    var future2 = CompletableFuture.runAsync(runnable).orTimeout(16_500, TimeUnit.MILLISECONDS);
    sleep(3000);
    var future3 = CompletableFuture.runAsync(runnable).orTimeout(16_500, TimeUnit.MILLISECONDS);
    sleep(3000);
    var future4 = CompletableFuture.runAsync(runnable).orTimeout(16_500, TimeUnit.MILLISECONDS);
    sleep(3000);
    var future5 = CompletableFuture.runAsync(runnable).orTimeout(16_500, TimeUnit.MILLISECONDS);
    var combined = CompletableFuture.allOf(future1, future2, future3, future4, future5);
    // Note that handle is executed only once and r parameter will be null and e parameter will be either null
    // (if there is no exception in any of the futures) or will be the first exception thrown from one of the futures.
    // All futures are executed regardless of some of them throwing exceptions.
    // When get method is called, it throws ExecutionException only wrapping the first TimeoutException.
    // Note that handle and get doesn't wait for the timed-out futures to execute fully, although they continue to run

    combined.handle((r, e) -> {
      if (e != null) {
        System.out.printf("Some error happened: %s%n", e);
        return 0;
      }
      System.out.printf("Completed successfully. %s%n", getThreadInfo());
      return 1;
    });
    try {
      combined.get();
    } catch (ExecutionException e) {
      System.out.println("Combined get resulted in an exception: " + e);
    }
    sleep(15_000);
  }
}
