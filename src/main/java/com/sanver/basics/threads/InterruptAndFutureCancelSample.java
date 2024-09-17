package com.sanver.basics.threads;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;

import static com.sanver.basics.utils.Utils.sleep;

public class InterruptAndFutureCancelSample {
    public static void main(String[] args) {
        Runnable print = () -> {
            int i = 0;
            while (true) {
                System.out.print(++i + " ");
            }
        };

//         Uncomment the method to test the interrupt behavior
//        interruptRunningThread(print);
//        cancelCompletableFuture(print);
//        cancelForkJoinPoolFuture(print);
//        cancelThreadPoolExecutorFuture(print);
//        cancelThreadPoolTaskExecutorFuture(print);
    }

    private static void interruptRunningThread(Runnable print) {
        var printThread = new Thread(print);
        printThread.start();
        printThread.interrupt(); // this won't stop the current thread execution
    }

    private static void cancelCompletableFuture(Runnable print) {
        var future = CompletableFuture.runAsync(print);
        sleep(1000);
        future.cancel(true); // This will stop the current thread execution
    }

    private static void cancelForkJoinPoolFuture(Runnable print) {
        var future = ForkJoinPool.commonPool().submit(print);
        sleep(1000);
        future.cancel(true); // This will stop the current thread execution
    }

    private static void cancelThreadPoolExecutorFuture(Runnable print) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        var future = executor.submit(print);
        sleep(1000);
        future.cancel(true); // This won't stop the current thread execution
    }

    private static void cancelThreadPoolTaskExecutorFuture(Runnable print) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        var future = executor.submit(print);
        sleep(1000);
        future.cancel(true); // This won't stop the current thread execution.
    }


}
