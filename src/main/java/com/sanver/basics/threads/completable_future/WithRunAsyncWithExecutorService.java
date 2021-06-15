package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class WithRunAsyncWithExecutorService {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Runnable getPrimeNumbers = () -> {
      var results = new ArrayList<>(List.of(2));
      int i, j;
      for (i = 3; i < 10; i++) {
        System.out.printf("%d: Calculating if %d is prime\n", Thread.currentThread().getId(), i);
        sleep(1000);
        var sqrt = Math.sqrt(i);
        for (j = 2; j <= sqrt; j++) {
          if (i % j == 0) {
            break;
          }
        }
        if (j > sqrt) {
          results.add(i);
        }
      }
      System.out.println(results);
    };

    var executor = Executors.newFixedThreadPool(1);
    var first = CompletableFuture.runAsync(getPrimeNumbers, executor);
    var second = CompletableFuture.runAsync(getPrimeNumbers, executor);
    var firstResult = first.get();
    System.out.println(firstResult);
    var secondResult = second.get();
    System.out.println(secondResult);
    executor.shutdown();
  }
}
