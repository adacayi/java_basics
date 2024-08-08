package com.sanver.basics.threads.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.sanver.basics.utils.Utils.sleep;

public class ExecutorServiceSample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
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

        taskA.get();// To wait for taskA to finish
        taskB.get();// To wait for taskB to finish

        System.out.println("Finished.");
        service.shutdown();
        // If the thread pool is not shutdown, the main thread won't end, because the thread pool doesn't let the main thread exit
        // until all active threads are finished and pool size becomes zero.
        // Since this thread pool is a thread pool with core pool size 1, the pool size will never be zero, hence will never exit if shutdown is not called explicitly.
    }
}
