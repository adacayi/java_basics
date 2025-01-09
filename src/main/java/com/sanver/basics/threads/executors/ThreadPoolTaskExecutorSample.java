package com.sanver.basics.threads.executors;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Future;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;
import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

public class ThreadPoolTaskExecutorSample {

    public static void main(String[] args) {
        // ThreadPoolTaskExecutor is a java bean that allows for configuring a ThreadPoolExecutor in a bean style
        // by setting up the values for the instance variables like
        // corePoolSize, maxPoolSize, keepAliveSeconds, queueCapacity and exposing it as a Spring TaskExecutor.
        // The default configuration of core pool size is 1, max pool size, queue capacity as 2,147,483,647 (Integer.MAX_VALUE) and keep alive time is 60s.
        // This is roughly equivalent to Executors.newSingleThreadExecutor(), sharing a single thread for all tasks.

        // The corePoolSize is the minimum number of workers to keep alive without timing out
        // The pool size will exceed the corePoolSize only if the number of items trying to be executed at the same time
        // exceeds the queueCapacity.

        // Setting queueCapacity to 0 mimics Executors.newCachedThreadPool(), with immediate scaling of threads in
        // the pool to a very high number.

        // Effectively, to set a fixed pool size, set the corePoolSize and maxPoolSize to same value.
        // maxPoolSize must be greater than or equal to corePoolSize
        var executor = new ThreadPoolTaskExecutor();
        // This will set thread name prefix, which can be seen in the logs
        executor.setThreadNamePrefix("Thread pool sample threads - ");
        // The corePoolSize is the minimum number of workers to keep alive without timing out
        // The pool size will exceed the corePoolSize only if the number of items trying to be executed at the same time
        // exceeds the queueCapacity.
        executor.setCorePoolSize(2); // For detailed examples look into ThreadPoolTaskExecutorPropertiesSample class
        executor.setQueueCapacity(1); // Until queue capacity is full, the pool size will not exceed the core pool size
        executor.setMaxPoolSize(4); // Pool size won't exceed the max pool size, and if the queue capacity is full, and the pool size is equal to max pool size and a new task is to be submitted, this will result in an error: "did not accept task". Check the corePoolSizeWithBoundMaxPoolSizeAndBoundQueueCapacity method in ThreadPoolTaskExecutorPropertiesSample class.
        System.out.printf("Default thread keep alive seconds: %d%n", executor.getKeepAliveSeconds());
        var keepAliveSeconds = 10;
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.initialize(); // ThreadPoolTaskExecutor needs to be initialized, otherwise submitting tasks to it will result in a runtime exception java.lang.IllegalStateException: ThreadPoolTaskExecutor not initialized

        printThreadPool(executor, "%nInitial state".formatted());
        sleep(7_000);
        var taskCount = 5;
        var futures = new Future<?>[taskCount];

        for (int i = 1; i <= taskCount; i++) {
            if (i == 3) {
                System.out.println("Note that since pool size reached core pool size and the queue is not full, task 3 will be put in to the queue, rather than increasing the pool size. Thus, it won't be executed immediately as well.");
            }

            if (i > 3) {
                System.out.println("Note that now the queue capacity is full with task 3, and the max pool size is not reached, this task will increase the pool size and will be executed immediately.");
            }
            sleep(15_000);
            futures[i - 1] = executor.submit(getRunnable(i));
            sleep(20); // This is to make sure execution is started and "Running process x" is printed before printing the executor state.
            printThreadPool(executor, String.format("%nState after task %d was submitted", i));
        }

        printThreadPool(executor, "After all tasks submitted");
        Arrays.asList(futures).forEach(future -> uncheck(() -> future.get()));
        printThreadPool(executor, "%nAfter all tasks finished".formatted());
        sleep(keepAliveSeconds * 1000L);
        printThreadPool(executor, "After keep alive seconds (%ds)".formatted(keepAliveSeconds));
        System.out.println("Notice that the pool size does not drop to zero, but to the core pool size after keep alive seconds.");
        sleep(7_000);
        executor.shutdown(); // For ThreadPoolTaskExecutor, shutdown interrupts the active tasks, while this is not true for the ThreadPoolExecutor.
        printThreadPool(executor, "%nAfter shutdown called".formatted());
    }

    public static Runnable getRunnable(int i) {
        return () -> {
            System.out.printf("Running process %d%n", i);

            if (i != 3) {
                sleep(70_000 - (i - 1) * 15_000L);
            }

            System.out.printf("Process %d finished%n", i);
        };
    }
}
