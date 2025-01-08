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
    /**
     * A cached thread pool starts with zero threads and can potentially grow to have
     * {@code Integer.MAX_VALUE} threads.
     *
     * <p>Practically, the only limitation for a cached thread pool is the available system resources.
     * To better manage these resources, cached thread pools automatically remove threads that
     * remain idle for one minute. The idle timeout duration is configurable via
     * {@link ThreadPoolExecutor#setKeepAliveTime(long, TimeUnit) setKeepAliveTime}.
     *
     * <p>The following are the key characteristics of a cached thread pool:
     * <ul>
     *   <li><strong>Core pool size:</strong> The core pool size is zero, meaning the minimum number
     *       of workers to keep alive without timing out is zero. This implies the pool size
     *       becomes zero after threads complete their tasks and the keep-alive time has elapsed.</li>
     *   <li><strong>Queue capacity:</strong> The queue capacity is zero, which means tasks are
     *       not queued. Instead, a new thread is created for each task submitted.
     *       (Note: The pool size will exceed the <strong>corePoolSize</strong> only if the number of items trying to be executed at the same time exceeds <strong>corePoolSize + queueCapacity</strong>)</li>
     * </ul>
     *
     * <p>This behavior allows cached thread pools to scale dynamically with the workload.
     * <p>Check {@link ThreadPoolTaskExecutorSample} for more information about pool properties like core pool size, queue capacity and max pool size.
     */
    public static void main(String[] args) {
        var threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool(); // Note, we need to cast ExecutorService to ThreadPoolExecutor to be able to use the getPoolSize() and getActiveCount() methods.
        var keepAliveTime = 10;
        threadPool.setKeepAliveTime(keepAliveTime, TimeUnit.SECONDS);
        printThreadPool(threadPool, "Initial thread pool information");
        var latch = new CountDownLatch(1);
        var taskCount = 5;

        for (int i = 0; i < taskCount; i++) {
            var taskId = i + 1;
            threadPool.execute(() -> uncheck(() -> {
                printCurrentThread("Task %d is running".formatted(taskId));
                latch.await();
            }));
            sleep(10);
        }

        printThreadPool(threadPool, "Thread pool after %d tasks have been submitted".formatted(taskCount));
        latch.countDown();
        sleep(300);
        printThreadPool(threadPool, "Thread pool after %d tasks have been executed".formatted(taskCount));
        sleep(keepAliveTime * 1_000L);
        printThreadPool(threadPool, "Thread pool after %d seconds idle time".formatted(keepAliveTime));
        // The main thread won't exit until pool size becomes zero
        // (since pool size being greater than 0 means there are threads in the pool, even though they may not be active,
        // and if they are not daemon threads, main thread won't exit.
        // For ThreadPoolExecutor classes like the cached thread pool, threads are not daemon,
        // since they use Executors.defaultThreadFactory() as the ThreadFactory which sets daemon as false).
        // In this example, since we waited for the keepAliveTime after all threads finished, the pool size will become zero and the code will exit.
        // Thus, there is no need to call shutdown() for threadPool.
    }
}
