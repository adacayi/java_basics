package com.sanver.basics.threads.executors;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;
import static com.sanver.basics.utils.Utils.printCurrentThread;
import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

public class CachedThreadPoolSample {
    /*
    The cached pool starts with zero threads and can potentially grow to have Integer.MAX_VALUE threads.
    Practically, the only limitation for a cached thread pool is the available system resources.
    To better manage system resources, cached thread pools will remove threads that remain idle for one minute.
    This one minute is configurable via the setKeepAliveTime.
     */
    public static void main(String[] args) {
        var threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool(); // Note, we need to cast ExecutorService to ThreadPoolExecutor to be able to use the getPoolSize() and getActiveCount() methods.
        var keepAliveTime = 10;
        threadPool.setKeepAliveTime(keepAliveTime, TimeUnit.SECONDS);
        System.out.printf("Keep alive time: %d seconds%n", threadPool.getKeepAliveTime(TimeUnit.SECONDS));
        printThreadPool(threadPool, "Initial thread pool information");
        var latch = new CountDownLatch(1);
        var taskCount = 5;

        for (int i = 0; i < taskCount; i++) {
            var processId = i + 1;
            threadPool.execute(() -> uncheck(() -> {
                printCurrentThread(String.format("Process %d is running", processId));
                latch.await();
            }));
            sleep(10);
        }

        printThreadPool(threadPool, String.format("Thread pool after %d tasks have been submitted", taskCount));
        latch.countDown();
        sleep(100);
        printThreadPool(threadPool, String.format("Thread pool after %d tasks have been executed", taskCount));
        sleep(keepAliveTime * 1000L);
        printThreadPool(threadPool, String.format("Thread pool after %d seconds idle time", keepAliveTime));
        // The main thread won't exit until pool size becomes zero
        // (since pool size being greater than 0 means there are threads in the pool, even though they may not be active,
        // and if they are not daemon threads, main thread won't exit.
        // For ThreadPoolExecutor classes like the cached thread pool, threads are not daemon,
        // since they use Executors.defaultThreadFactory() as the ThreadFactory which sets daemon as false).
        // In this example, since we waited for the keepAliveTime after all threads finished, the pool size will become zero and the code will exit.
        // Thus, there is no need to call shutdown() for threadPool. (Note: We need to cast threadPool to ExecutorService to access the shutdown() method)
    }
}
