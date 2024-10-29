package com.sanver.basics.threads;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

// Look also to RecursiveTaskSample and RecursiveActionSample

/**
 * Java 7 introduced the fork/join framework. It provides tools to help speed up parallel processing by attempting to use all available processor cores. It accomplishes this through a divide and conquer approach.
 * In practice, this means that the framework first “forks,” recursively breaking the task into smaller independent subtasks until they are simple enough to run asynchronously.
 * After that, the “join” part begins. The results of all subtasks are recursively joined into a single result. In the case of a task that returns void, the program simply waits until every subtask runs.
 * To provide effective parallel execution, the fork/join framework uses a pool of threads called the ForkJoinPool. This pool manages worker threads of type ForkJoinWorkerThread.
 * <br><br>
 * The ForkJoinPool is the heart of the framework. It is an implementation of the ExecutorService that manages worker threads and provides us with tools to get information about the thread pool state and performance.
 * Worker threads can execute only one task at a time, but the ForkJoinPool doesn’t create a separate thread for every single subtask. Instead, each thread in the pool has its own double-ended queue (or deque, pronounced “deck”) that stores tasks.
 * This architecture is vital for balancing the thread’s workload with the help of the work-stealing algorithm.
 * <br><br>
 *
 * <b>Work Stealing Algorithm</b>
 * <br>
 * Simply put, free threads try to “steal” work from deques of busy threads.
 * By default, a worker thread gets tasks from the head of its own deque. When it is empty, the thread takes a task from the tail of the deque of another busy thread or from the global entry queue since this is where the biggest pieces of work are likely to be located.
 * This approach minimizes the possibility that threads will compete for tasks. It also reduces the number of times the thread will have to go looking for work, as it works on the biggest available chunks of work first.
 * <br><br>
 * <b>How It Works</b>
 * <ol>
 * <li>	<b>Task Submission:</b> Tasks are submitted to the ForkJoinPool using methods like execute, submit, or invoke.</li>
 * <li>	<b>Work Queue:</b> Submitted tasks are placed into a work queue.</li>
 * <li>	<b>Worker Threads:</b> A set of worker threads actively monitor the work queue and pick up tasks to execute.</li>
 * <li>	<b>Forking and Joining:</b> Tasks can be split into smaller subtasks (forking) and executed independently. Once subtasks are completed, their results are combined (joining).</li>
 * <li>	<b>Work Stealing:</b> If a worker thread runs out of tasks, it can "steal" tasks from the queues of other busy threads, ensuring efficient utilization of resources.</li>
 * </ol>
 * <a href="https://www.baeldung.com/java-fork-join">Source</a>
 */
public class ForkJoinPoolSample {
    // Check https://www.baeldung.com/java-fork-join
    public static void main(String[] args) {
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.printf("Array: %39s%n", Arrays.toString(values));
        var pool = ForkJoinPool.commonPool(); // Returns the common pool instance. This pool is statically constructed; its run state is unaffected by attempts to shutdown or shutdownNow. The threads are deamon threads.
        // Notice main thread can be used with commonPool, but this is not the case for the new ForkJoinPool(3) instance.
        var result = pool.invoke(new ArraySum(values)); // This waits for the task to complete, thus blocking the main thread.
        System.out.println("Final result: " + result);

        System.out.printf("%n------------------------%n");
        System.out.printf("Array: %39s%n", Arrays.toString(values));
        pool = new ForkJoinPool(3); // 3 threads. The threads in this pool are daemon threads similar to ForkJoinPool.commonPool.
        var task = new ArraySum(values);
        pool.execute(task); // Another usage. This does not block the main thread. We can also use submit method.
        System.out.println("Final result: " + task.join()); // The join here also returns the result without throwing UninterruptedException or ExecutionException like the get method does.
//        var future = pool.submit(task);
//        System.out.println("Final result: " + future.join());
        // Since ForkJoinPool threads are daemon threads, main thread will exit, even when we don't call the pool.shutdown().
    }

    /**
     * <p>
     * This class needs to extend ForkJoinTask.
     * There are two implementations for ForkJoinTask, RecursiveAction and RecursiveTask.
     * RecursiveAction is for tasks that don't return a value, while RecursiveTask is for tasks that return value.
     * </p>
     */
    private static class ArraySum extends RecursiveTask<Integer> {
        private static final String FORMAT = "Thread id: %-3d Thread name: %-33s Is Daemon: %-5s";
        private final int[] array;

        ArraySum(final int[] array) {
            this.array = array;
        }

        private static void delay() {
            sleep(5_000);
        }

        @Override
        protected Integer compute() {
            int result = 0;
            if (array == null || array.length == 0) {
                return result;
            }

            if (array.length == 1) {

                System.out.printf("Calculating %34s. %s%n", Arrays.toString(array), getThreadInfo(FORMAT));
                delay();
                result = array[0];
                System.out.printf("Finished calculating %25s. %s Result: %2d%n", Arrays.toString(array), getThreadInfo(FORMAT), result);
                return array[0];
            }

            System.out.printf("Calculating %34s. %s%n", Arrays.toString(array), getThreadInfo(FORMAT));
            delay();
            int low = 0;
            int high = array.length;
            int mid = (low + high) >>> 1;
            var sum1 = new ArraySum(Arrays.copyOf(array, mid));
            var sum2 = new ArraySum(Arrays.copyOfRange(array, mid, high));
            var futures = ForkJoinTask.invokeAll(List.of(sum1, sum2));
            result = futures.stream().mapToInt(ForkJoinTask::join).sum();

//          Alternate way 1
//            sum1.fork();
//            sum2.fork();
//            result = sum1.join() + sum2.join();

//          Alternate way 2
//            var pool = getPool();
//            var future1 = pool.submit(sum1);
//            var future2 = pool.submit(sum2);
//            result = future1.join() + future2.join();

            delay();
            System.out.printf("Finished calculating %25s. %s Result: %2d%n", Arrays.toString(array), getThreadInfo(FORMAT), result);
            return result;
        }
    }
}
