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
      System.out.printf("%d: started. Thread: %d%n", id, Thread.currentThread().getId());
      if (id == 2 || id == 3) {
        throw new RuntimeException(String.format("Some error occurred in thread %d", Thread.currentThread().getId()));
      }
      System.out.printf("%d: completed. Thread: %d%n", id, Thread.currentThread().getId());
      return "completed";
    };

    var future1 = CompletableFuture.supplyAsync(supplier);
    var future2 = CompletableFuture.supplyAsync(supplier);
    var future3 = CompletableFuture.supplyAsync(supplier);
    sleep(1000);
    var future4 = CompletableFuture.supplyAsync(supplier);
    var future5 = CompletableFuture.supplyAsync(supplier);

    CompletableFuture<Void> combined = CompletableFuture.allOf(future1, future2, future3, future4, future5); // This combined completable future would return null when combined.get() is called, if all the threads were executed without an exception.

    // Note that handle is executed only once and r parameter will be null and e parameter will be either null
    // (if there is no exception in any of the futures) or will be the first exception thrown from one of the futures.
    // All futures are executed regardless of some of them throwing exceptions.
    // handle and thenAccept are executed after all completable futures are executed (regardless of them throwing exceptions).

    CompletableFuture<Void> finalCompletable = combined.handle(
        (r, e) -> {
          sleep(5000);
          if (e == null) {
            System.out.println("No error occurred. Return value is " + r);
            count.addAndGet(1);
            return -1;
          }
          System.out.println("Error occurred: " + e);
          return count.addAndGet(1);
        }).thenAccept(x -> {
      sleep(5000);
      System.out.println("The return value is " + x);
    });

    // When get method is called, it throws ExecutionException only wrapping the first exception.
    // Note that even get is called from the combined object (not from finalCompletable object),
    // it will wait for handle and thenAccept methods to finish.

    combined.get();

    // If finalCompletable.get() was called here instead of combined.get(), then no exception would be thrown
//    finalCompletable.get();
  }
}
