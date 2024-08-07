package com.sanver.basics.threads.executors;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.LambdaExceptionUtil.rethrowConsumer;
import static com.sanver.basics.utils.Utils.sleep;

public class ThreadPoolTaskExecutorSample {

    public static void main(String[] args) {
        // ThreadPoolTaskExecutor is a java bean that allows for configuring a ThreadPoolExecutor in a bean style
        // by setting up the values for the instance variables like
        // corePoolSize, maxPoolSize, keepAliveSeconds, queueCapacity and exposing it as a Spring TaskExecutor.
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
        executor.initialize();

        print(executor, "Initial state");
        var taskCount = 5;
        var futures = new Future<?>[taskCount];

        for (int i = 1; i <= taskCount; i++) {
            if (i == 3) {
                System.out.println("Note that since the queue capacity is one, task 3 will be put in to the queue, rather than increasing the pool size. Thus, it won't be executed immediately as well.");
            }

            if (i > 3) {
                System.out.println("Note that now the queue capacity is full with task 3, and the max pool size is not reached, this task will increase the pool size and will be executed immediately.");
            }
            futures[i - 1] = executor.submit(getRunnable(i));
            sleep(20); // This is to make sure execution is started and "Running process x" is printed before printing the executor state.
            print(executor, String.format("State after task %d was submitted", i));
        }

        print(executor, "After all tasks submitted");
        Arrays.asList(futures).forEach(rethrowConsumer(Future::get));
        print(executor, "After all tasks finished");
        sleep(keepAliveSeconds * 1000L);
        print(executor, String.format("After keep alive seconds (%ds)", keepAliveSeconds));
        System.out.println("Notice that the pool size does drop to zero but to the core pool size after keep alive seconds.");
        executor.shutdown();
    }

    public static Runnable getRunnable(int i) {
        return () -> {
            System.out.printf("Running process %d%n", i);
            sleep(3000);
            System.out.printf("Process %d finished%n", i);
        };
    }

    private static void print(ThreadPoolTaskExecutor threadPool, String... info) {
        System.out.println();
        for (var infoItem : info) {
            System.out.println(infoItem);
        }
        var max = Arrays.stream(info).map(x -> x.length()).mapToInt(x -> x).max().getAsInt();
        System.out.println(IntStream.range(0, max).mapToObj(x -> "-").collect(Collectors.joining()));

        System.out.printf("Active threads in the pool: %d%n", threadPool.getActiveCount());
        System.out.printf("The number of threads in the pool: %d%n%n", threadPool.getPoolSize());
    }
}
