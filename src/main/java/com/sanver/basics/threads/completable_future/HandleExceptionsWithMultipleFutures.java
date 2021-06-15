package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class HandleExceptionsWithMultipleFutures {

  private static AtomicInteger count = new AtomicInteger(0);
  private static AtomicInteger idCount = new AtomicInteger(0);

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Supplier<String> supplier = () -> {
      var id = idCount.addAndGet(1);
      System.out.printf("%d: started. Thread: %d\n", id, Thread.currentThread().getId());
      if (id == 2 || id == 3) {
        throw new RuntimeException(String.format("Some error occurred in thread %d", Thread.currentThread().getId()));
      }
      System.out.printf("%d: completed. Thread: %d\n", id, Thread.currentThread().getId());
      return "completed";
    };

    var future1 = CompletableFuture.supplyAsync(supplier);
    var future2 = CompletableFuture.supplyAsync(supplier);
    var future3 = CompletableFuture.supplyAsync(supplier);
    sleep(1000);
    var future4 = CompletableFuture.supplyAsync(supplier);
    var future5 = CompletableFuture.supplyAsync(supplier);

    var combined = CompletableFuture.allOf(future1, future2, future3, future4,future5);

    combined.handle(
        (s, t) -> {
          if (t == null) {
            System.out.println("No error occurred. Return value is " + s);
            count.addAndGet(1);
            return -1;
          }
          System.out.println("Error occurred: " + t);
          return count.addAndGet(1);
        }).thenAccept(x -> System.out.println("The return value is " + x)).get();

    // Note that handle is executed only once and s parameter will be null and t parameter will be either null
    // (if there is no exception in any of the futures) or will be the first exception thrown from one of the futures.
    // All futures are executed regardless of some of them throwing exceptions.
    // When get method is called, it throws ExecutionException only wrapping the first exception.
    combined.get();
  }
}
