package com.sanver.basics.threads.executors;

import org.modelmapper.internal.util.Assert;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.text.NumberFormat;
import java.util.concurrent.CountDownLatch;

import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

public class ThreadPoolTaskExecutorPropertiesSample {

    private static final int NUMBER_OF_OPERATIONS = 10;
    private static final int CORE_POOL_SIZE = 3;
    private static final int QUEUE_CAPACITY = 4;
    private static final int MAX_POOL_SIZE = 5;
    private static final NumberFormat numberFormat = NumberFormat.getInstance();

    public static void main(String[] args) {
        // ThreadPoolTaskExecutor is a java bean that allows for configuring a ThreadPoolExecutor in a bean style
        // by setting up the values for the instance variables like
        // corePoolSize, maxPoolSize, keepAliveSeconds, queueCapacity and exposing it as a Spring TaskExecutor.
        // The default configuration of core pool size is 1, max pool size and queue capacity as 2147483647.
        // This is roughly equivalent to Executors.newSingleThreadExecutor(), sharing a single thread for all tasks.

        // The corePoolSize is the minimum number of workers to keep alive without timing out
        // The pool size will exceed the corePoolSize only if the number of items trying to be executed at the same time
        // exceeds the queueCapacity.

        // Setting queueCapacity to 0 mimics Executors.newCachedThreadPool(), with immediate scaling of threads in
        // the pool to a very high number.

        // Effectively, to set a fixed pool size, set the corePoolSize and maxPoolSize to same value.
        // Note, queueCapacity must be greater than corePoolSize.
        // Also, maxPoolSize must be greater than or equal to corePoolSize

        corePoolSizeSetLargerThanOperationCountWithUnboundMaxPoolSizeAndUnboundQueueCapacity();
        corePoolSizeSetWithUnboundMaxPoolSizeAndUnboundQueueCapacity();
        corePoolSizeSetWithUnboundMaxPoolSizeAndBoundQueueCapacity();
        corePoolSizeSetWithUnboundMaxPoolSizeAndBoundQueueCapacityAndOperationCountSameAsQueueCapacity();
        corePoolSizeSetWithBoundMaxPoolSizeAndBoundQueueCapacity();
        corePoolSizeSetWithBoundMaxPoolSizeAndUnboundQueueCapacity();
        corePoolSizeNotSetWithBoundMaxPoolSizeAndUnboundQueueCapacity();
        fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize();
    }

    private static void corePoolSizeSetLargerThanOperationCountWithUnboundMaxPoolSizeAndUnboundQueueCapacity() {
        System.out.printf("%nRunning  corePoolSizeSetLargerThanOperationCountWithUnboundMaxPoolSizeAndUnboundQueueCapacity%n");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = Integer.MAX_VALUE;
        var queueCapacity = Integer.MAX_VALUE;
        var operationCount = CORE_POOL_SIZE - 1;
        var expectedPoolSize = getExpectedPoolSize(corePoolSize, maxPoolSize, queueCapacity, operationCount); // This is equal to corePoolSize
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        // This will set maxPoolSize and queueCapacity to unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        assertPoolSizeAndShutDownWhenFinished(countDownLatch, executor, expectedPoolSize);
        System.out.printf("%nFinished corePoolSizeSetLargerThanOperationCountWithUnboundMaxPoolSizeAndUnboundQueueCapacity%n");
    }

    private static void corePoolSizeSetWithUnboundMaxPoolSizeAndUnboundQueueCapacity() {
        System.out.printf("%nRunning  corePoolSizeSetWithUnboundMaxPoolSizeAndUnboundQueueCapacity%n");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = Integer.MAX_VALUE;
        var queueCapacity = Integer.MAX_VALUE;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = getExpectedPoolSize(corePoolSize, maxPoolSize, queueCapacity, operationCount); // This is equal to corePoolSize
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        // This will set maxPoolSize and queueCapacity to unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        assertPoolSizeAndShutDownWhenFinished(countDownLatch, executor, expectedPoolSize);
        System.out.println("Finished corePoolSizeSetWithUnboundMaxPoolSizeAndUnboundQueueCapacity");
    }

