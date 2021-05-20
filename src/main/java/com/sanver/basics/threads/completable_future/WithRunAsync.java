package com.sanver.basics.threads.completable_future;

import static com.sanver.basics.utils.ThreadUtils.sleep;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class WithRunAsync {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Runnable getPrimeNumbers = () -> {
      var results = new ArrayList<>(List.of(2));
      int i, j;
      for (i = 3; i < 10; i++) {
        System.out.printf("Calculating if %d is prime\n", i);
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

    var completableFuture = CompletableFuture.runAsync(getPrimeNumbers);
    sleep(3000);
    System.out.println("completableFuture's get method is called");
    var results = completableFuture.get();
    System.out.printf("The get method's return value: %s\n", results);
  }
}
