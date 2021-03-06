package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class HandleExceptionsWithMultipleFuturesAndExecutor {

  private static AtomicInteger count = new AtomicInteger(0);
  private static AtomicInteger idCount = new AtomicInteger(0);

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Supplier<String> supplier = () -> {
      var id = idCount.addAndGet(1);
      System.out.printf("%d: started. Thread: %d\n", id, Thread.currentThread().getId());
      sleep(2000);
      if (id == 2 || id == 3) {
        throw new RuntimeException(String.format("Some error occurred in thread %d", Thread.currentThread().getId()));
      }
      System.out.printf("%d: completed. Thread: %d\n", id, Thread.currentThread().getId());
      return "completed";
    };

    var executor = Executors.newFixedThreadPool(2);
    var future1 = CompletableFuture.supplyAsync(supplier, executor);
    var future2 = CompletableFuture.supplyAsync(supplier, executor);
    var future3 = CompletableFuture.supplyAsync(supplier, executor);
    var future4 = CompletableFuture.supplyAsync(supplier, executor);
    var future5 = CompletableFuture.supplyAsync(supplier, executor);

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

    // Note that there is a difference in behavior to timeout for error handling with executors,
    // because for timeout new threads are not executed when timeout occurs, but it is not the case with exceptions
    combined.get();
  }
}
