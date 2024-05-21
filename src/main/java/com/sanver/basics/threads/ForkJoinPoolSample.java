package com.sanver.basics.threads;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.sanver.basics.utils.Utils.sleep;

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
            System.out.printf("Calculating Fibonacci(%d) in ForkJoinWorkedThread: %s - %s%n", n, thread.getId(), thread.getName());
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
