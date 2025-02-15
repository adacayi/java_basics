package com.sanver.basics.threads.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;

/**
 * Demonstrates the usage of Executors.newWorkStealingPool() in Java.
 *
 * <p>
 * Executors.newWorkStealingPool() is a factory method that returns a {@link ForkJoinPool}
 * with a parallelism level equal to the number of available processors.
 * This pool employs a work-stealing algorithm, where idle threads can steal tasks
 * from busy threads, enhancing overall throughput.
 * </p>
 *
 * <h2>Key Features:</h2>
 * <ul>
 *     <li>Work-stealing mechanism for efficient CPU usage.</li>
 *     <li>Ideal for parallel streams and tasks with uneven workloads.</li>
 *     <li>Parallelism level defaults to the number of available processors.</li>
 * </ul>
 *
 * <h2>Common Methods:</h2>
 * <ul>
 *     <li>{@link ExecutorService#submit} - Submits a Callable/Runnable task for execution and returns a Future.</li>
 *     <li>{@link ExecutorService#execute(Runnable)} - Executes a Runnable task asynchronously.</li>
 *     <li>{@link ExecutorService#shutdown()} - Initiates an orderly shutdown.</li>
 *     <li>{@link ExecutorService#awaitTermination(long, TimeUnit)} - Blocks until all tasks complete.</li>
 * </ul>
 *
 * <h2>Differences between ForkJoinPool and Executors.newWorkStealingPool()</h2>
 * <p>
 * <h2>1. Initialization and Type</h2>
 * <ul>
 *   <li><b>ForkJoinPool(28)</b>:
 *     <ul>
 *       <li>Directly creates an instance of {@link ForkJoinPool} with a specified parallelism level.</li>
 *       <li>Provides full control over the pool’s configuration, including custom {@link ForkJoinWorkerThreadFactory} and {@link Thread.UncaughtExceptionHandler}.</li>
 *     </ul>
 *   </li>
 *   <li><b>Executors.newWorkStealingPool(28)</b>:
 *     <ul>
 *       <li>Creates a {@link ForkJoinPool} indirectly using the {@link Executors} factory method.</li>
 *       <li>Uses default thread factory and settings provided by {@link Executors}.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>2. Purpose and Usage</h2>
 * <ul>
 *   <li><b>ForkJoinPool</b>:
 *     <ul>
 *       <li>Designed for {@link ForkJoinTask}, such as {@link java.util.concurrent.RecursiveTask} or {@link java.util.concurrent.RecursiveAction}.</li>
 *       <li>Offers fine-grained control over pool behavior.</li>
 *     </ul>
 *   </li>
 *   <li><b>Executors.newWorkStealingPool</b>:
 *     <ul>
 *       <li>Provides a convenient factory method for creating a work-stealing pool.</li>
 *       <li>Suitable for general parallel tasks with minimal configuration.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>3. Work-Stealing Behavior</h2>
 * <ul>
 *   <li>Both use the work-stealing algorithm, but:</li>
 *   <li><b>ForkJoinPool</b>: Configurable work-stealing behavior.</li>
 *   <li><b>Executors.newWorkStealingPool</b>: Always uses default work-stealing settings.</li>
 * </ul>
 *
 * <h2>4. Default Configurations</h2>
 * <ul>
 *   <li><b>ForkJoinPool</b>: Customizable settings (e.g., thread factory, async mode).</li>
 *   <li><b>Executors.newWorkStealingPool</b>: Uses default configurations suitable for most tasks.</li>
 * </ul>
 *
 * <h2>5. Thread Management</h2>
 * <ul>
 *   <li><b>ForkJoinPool</b>:
 *     <ul>
 *       <li>Allows specifying custom thread factories, handlers, and scheduling.</li>
 *     </ul>
 *   </li>
 *   <li><b>Executors.newWorkStealingPool</b>:
 *     <ul>
 *       <li>Uses default {@link ForkJoinWorkerThreadFactory} and {@link Thread.UncaughtExceptionHandler}.</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * <h2>Conclusion</h2>
 * <ul>
 *   <li>Use <b>ForkJoinPool</b> directly for precise control and {@link ForkJoinTask} management.</li>
 *   <li>Use <b>Executors.newWorkStealingPool</b> for quick, default-configured parallel task execution.</li>
 * </ul>
 */
public class WorkStealingPoolSample {
    public static void main(String[] args) {
        var vault = new Vault<Integer>();
        int count = 10_000_000;

        try(var executor = Executors.newWorkStealingPool(28)) { // Try Executors.newFixedThreadPool(28) to see the performance difference
            measure(() -> {
                IntStream.range(0, count).parallel().forEach(i -> executor.execute(() -> vault.get(0)));
                IntStream.range(0, count).parallel().forEach(i-> executor.execute(()->vault.add(i)));
            });
        }
        System.out.printf("Vault size: %,d%n", vault.size());
    }

    static class Vault<T> {
        private final List<T> values;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        public Vault() {
            this.values = new ArrayList<>();
        }

        public Vault(List<T> values) {
            this.values = values;
        }

        public boolean add(T value) {
            lock.writeLock().lock();
            try {
                return values.add(value);
            } finally {
                lock.writeLock().unlock();
            }
        }

        public T get(int i) {
            lock.readLock().lock();
            try {
                if (values.isEmpty()) {
                    return null;
                }

                return values.get(i);
            } finally {
                lock.readLock().unlock();
            }
        }

        public int size() {
            return values.size();
        }
    }
}
