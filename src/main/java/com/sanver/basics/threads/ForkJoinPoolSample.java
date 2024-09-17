package com.sanver.basics.threads;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

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
        var pool = ForkJoinPool.commonPool();
        Integer result = pool.invoke(new Fibonacci(4));
        System.out.printf("%nResult is %d%n%n", result);
        // new ForkJoinPool with a specific number of threads
        pool = new ForkJoinPool(3); // 3 threads
        var fibonacci5 = new Fibonacci(5);
        pool.submit(fibonacci5); // Another usage, which does not block the main thread. We can also use execute method.
        result = fibonacci5.join(); // The join here also returns the result without throwing UninterruptedException or ExecutionException like the get method does.
        System.out.printf("%nResult is %d%n", result);
    }

    static class Fibonacci extends RecursiveTask<Integer> { // This class needs to extend ForkJoinTask.
        // There are two implementations for ForkJoinTask, RecursiveAction and RecursiveTask.
        // RecursiveAction is for tasks that don't return a value, while RecursiveTask is for tasks that return value.
        private final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        protected Integer compute() {
            var thread = Thread.currentThread();
            System.out.printf("Calculating Fibonacci(%d) in ForkJoinWorkedThread: %s - %s Is Daemon: %s%n", n, thread.getId(), thread.getName(), thread.isDaemon());
            if (n <= 1) {//This if statement is the part where the divided job is executed.
                sleep(2000);
                return n;
            }

            // This part is responsible for job division
            var tasks = ForkJoinTask.invokeAll(List.of(new Fibonacci(n - 1), new Fibonacci(n - 2)));
            return tasks.stream().mapToInt(ForkJoinTask::join).sum();
        }
    }
}