    private static void corePoolSizeSetWithUnboundMaxPoolSizeAndBoundQueueCapacity() {
        System.out.printf("%nRunning  corePoolSizeSetWithUnboundMaxPoolSizeAndBoundQueueCapacity%n");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = Integer.MAX_VALUE;
        var queueCapacity = QUEUE_CAPACITY;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = getExpectedPoolSize(corePoolSize, maxPoolSize, queueCapacity, operationCount);
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);

        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        // This will set maxPoolSize as unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        assertPoolSizeAndShutDownWhenFinished(countDownLatch, executor, expectedPoolSize);
        System.out.printf("%nFinished corePoolSizeSetWithUnboundMaxPoolSizeAndBoundQueueCapacity%n");
    }

    private static void corePoolSizeSetWithUnboundMaxPoolSizeAndBoundQueueCapacityAndOperationCountSameAsQueueCapacity() {
        System.out.printf("%nRunning  corePoolSizeSetWithUnboundMaxPoolSizeAndBoundQueueCapacityAndOperationCountSameAsQueueCapacity%n");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = Integer.MAX_VALUE;
        var queueCapacity = QUEUE_CAPACITY;
        var operationCount = QUEUE_CAPACITY;
        var expectedPoolSize = getExpectedPoolSize(corePoolSize, maxPoolSize, queueCapacity, operationCount); // This is equal to corePoolSize
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        // This will set maxPoolSize as unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        assertPoolSizeAndShutDownWhenFinished(countDownLatch, executor, expectedPoolSize);
        System.out.println("Finished corePoolSizeSetWithUnboundMaxPoolSizeAndBoundQueueCapacityAndOperationCountSameAsQueueCapacity");
    }

    private static void corePoolSizeSetWithBoundMaxPoolSizeAndBoundQueueCapacity() {
        System.out.printf("%nRunning  corePoolSizeSetWithBoundMaxPoolSizeAndBoundQueueCapacity%n");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = MAX_POOL_SIZE;
        var queueCapacity = QUEUE_CAPACITY;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = getExpectedPoolSize(corePoolSize, maxPoolSize, queueCapacity, operationCount); // Since operationCount - queueCapacity is larger than maxPoolSize, it will result in a runtime error "did not accept task", because the queue cannot hold all the operations that are not in the pool. One task won't be executed, but others will.
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setMaxPoolSize(maxPoolSize);
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);
        executeThreads(executor, countDownLatch, operationCount);

        while (countDownLatch.getCount() > 1) { // Since one task won't be executed, because the queue capacity and max pool size don't allow it
            Assert.isTrue(executor.getPoolSize() == expectedPoolSize);
        }

        printThreadPool(executor, "Final thread pool information");
        executor.shutdown();
        System.out.printf("%nFinished corePoolSizeSetWithBoundMaxPoolSizeAndBoundQueueCapacity%n");
    }

    private static void corePoolSizeSetWithBoundMaxPoolSizeAndUnboundQueueCapacity() {
        System.out.printf("%nRunning  corePoolSizeSetWithBoundMaxPoolSizeAndUnboundQueueCapacity%n");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = MAX_POOL_SIZE;
        var queueCapacity = Integer.MAX_VALUE;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = getExpectedPoolSize(corePoolSize, maxPoolSize, queueCapacity, operationCount); // This is equal to corePoolSize
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        // This will set queueCapacity as unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        assertPoolSizeAndShutDownWhenFinished(countDownLatch, executor, expectedPoolSize);
        System.out.printf("%nFinished corePoolSizeSetWithBoundMaxPoolSizeAndUnboundQueueCapacity%n");
    }

    private static void corePoolSizeNotSetWithBoundMaxPoolSizeAndUnboundQueueCapacity() {
        System.out.printf("%nRunning  corePoolSizeNotSetWithBoundMaxPoolSizeAndUnboundQueueCapacity%n");
        var corePoolSize = 1;
        var maxPoolSize = MAX_POOL_SIZE;
        var queueCapacity = Integer.MAX_VALUE;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = getExpectedPoolSize(corePoolSize, maxPoolSize, queueCapacity, operationCount);
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);
        var executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        // This will set queueCapacity as unbound and core pool size to 1
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        assertPoolSizeAndShutDownWhenFinished(countDownLatch, executor, expectedPoolSize);
        System.out.printf("%nFinished corePoolSizeNotSetWithBoundMaxPoolSizeAndUnboundQueueCapacity%n");
    }

    private static void fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize() {
        System.out.printf("%nRunning  fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize%n");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = CORE_POOL_SIZE;
        var queueCapacity = Integer.MAX_VALUE;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = getExpectedPoolSize(corePoolSize, maxPoolSize, queueCapacity, operationCount);
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);

        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        // This will set queueCapacity as unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        assertPoolSizeAndShutDownWhenFinished(countDownLatch, executor, expectedPoolSize);
        System.out.printf("%nFinished fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize%n");
    }

    public static void executeThreads(ThreadPoolTaskExecutor executor,
                                      CountDownLatch countDownLatch,
                                      int numberOfOperations) {
        printThreadPool(executor, "Initial thread pool information");

        for (int i = 0; i < numberOfOperations; i++) {
            try {
                executor.execute(getRunnable(i + 1, countDownLatch));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static Runnable getRunnable(int i, CountDownLatch countDownLatch) {
        return () -> {
            System.out.printf("Running process %d%n", i);
            sleep(3000);
            System.out.printf("Process %d finished%n", i);
            countDownLatch.countDown();
        };
    }

    private static void printQueue(long corePoolSize, long maxPoolSize, long queueCapacity, long operationCount, long expectedPoolSize) {
        System.out.printf("Core pool size  is      : %s%n", numberFormat.format(corePoolSize));
        System.out.printf("Max  pool size  is      : %s%n", numberFormat.format(maxPoolSize));
        System.out.printf("Queue capacity  is      : %s%n", numberFormat.format(queueCapacity));
        System.out.printf("Operation count is      : %s%n", numberFormat.format(operationCount));
        System.out.printf("Pool size will reach to : %s%n", numberFormat.format(expectedPoolSize));
    }

    private static long getExpectedPoolSize(long corePoolSize, long maxPoolSize, long queueCapacity, long operationCount) {
        return Math.min(maxPoolSize, Math.min(operationCount, corePoolSize + Math.max(0, operationCount - corePoolSize - queueCapacity)) );
    }

    private static void assertPoolSizeAndShutDownWhenFinished(CountDownLatch countDownLatch, ThreadPoolTaskExecutor executor, long expectedPoolSize) {
        while (countDownLatch.getCount() > 0) {
            Assert.isTrue(executor.getPoolSize() == expectedPoolSize);
        }

        printThreadPool(executor, "Final thread pool information");
        executor.shutdown();
    }
}