package com.sanver.basics.utils;

import com.google.gson.Gson;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
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
     * Prints detailed information about a {@link Executor} to the console and additional information provided by the info parameter.
     * This method calls the relevant {@code printThreadPool()} overload.
     *
     * @param pool the {@link Executor} whose details are to be printed.
     *             Must be an instance of {@link ThreadPoolExecutor} or {@link ForkJoinPool}
     * @param info optional additional information that can be logged alongside
     *             the thread pool details.
     */
    public static void printThreadPool(Executor pool, String... info) {
        switch (pool) {
            case ThreadPoolExecutor t -> printThreadPool(t, info);
            case ForkJoinPool f -> printThreadPool(f, info);
            default -> System.out.printf("%s is not a ThreadPoolExecutor or a ForkJoinPool.%n", pool.getClass().getName());
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
        int remainingCapacity = queue.remainingCapacity(); // Space left in the queue or Integer.MAX_VALUE if the queue is ScheduledThreadPoolExecutor.DelayedWorkQueue
        int queueCapacity = queueSize > 0 && remainingCapacity == Integer.MAX_VALUE ? Integer.MAX_VALUE : queueSize + remainingCapacity; // Total capacity of the queue
        // The LinkedBlockingQueue used by ThreadPoolExecutor returns capacity - count for remainingCapacity() where capacity represents the capacity of the queue and count represents the number of elements in the queue.
        // However, for ScheduledThreadPoolExecutor, it uses DelayedWorkQueue, which always returns Integer.MAX_VALUE for the remainingCapacity() method implying there is no intrinsic limit for the queue to accept elements without blocking.

        System.out.printf("Pool size: %,d Active thread count: %,d Core pool size: %,d Maximum pool size: %,d Queue capacity: %,d Tasks in queue: %,d Largest pool size: %,d Keep alive time: %,ds%n%n",
                pool.getPoolSize(), pool.getActiveCount(), pool.getCorePoolSize(), pool.getMaximumPoolSize(), queueCapacity, queueSize, pool.getLargestPoolSize(), pool.getKeepAliveTime(TimeUnit.SECONDS));
    }

    public static void printThreadPool(ForkJoinPool pool, String... info) {
        displayInfo(info);

        System.out.printf("Pool size: %,d Active thread count: %,d Parallelism: %,d Tasks in queue: %,d%n%n",
                pool.getPoolSize(), pool.getActiveThreadCount(), pool.getParallelism(), pool.getQueuedTaskCount());
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

    public static void warmup() {
        IntStream.range(1, Runtime.getRuntime().availableProcessors() * 3).parallel().forEach(x -> sleepNano(300_000_000));
    }

    /**
     * Generates combination of numbers between 0 and limit for the given number of digits
     * e.g. generateCombinations(2,3) will result in [(0,1), (0,2), (0,3), (1,2), (1,3), (2,3)]
     *
     * @param digit represents the number of items in the permutation
     * @param limit represents the maximum value an item can get
     * @return the list of all permutations
     */
    public static List<List<Integer>> generateCombinations(int digit, int limit) {
        return generateVariations(digit, limit, false);
    }

    /**
     * Generates permutations of numbers between 0 and limit for the given number of digits
     * e.g. generatePermutations(2,1) will result in [(0,0), (0,1), (1,0), (1,1)]
     *
     * @param digit represents the number of items in the permutation
     * @param limit represents the maximum value an item can get
     * @return the list of all permutations
     */
    public static List<List<Integer>> generatePermutations(int digit, int limit) {
        return generateVariations(digit, limit, true);
    }

    private static List<List<Integer>> generateVariations(int digit, int limit, boolean orderMatters) {
        Integer[] combination = new Integer[digit];
        Arrays.parallelSetAll(combination, x -> -1);
        var result = new ArrayList<List<Integer>>();

        int currentDigit = 0;

        while (currentDigit >= 0) {
            while (fill(combination, currentDigit, limit, orderMatters)) {
                result.add(List.of(combination));
                if (currentDigit < digit - 1) {
                    currentDigit = digit - 1;
                }
            }
            currentDigit--;
        }

        return result;
    }

    private static boolean fill(Integer[] combination, int digit, int limit, boolean orderMatters) {
        int length = combination.length;

        if (orderMatters) {
            if (combination[digit] + 1 > limit) {
                return false;
            }
        } else {
            if (combination[digit] + length - digit > limit) {
                return false;
            }
        }

        combination[digit]++;
        if (orderMatters) {
            IntStream.range(digit + 1, length).forEach(i -> combination[i] = 0);
        } else {
            IntStream.range(digit + 1, length).forEach(i -> combination[i] = combination[digit] + i - digit);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T object) {
        var gson = new Gson();
        return (T) gson.fromJson(gson.toJson(object), object.getClass());
    }
}
