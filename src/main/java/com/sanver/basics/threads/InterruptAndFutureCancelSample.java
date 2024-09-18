package com.sanver.basics.threads;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.ThreadPoolExecutor;

import static com.sanver.basics.utils.Utils.sleep;

public class InterruptAndFutureCancelSample {
    public static void main(String[] args) {
        Runnable print = () -> {
            int i = 0;
            while (true) {
                i++;
                if (i % 100_000_000 == 0) {
                    System.out.print(i + " ");
                }
            }
        };

        Runnable printWithSleep = () -> {
            int i = 0;
            while (true) {
                System.out.print(++i + " ");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.printf("%n%nThread is interrupted. %s%n%n", e);
                    break;
                }
            }
        };

//         Uncomment the method to test the interrupt behavior
//        interruptRunningThread(print);
//        interruptRunningThread(printWithSleep);
//        cancelCompletableFuture(print);
//        cancelCompletableFuture(printWithSleep);
//        cancelForkJoinPoolFuture(print);
//        cancelForkJoinPoolFuture(printWithSleep);
//        cancelThreadPoolExecutorFuture(print);
//        cancelThreadPoolExecutorFuture(printWithSleep);
//        cancelThreadPoolTaskExecutorFuture(print);
//        cancelThreadPoolTaskExecutorFuture(printWithSleep);
    }

    private static void interruptRunningThread(Runnable print) {
        var printThread = new Thread(print);
        printThread.start();
        sleep(1000);
        printThread.interrupt(); // this won't stop the current thread execution for print, but it will for printWithSleep, since Thread.sleep will be interrupted and will throw InterruptedException.
    }

    private static void cancelCompletableFuture(Runnable print) {
        var future = CompletableFuture.runAsync(print);
        sleep(1000);
        future.cancel(true); // This won't stop the current thread execution for both print and printWithSleep, since CompletableFuture.cancel does not interrupt the underlying thread, because by design there does not need to be a thread at all for CompletableFuture instance in the first place. Check: https://nurkiewicz.com/2015/03/completablefuture-cant-be-interrupted.html
        sleep(5000); // If we used future.join(), we would get a CancellationException, but we used sleep here to show the thread continues to work. Remember the threads for the ForkJoinPool are daemon threads,
        // and all async methods in CompletableFuture without an explicit Executor argument are performed using the ForkJoinPool.commonPool() (unless it does not support a parallelism level of at least two, in which case, a new Thread is created to run each task).

        try {
            future.join();
        } catch (CancellationException e) {
            System.out.printf("%n%nCancellation exception caught: %s%n%n", e);
        }

        sleep(3000); // Notice even after CancellationException is thrown, the thread carries on working.
    }

    private static void cancelForkJoinPoolFuture(Runnable print) {
        ForkJoinTask<?> future = ForkJoinPool.commonPool().submit(print);
        sleep(1000);
        future.cancel(true); // This won't stop the current thread execution for both print and printWithSleep. Similar to CompletableFuture, ForkJoinTask cancel method does not interrupt the underlying thread.
        sleep(5000); // If we used future.join(), we would get an CancellationException, but we used sleep here to show the thread continues to work. Remember the threads for the ForkJoinPool are daemon threads.

        try {
            future.join();
        } catch (CancellationException e) {
            System.out.printf("%n%nCancellation exception caught: %s%n%n", e);
        }

        sleep(3000); // Notice even after CancellationException is thrown, the thread carries on working.
    }

    private static void cancelThreadPoolExecutorFuture(Runnable print) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        var future = executor.submit(print);
        sleep(1000);
        future.cancel(true); // This won't stop the current thread execution for print, but it will for printWithSleep, since Thread.sleep will be interrupted and will throw InterruptedException.
        sleep(1000);

        try {
            future.get();
        } catch (Exception e) {
            System.out.printf("%n%nException caught: %s%n%n", e);
        }

        sleep(3000);
        executor.shutdown();
    }

    private static void cancelThreadPoolTaskExecutorFuture(Runnable print) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        var future = executor.submit(print);
        sleep(1000);
        future.cancel(true); // This won't stop the current thread execution for print, but it will for printWithSleep, since Thread.sleep will be interrupted and will throw InterruptedException.
        sleep(1000);

        try {
            future.get();
        } catch (Exception e) {
            System.out.printf("%n%nException caught: %s%n%n", e);
        }

        sleep(3000);
        executor.shutdown();
    }
}
