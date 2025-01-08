package com.sanver.basics.threads.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;
import static com.sanver.basics.utils.Utils.sleepNano;

/**
 * Demonstrates the usage of {@link ExecutorService} methods of {@code shutdown()},
 * {@code shutdownNow()}, {@code awaitTermination()} and {@code close()} in various scenarios.
 *
 * <p>This class provides examples of how thread pools behave during normal operation,
 * shutdown, and termination. The behavior of interruptible and non-interruptible tasks
 * is also demonstrated to highlight the implications of calling {@code shutdown()} and
 * {@code shutdownNow()}.
 *
 * <p><strong>Key Features Demonstrated:</strong>
 * <ul>
 *   <li>How to initialize a thread pool using {@link Executors#newFixedThreadPool(int)}</li>
 *   <li>Submitting tasks to an {@link ExecutorService}</li>
 *   <li>Understanding {@code shutdown()} behavior:
 *       <ul>
 *           <li>Allows already submitted tasks to finish execution.</li>
 *           <li>Prevents new tasks from being submitted.</li>
 *       </ul>
 *   </li>
 *   <li>Understanding {@code shutdownNow()} behavior:
 *       <ul>
 *           <li>Attempts to stop actively executing tasks by interrupting threads.</li>
 *           <li>Does not guarantee task termination if tasks are not interruptible.</li>
 *       </ul>
 *   </li>
 *   <li>Using {@code awaitTermination()} to wait for tasks to complete after shutdown.</li>
 *   <li>Behavior of interruptible vs. non-interruptible tasks during {@code shutdownNow}.</li>
 * </ul>
 *
 * <p><strong>Structure of the Examples:</strong>
 * <ol>
 *   <li><strong>First Example:</strong> Explicitly calls {@code shutdown()} and
 *       observing the effects on the thread pool and running tasks and task in the queue and finally calling {@code awaitTermination()}
 *       to observe waiting for all tasks to finish.
 *       </li>
 *   <li><strong>Second Example:</strong> Explicitly calls {@code shutdownNow()} where the running tasks are interruptable,
 *       observing the effects on the thread pool and running tasks and task in the queue.</li>
 *   <li><strong>Third Example:</strong> Explicitly calls {@code shutdownNow()} where the running tasks are non-interruptable,
 *       observing the effects on the thread pool and running tasks and task in the queue.</li>
 *   <li><strong>Final Example:</strong> Demonstrates task submission and automatic cleanup
 *       using the try-with-resources block.</li>
 * </ol>
 *
 * <p>Check {@link ThreadPoolTaskExecutorSample} for more information about pool properties like core pool size, queue capacity and max pool size.
 * @see ExecutorService#close()
 */

public class AwaitTerminationShutdownSample {

    private static final int WAIT_BETWEEN_CASES = 6_000;
    private static final int WORK_DURATION = 25_000;

    public static void main(String[] args) {
        var task = getTask(true);

        var pool = Executors.newFixedThreadPool(2);

        printThreadPool(pool, "Initial thread pool information");
        submitTasks(pool, task, 3, 12_000);
        pool.shutdown();
        printThreadPool(pool, "Thread pool after shutdown is called. Notice shutdown does not wait for the tasks to finish, also the task in the queue remains");
        sleep(WAIT_BETWEEN_CASES);
        executeTask(pool, task, 4);
        awaitTermination(pool);

        shutdownNowExample(2, true);
        shutdownNowExample(3, false);

        try (var pool2 = Executors.newFixedThreadPool(2)) {
            printThreadPool(pool2, "%nFourth Example. Initial thread pool information".formatted());
            submitTasks(pool2, task, 3, 10_000);
        } // Check ExecutorService.close() method to see how shutdown, awaitTermination and shutdownNow are called
        System.out.println("Note that since ExecutorService is AutoCloseable and close method calls for shutdown and then awaits for termination, this information is printed after all tasks submitted to the pool has finished.");

    }

    private static void shutdownNowExample(int id, boolean isInterruptable) {
        var task = getTask(isInterruptable);
        String message = isInterruptable ? "Thread pool after shutdownNow is called. The task in the queue is removed. Notice since shutdownNow can interrupt the running threads the running threads are also finished." :
                "Thread pool after shutdownNow is called. Notice since shutdownNow cannot interrupt the running threads, we still have active threads, but the task in the queue is removed.";
        ExecutorService pool;
        sleep(WAIT_BETWEEN_CASES);
        pool = Executors.newFixedThreadPool(2);
        printThreadPool(pool, "%n%dth example. Initial thread pool information".formatted(id));
        submitTasks(pool, task, 3, WORK_DURATION);
        pool.shutdownNow();
        printThreadPool(pool, message);
        sleep(WAIT_BETWEEN_CASES);
        executeTask(pool, task, 4);

        if (!isInterruptable) {
            awaitTermination(pool);
        }
    }

    private static void awaitTermination(ExecutorService pool) {
        try {
            var terminated = pool.awaitTermination(1, TimeUnit.MINUTES);
            System.out.println("Terminated: " + terminated);
            printThreadPool(pool, "Thread pool after awaiting termination");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void submitTasks(ExecutorService pool, BiConsumer<Integer, Integer> task, int count, int delay) {
        sleep(3_000);
        IntStream.range(0, count).forEach(i -> pool.execute(() -> task.accept(i, delay)));
        sleep(3_000);
        printThreadPool(pool, "%nThread pool after %d tasks have been submitted".formatted(count));
        sleep(WAIT_BETWEEN_CASES);
    }

    private static void executeTask(ExecutorService pool, BiConsumer<Integer, Integer> task, int id) {
        try {
            pool.execute(() -> task.accept(id, WORK_DURATION)); // This won't be executed, since shutdown is already called and will throw a RejectedExecutionException
        } catch (RuntimeException e) {
            System.out.printf("Task %d is rejected since shutdown is in progress. %s%n%n", id, e.getMessage());
        }
    }

    private static BiConsumer<Integer, Integer> getTask(boolean isInterruptable) {
        return (taskId, delay) -> {
            System.out.printf("Task %d  started. %s%n", taskId, getThreadInfo());
            doWork(isInterruptable, delay);
            System.out.printf("Task %d finished. %s%n", taskId, getThreadInfo());
        };
    }

    private static void doWork(boolean isInterruptable, int delay) {
        if (isInterruptable) {
            sleep(delay);
        } else {
            sleepNano(delay * 1_000_000L);
        }
    }
}
