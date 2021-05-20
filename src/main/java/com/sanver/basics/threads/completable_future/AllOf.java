package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.ThreadUtils.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AllOf {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Runnable getPrimeNumbers = () -> {
      var results = new ArrayList<>(List.of(2));
      int i, j;
      for (i = 3; i < 10; i++) {
        System.out.printf("%d: Calculating if %d is prime\n", Thread.currentThread().getId(),i);
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
      System.out.printf("Runnable ended with primes %s\n", results);
    };

    var completableFuture1 = CompletableFuture.runAsync(getPrimeNumbers);
    var completableFuture2 = CompletableFuture.runAsync(getPrimeNumbers);
    var completableFuture3 = CompletableFuture.runAsync(getPrimeNumbers);
    var combined=CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3);
    combined.get();
  }
}
