package com.sanver.basics.threads.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.sanver.basics.utils.Utils.sleep;

public class ExecutorServiceWithCallableSample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int[] value = new int[1];
        try (var service = Executors.newFixedThreadPool(2)) {
            Callable<int[]> increment = () -> {
                sleep(3000);// This is put to show that Future.get() method waits for the call method to finish
                value[0] = 1000;
                return value;
            };

            Future<int[]> future = service.submit(increment);

            System.out.println(value[0]);
            System.out.println(future.get()[0]);
        }
    }
}
