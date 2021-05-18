package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeoutWithMultipleFuturesAndExecutor {

  private static AtomicInteger count = new AtomicInteger(0);

  public static void main(String[] args) throws InterruptedException {
    Runnable runnable = () -> {
      var id = count.addAndGet(1);
      System.out.printf("%d: started. Thread: %d\n", id, Thread.currentThread().getId());
      if (id == 2 || id == 3) {
        sleep(7000);
      }
      System.out.printf("%d: completed. Thread: %d\n", id, Thread.currentThread().getId());
    };

    var executor = Executors.newFixedThreadPool(2);
    var future1 = CompletableFuture.runAsync(runnable, executor).orTimeout(1000, TimeUnit.MILLISECONDS);
    var future2 = CompletableFuture.runAsync(runnable, executor).orTimeout(1000, TimeUnit.MILLISECONDS);
    var future3 = CompletableFuture.runAsync(runnable, executor).orTimeout(1000, TimeUnit.MILLISECONDS);
    var future4 = CompletableFuture.runAsync(runnable, executor).orTimeout(1000, TimeUnit.MILLISECONDS);
    var future5 = CompletableFuture.runAsync(runnable, executor).orTimeout(1000, TimeUnit.MILLISECONDS);
    var combined = CompletableFuture.allOf(future1, future2, future3, future4, future5);
    combined.handle((s, t) -> {
      var id = Thread.currentThread().getId();
      if (t != null) {
        System.out.printf("%d: Some error happened: %s\n", id, t);
        return 0;
      }
      System.out.printf("%d: Completed successfully.\n", id);
      return 1;
    });
    // Note that when timeout occurs, no unexecuted futures are assigned to freed threads.
    // Execution of already started threads continues though.

    try {
      combined.get();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    CompletableFuture.runAsync(() ->
            System.out.println(
                "This thread cannot be acquired while the other 2 timed-out threads are still executing, because the "
                    + "pool size is 2 and although they are timed out they are still executing"),
        executor
    );

    executor.shutdown();
  }

  private static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
