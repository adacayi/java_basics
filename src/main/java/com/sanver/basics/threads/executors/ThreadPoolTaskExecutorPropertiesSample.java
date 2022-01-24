package com.sanver.basics.threads.executors;

import static com.sanver.basics.utils.Utils.sleep;

import java.util.concurrent.CountDownLatch;
import org.modelmapper.internal.util.Assert;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadPoolTaskExecutorPropertiesSample {

  private static final int NUMBER_OF_OPERATIONS = 10;
  private static final int CORE_POOL_SIZE = 3;
  private static final int QUEUE_CAPACITY = 5;
  private static final int MAX_POOL_SIZE = 6;

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
    corePoolSizeWithBoundMaxPoolSizeAndBoundQueueCapacity();
    corePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity();
    noCorePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity();
    fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize();
  }

  private static void corePoolSizeWithUnboundMaxPoolSizeAndUnboundQueueCapacity() {
    System.out.println("Running corePoolSizeWithUnboundMaxPoolSizeAndUnboundQueueCapacity");
    var executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(CORE_POOL_SIZE);
    // This will set maxPoolSize and queueCapacity to unbound
    executor.initialize();

    var countDownLatch = new CountDownLatch(NUMBER_OF_OPERATIONS);

    executeThreads(executor, countDownLatch);
    while (countDownLatch.getCount() > 0) {
      Assert.isTrue(executor.getPoolSize() == CORE_POOL_SIZE);
    }
    System.out.println("Finished corePoolSizeWithUnboundMaxPoolSizeAndUnboundQueueCapacity");
  }

  private static void corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacity() {
    System.out.println("Running corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacity");
    var executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(CORE_POOL_SIZE);
    executor.setQueueCapacity(QUEUE_CAPACITY);
    // This will set maxPoolSize as unbound
    executor.initialize();

    var countDownLatch = new CountDownLatch(NUMBER_OF_OPERATIONS);

    executeThreads(executor, countDownLatch);
    while (countDownLatch.getCount() > 0) {
      Assert.isTrue(executor.getPoolSize() == QUEUE_CAPACITY);
    }
    System.out.println("Finished corePoolSizeWithUnboundMaxPoolSizeAndBoundQueueCapacity");
  }

  private static void corePoolSizeWithBoundMaxPoolSizeAndBoundQueueCapacity() {
    System.out.println("Running corePoolSizeWithBoundMaxPoolSizeAndBoundQueueCapacity");
    var executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(CORE_POOL_SIZE);
    executor.setQueueCapacity(QUEUE_CAPACITY);
    executor.setMaxPoolSize(MAX_POOL_SIZE);
    executor.initialize();

    var countDownLatch = new CountDownLatch(NUMBER_OF_OPERATIONS);

    executeThreads(executor, countDownLatch);
    while (countDownLatch.getCount() > 0) {
      Assert.isTrue(executor.getPoolSize() == QUEUE_CAPACITY);
    }
    System.out.println("Finished corePoolSizeWithBoundMaxPoolSizeAndBoundQueueCapacity");
  }

  private static void corePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity() {
    System.out.println("Running corePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity");
    var executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(CORE_POOL_SIZE);
    executor.setMaxPoolSize(MAX_POOL_SIZE);
    // This will set queueCapacity as unbound
    executor.initialize();

    var countDownLatch = new CountDownLatch(NUMBER_OF_OPERATIONS);

    executeThreads(executor, countDownLatch);
    while (countDownLatch.getCount() > 0) {
      Assert.isTrue(executor.getPoolSize() == CORE_POOL_SIZE);
    }
    System.out.println("Finished corePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity");
  }

  private static void noCorePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity() {
    System.out.println("Running noCorePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity");
    var executor = new ThreadPoolTaskExecutor();
    executor.setMaxPoolSize(MAX_POOL_SIZE);
    // This will set queueCapacity as unbound and core pool size to 1
    executor.initialize();

    var countDownLatch = new CountDownLatch(NUMBER_OF_OPERATIONS);

    executeThreads(executor, countDownLatch);
    while (countDownLatch.getCount() > 0) {
      Assert.isTrue(executor.getPoolSize() == 1);
    }
    System.out.println("Finished noCorePoolSizeWithBoundMaxPoolSizeAndUnboundQueueCapacity");
  }

  private static void fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize() {
    System.out.println("Running fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize");
    var executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(CORE_POOL_SIZE);
    executor.setMaxPoolSize(CORE_POOL_SIZE);
    // This will set queueCapacity as unbound
    executor.initialize();

    var countDownLatch = new CountDownLatch(NUMBER_OF_OPERATIONS);

    executeThreads(executor, countDownLatch);
    while (countDownLatch.getCount() > 0) {
      Assert.isTrue(executor.getPoolSize() == CORE_POOL_SIZE);
    }
    System.out.println("Finished fixedPoolSizeWithCorePoolSizeEqualsMaxPoolSize");
  }

  public static void executeThreads(ThreadPoolTaskExecutor executor, CountDownLatch countDownLatch) {
    for (int i = 0; i < NUMBER_OF_OPERATIONS; i++) {
      executor.execute(getRunnable(i + 1, countDownLatch));
    }
  }

  public static Runnable getRunnable(int i, CountDownLatch countDownLatch) {
    return () -> {
      System.out.println("Running process " + i);
      sleep(1000);
      System.out.printf("Process %s finished\n", i);
      countDownLatch.countDown();
    };
  }
}