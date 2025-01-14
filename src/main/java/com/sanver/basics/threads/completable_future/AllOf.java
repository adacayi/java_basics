package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;

import static com.sanver.basics.utils.Utils.sleep;

/**
 * @see <a href = "https://www.baeldung.com/java-completablefuture">Completable Future</a>
 */
public class AllOf {
  public static void main(String[] args) {
    var completableFuture1 = CompletableFuture.runAsync(getRunnable(1));
    var completableFuture2 = CompletableFuture.runAsync(getRunnable(2));
    var completableFuture3 = CompletableFuture.runAsync(getRunnable(3));

    // Note that runAsync immediately starts execution

    var combined = CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3);
    combined.join();
  }

  public static Runnable getRunnable(int i) {
    return () -> {
      System.out.printf("Process %d started.%n", i);
      sleep(2000);
      System.out.printf("Process %d finished.%n", i);
    };
  }
}
