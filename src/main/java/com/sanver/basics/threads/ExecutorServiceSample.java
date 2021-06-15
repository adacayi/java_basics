package com.sanver.basics.threads;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceSample {

  public static void main(String[] args) {
    ExecutorService service = Executors.newFixedThreadPool(1);
    Runnable writeA = () -> {
      sleep(2000);// Event though first runnable object sleeps for a while second runnable object
      // is not started since the size of the thread pool is 1.
      for (int i = 0; i < 4; i++) {
        System.out.println("A");
      }
    };
    Runnable writeB = () -> {
      for (int i = 0; i < 4; i++) {
        System.out.println("B");
      }
    };

    Future<?> taskA = service.submit(writeA);
    Future<?> taskB = service.submit(writeB);

    try {
      taskA.get();// To wait for taskA to finish
      taskB.get();// To wait for taskB to finish
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    System.out.println("Finished.");
    service.shutdown();
  }
}
