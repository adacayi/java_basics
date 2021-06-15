package com.sanver.basics.threads;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class ExecutorServiceWithCallableSample {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    int[] value = new int[1];
    var service = Executors.newFixedThreadPool(2);
    Callable<int[]> increment = () -> {
      sleep(3000);// This is put to show that Future.get() method waits for the call method to finish
      value[0] = 1000;
      return value;
    };

    var future = service.submit(increment);

    System.out.println(future.get()[0]);
    System.out.println(value[0]);

    service.shutdown();
  }
}
