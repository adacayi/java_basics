package com.sanver.basics.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import static com.sanver.basics.utils.Utils.sleep;

public class FutureTaskSample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<List<Integer>> process = () -> {
            var result = new ArrayList<Integer>();
            for (int i = 1; i < 10; i++) {
                result.add(i);
                System.out.print(i + " ");
                sleep(300);
            }

            return result;
        };

        var future = new FutureTask<>(process);
        var thread = new Thread(future); // We can use FutureTask to get result of an executed thread. We can use it to propagate exceptions in the thread to the main thread as well by calling the get method. See ExceptionHandlingInThreads for an example.

        thread.start();
        var result = future.get();
        System.out.printf("%nThread finished. Result: %s%n", result);
    }
}
