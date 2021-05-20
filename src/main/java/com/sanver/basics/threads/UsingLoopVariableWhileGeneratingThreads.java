package com.sanver.basics.threads;

import static com.sanver.basics.utils.ThreadUtils.sleep;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class UsingLoopVariableWhileGeneratingThreads {

  public static void main(String[] args) {
    int poolSize = 4, operationCount = 16;
    ExecutorService service = Executors.newFixedThreadPool(poolSize);
    Future<?>[] taskArray = IntStream.rangeClosed(1, operationCount).mapToObj(x -> service.submit(() -> {
      System.out.println("Task " + x + " started.");
      sleep(2000);
      System.out.println("Task " + x + " finished.");
    })).toArray(x -> new Future<?>[operationCount]);

    for (Future<?> task : taskArray) {
      try {
        task.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    service.shutdown();
  }
}
