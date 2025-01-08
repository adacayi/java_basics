package com.sanver.basics.utils;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;


public class Utils {
    private static final Random random = new Random();

    private Utils() {
    }

    public static void sleep(long millis) {
        uncheck(() -> Thread.sleep(millis));
    }

    public static void sleepNano(long nanos) {
        var start = System.nanoTime();
        while (System.nanoTime() - start < nanos) ;
    }

    /**
     * @return The current thread's thread id, thread name and is daemon information
     */
    public static String getThreadInfo() {
        return getThreadInfo("Thread id: %-3d Thread name: %-4s Is Daemon: %-5s Is Virtual: %-5s");
    }

    /**
     * Returns the current thread information containing the thread id, thread name and is daemon information based on the given format
     *
     * @param format Format to output thread information. e.g. "Thread id: %-3d Thread name: %-33s Is Daemon: %-5s"
     * @return Thread id, thread name and is daemon information
     */
    public static String getThreadInfo(String format) {
        var thread = Thread.currentThread();
        return format.formatted(thread.threadId(), thread.getName(), thread.isDaemon(), thread.isVirtual());
    }

    /**
     * @return The current thread's id, converted to int from long for convenience
     */
    public static int threadId() {
        return (int) Thread.currentThread().threadId();
    }

    public static void printCurrentThread(String... info) {
        displayInfo(info);
        System.out.printf("%s%n%n", getThreadInfo());
    }

    public static void printForkJoinPool(ForkJoinPool pool, String... info) {
        displayInfo(info);

        System.out.printf("Active threads in the fork join pool: %d%n", pool.getActiveThreadCount());
        System.out.printf("The number of threads in the fork join pool: %d%n%n", pool.getPoolSize());
    }

    /**
     * Prints detailed information about a {@link ThreadPoolTaskExecutor} to the console and additional information provided by the info parameter.
     * This method calls {@link #printThreadPool(ThreadPoolExecutor, String...)}
     *
     * @param pool the {@link ThreadPoolTaskExecutor} whose details are to be printed.
     *             Must not be null.
     * @param info optional additional information that can be logged alongside
     *             the thread pool details.
     */
    public static void printThreadPool(ThreadPoolTaskExecutor pool, String... info) {
        printThreadPool(pool.getThreadPoolExecutor(), info);
    }

    /**
     * Prints detailed information about a {@link ExecutorService} to the console and additional information provided by the info parameter.
     * This method calls {@link #printThreadPool(ThreadPoolExecutor, String...)}

     * @param pool the {@link ExecutorService} whose details are to be printed.
     *      *             Must be an instance of {@link ThreadPoolExecutor}
     * @param info optional additional information that can be logged alongside
     *             the thread pool details.
     */
    public static void printThreadPool(ExecutorService pool, String... info) {
        if (pool instanceof ThreadPoolExecutor threadPoolExecutor) {
            printThreadPool(threadPoolExecutor, info);
        } else {
            System.out.printf("%s is not a ThreadPoolExecutor.%n", pool.getClass().getName());
        }
    }

    /**
     * Prints detailed information about a {@link ThreadPoolExecutor} to the console and additional information provided by the info parameter.
     * The method displays metrics such as the current pool size, active thread count,
     * core pool size, largest pool size, maximum pool size, and the keep-alive time.
     * Largest pool size is the largest size the pool ever got to.
     *
     * @param pool the {@link ThreadPoolExecutor} whose details are to be printed.
     *             Must not be null.
     * @param info optional additional information that can be logged alongside
     *             the thread pool details.
     *
     * <p>
     * Example Output:
     * <pre>
     * Pool size: 5
     * Active thread count: 3
     * Core pool size: 4
     * Queue capacity: 1
     * Tasks in queue: 1
     * Maximum pool size: 8
     * Largest pool size: 6
     * Keep alive time: 60s
     * </pre>
     * </p>
     *
     * <p><strong>Note:</strong> The method writes directly to {@code System.out}.</p>
     *
     * @see java.util.concurrent.ThreadPoolExecutor
     * @see java.util.concurrent.TimeUnit
     */
    public static void printThreadPool(ThreadPoolExecutor pool, String... info) {
        displayInfo(info);
        BlockingQueue<Runnable> queue = pool.getQueue();

        int queueSize = queue.size();  // Current number of tasks in the queue
        int remainingCapacity = queue.remainingCapacity(); // Space left in the queue
        int queueCapacity = queueSize + remainingCapacity; // Total capacity of the queue

        System.out.printf("Pool size: %,d Active thread count: %,d Core pool size: %,d Queue capacity: %,d Tasks in queue: %,d Maximum pool size: %,d Largest pool size: %,d Keep alive time: %,ds%n%n",
                pool.getPoolSize(), pool.getActiveCount(), pool.getCorePoolSize(), queueCapacity, queueSize, pool.getMaximumPoolSize(), pool.getLargestPoolSize(), pool.getKeepAliveTime(TimeUnit.SECONDS));
    }

    private static void displayInfo(String... info) {
        if (info.length == 0) {
            return;
        }

        for (var infoItem : info) {
            System.out.println(infoItem);
        }
        var max = Arrays.stream(info).map(String::length).mapToInt(x -> x).max().orElse(0);
        System.out.println(IntStream.range(0, max).mapToObj(x -> "-").collect(Collectors.joining()));
    }

    public static int getRandomInt(int a, int b) {
        return random.nextInt(a, b + 1);
    }
}
