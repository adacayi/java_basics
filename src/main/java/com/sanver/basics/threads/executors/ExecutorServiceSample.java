package com.sanver.basics.threads.executors;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

public class ExecutorServiceSample {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1); // This is a ThreadPoolExecutor
        printThreadPool(service, "Initial pool state");
        Runnable writeA = () -> {
            System.out.println("writeA started. " + getThreadInfo());
            sleep(5000);// Event though first runnable object sleeps for a while second runnable object
            // is not started since the size of the thread pool is 1.
            for (int i = 0; i < 4; i++) {
                System.out.println("A");
                sleep(1000);
            }
        };
        Runnable writeB = () -> {
            System.out.println("writeB started. " + getThreadInfo());

            for (int i = 0; i < 4; i++) {
                System.out.println("B");
                sleep(1000);
            }
        };

        service.execute(writeA); // We can use the submit method instead to get back a Future<?>.
//        // Future<?> is returned when the Runnable overload is invoked.
//        // Future<Void> is returned when the Callable overload is invoked and the callable return value is null.
//        // We can also use invokeAll to invoke a Collection of Callable, which blocks the code until the tasks are executed and will return List<Future<Object>>.
        service.execute(writeB);

        printThreadPool(service, "Pool state after tasks were submitted");

        sleep(100);
        service.shutdown();
        System.out.println("Shutdown called");
        printThreadPool(service, "Pool state after shutdown called");
        // For ThreadPoolExecutor, shutdown does not interrupt the active tasks (use shutdownNow instead. shutdown lets all submitted tasks to execute including the ones in the queue, while shutdownNow will not execute tasks that are submitted but not started execution yet. shutdownNow will also try to interrupt already started tasks. Check AwaitTerminationShutdownSample.).
        // For ThreadPoolTaskExecutor shutdown interrupts the active tasks and remove tasks from the queue and won't execute them.
        // If the thread pool is not shutdown, the main thread won't end, because the thread pool doesn't let the main thread exit
        // until all active threads are finished and pool size becomes zero
        // (meaning there should be no threads left in the pool.
        // This is because the threads are not daemon threads for ThreadPoolExecutor,
        // which uses Executors.defaultThreadFactory() as the ThreadFactory.
        // On the other hand, ForkJoinPool.commonPool threads are daemon threads).
        // Since this thread pool is a thread pool with core pool size 1 and there are tasks submitted to the pool increasing the initial pool size of 0 to 1, the pool size will never go back to zero, hence will never exit if shutdown is not called explicitly.
    }
}
