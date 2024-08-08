package com.sanver.basics.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.sanver.basics.utils.Utils.sleep;

public class InterruptWithExecutorThreads {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<?> future = executorService.submit(() -> {
            try {
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                System.out.println("Executor service thread interrupted.");
                System.out.println("isInterruptedValue = " + Thread.currentThread().isInterrupted());
                Thread.currentThread().interrupt();
                System.out.println("isInterruptedValue = " + Thread.currentThread().isInterrupted());
            }
        });

        System.out.println("Thread started");

        sleep(2000);
        future.cancel(true);
        executorService.shutdown();
    }
}
