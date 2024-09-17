package com.sanver.basics.threads.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

public class ExecutorServiceSample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(1); // This is a ThreadPoolExecutor
        printThreadPool((ThreadPoolExecutor)service, "Initial pool state");
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

        printThreadPool((ThreadPoolExecutor)service, "Pool state after tasks were submitted");

        taskA.get();// To wait for taskA to finish
        taskB.get();// To wait for taskB to finish

        System.out.println("Finished.");
        service.shutdown(); // For ThreadPoolExecutor, shutdown does not interrupt the active tasks, while this is not true for the ThreadPoolTaskExecutor.
        // If the thread pool is not shutdown, the main thread won't end, because the thread pool doesn't let the main thread exit
        // until all active threads are finished and pool size becomes zero
        // (meaning there should be no threads left in the pool.
        // This is because the threads are not daemon threads for ThreadPoolExecutor,
        // which uses Executors.defaultThreadFactory() as the ThreadFactory.
        // On the other hand, ForkJoinPool.commonPool threads are daemon threads).
        // Since this thread pool is a thread pool with core pool size 1 and there are tasks submitted to the pool increasing the initial pool size of 0 to 1, the pool size will never go back to zero, hence will never exit if shutdown is not called explicitly.
    }
}
