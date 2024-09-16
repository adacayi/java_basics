package com.sanver.basics.threads.executors;

import org.modelmapper.internal.util.Assert;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;
import static com.sanver.basics.utils.Utils.sleep;

public class ThreadPoolTaskExecutorPropertiesSample {

    private static final int NUMBER_OF_OPERATIONS = 10;
    private static final int CORE_POOL_SIZE = 3;
    private static final int QUEUE_CAPACITY = 4;
    private static final int MAX_POOL_SIZE = 5;

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

        corePoolSizeWithUnboundMaxPoolSizeAndUnboundQueueCapacity();
        corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacity();
        corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacityAndOperationCountSameAsQueueCapacity();
        corePoolSizeWithBoundMaxPoolSizeAndBoundQueueCapacity();
        corePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity();
        noCorePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity();
        fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize();
    }

    private static void corePoolSizeWithUnboundMaxPoolSizeAndUnboundQueueCapacity() {
        System.out.println("Running  corePoolSizeWithUnboundMaxPoolSizeAndUnboundQueueCapacity");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = Integer.MAX_VALUE;
        var queueCapacity = Integer.MAX_VALUE;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = Math.min(Math.max(operationCount - queueCapacity, corePoolSize), maxPoolSize); // This is equal to corePoolSize
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        // This will set maxPoolSize and queueCapacity to unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        while (countDownLatch.getCount() > 0) {
            Assert.isTrue(executor.getPoolSize() == expectedPoolSize);
        }
        System.out.println("Finished corePoolSizeWithUnboundMaxPoolSizeAndUnboundQueueCapacity");
    }

    private static void corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacity() {
        System.out.println("Running  corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacity");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = Integer.MAX_VALUE;
        var queueCapacity = QUEUE_CAPACITY;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = Math.min(Math.max(operationCount - queueCapacity, corePoolSize), maxPoolSize);
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);

        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        // This will set maxPoolSize as unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        while (countDownLatch.getCount() > 0) {
            Assert.isTrue(executor.getPoolSize() == expectedPoolSize);
        }
        System.out.println("Finished corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacity");
    }

    private static void corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacityAndOperationCountSameAsQueueCapacity() {
        System.out.println("Running  corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacityAndOperationCountSameAsQueueCapacity");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = Integer.MAX_VALUE;
        var queueCapacity = QUEUE_CAPACITY;
        var operationCount = QUEUE_CAPACITY;
        var expectedPoolSize = Math.min(Math.max(operationCount - queueCapacity, corePoolSize), maxPoolSize); // This is equal to corePoolSize
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        // This will set maxPoolSize as unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        while (countDownLatch.getCount() > 0) {
            Assert.isTrue(executor.getPoolSize() == expectedPoolSize);
        }
        System.out.println("Finished corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacityAndOperationCountSameAsQueueCapacity");
    }

    private static void corePoolSizeWithBoundMaxPoolSizeAndBoundQueueCapacity() {
        System.out.println("Running  corePoolSizeWithBoundMaxPoolSizeAndBoundQueueCapacity");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = MAX_POOL_SIZE;
        var queueCapacity = QUEUE_CAPACITY;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = Math.min(Math.max(operationCount - queueCapacity, corePoolSize), maxPoolSize); // Since Math.max(operationCount - queueCapacity, corePoolSize) is larger than maxPoolSize, it will result in a runtime error "did not accept task", because the queue cannot hold all the operations that are not in the pool. One task won't be executed, but others will.
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
        System.out.println("Finished corePoolSizeWithBoundMaxPoolSizeAndBoundQueueCapacity");
    }

    private static void corePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity() {
        System.out.println("Running  corePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = MAX_POOL_SIZE;
        var queueCapacity = Integer.MAX_VALUE;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = Math.min(Math.max(operationCount - queueCapacity, corePoolSize), maxPoolSize); // This is equal to corePoolSize
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        // This will set queueCapacity as unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        while (countDownLatch.getCount() > 0) {
            Assert.isTrue(executor.getPoolSize() == expectedPoolSize);
        }
        System.out.println("Finished corePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity");
    }

    private static void noCorePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity() {
        System.out.println("Running  noCorePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity");
        var corePoolSize = 1;
        var maxPoolSize = MAX_POOL_SIZE;
        var queueCapacity = Integer.MAX_VALUE;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = Math.min(Math.max(operationCount - queueCapacity, corePoolSize), maxPoolSize);
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);
        var executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        // This will set queueCapacity as unbound and core pool size to 1
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        while (countDownLatch.getCount() > 0) {
            Assert.isTrue(executor.getPoolSize() == expectedPoolSize);
        }
        System.out.println("Finished noCorePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity");
    }

    private static void fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize() {
        System.out.println("Running  fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize");
        var corePoolSize = CORE_POOL_SIZE;
        var maxPoolSize = CORE_POOL_SIZE;
        var queueCapacity = Integer.MAX_VALUE;
        var operationCount = NUMBER_OF_OPERATIONS;
        var expectedPoolSize = Math.min(Math.max(operationCount - queueCapacity, corePoolSize), maxPoolSize);
        printQueue(corePoolSize, maxPoolSize, queueCapacity, operationCount, expectedPoolSize);

        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        // This will set queueCapacity as unbound
        executor.initialize();

        var countDownLatch = new CountDownLatch(operationCount);

        executeThreads(executor, countDownLatch, operationCount);
        while (countDownLatch.getCount() > 0) {
            Assert.isTrue(executor.getPoolSize() == expectedPoolSize);
        }
        System.out.println("Finished fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize");
    }

    public static void executeThreads(ThreadPoolTaskExecutor executor,
                                      CountDownLatch countDownLatch,
                                      int numberOfOperations) {
        for (int i = 0; i < numberOfOperations; i++) {
            try {
                executor.execute(getRunnable(i + 1, countDownLatch));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        shutdownExecutor(executor, countDownLatch);
    }

    public static void shutdownExecutor(ThreadPoolTaskExecutor executor, CountDownLatch countDownLatch) {
        CompletableFuture.runAsync(() -> {
            uncheck(() -> countDownLatch.await());
            executor.shutdown();
        });
    }

    public static Runnable getRunnable(int i, CountDownLatch countDownLatch) {
        return () -> {
            System.out.printf("Running process %d%n", i);
            sleep(3000);
            System.out.printf("Process %d finished%n", i);
            countDownLatch.countDown();
        };
    }

    private static void printQueue(int corePoolSize, int maxPoolSize, int queueCapacity, int operationCount, int expectedPoolSize) {
        System.out.println("Core pool size is " + corePoolSize);
        System.out.println("Max pool size is " + maxPoolSize);
        System.out.println("Queue capacity is " + queueCapacity);
        System.out.println("Operation count is " + operationCount);
        System.out.println("Pool size is " + expectedPoolSize);
    }
}