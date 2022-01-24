package com.sanver.basics.threads.executors;

import static com.sanver.basics.utils.Utils.sleep;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class ThreadPoolTaskExecutorSample {

  public static void main(String[] args) {
    // ThreadPoolTaskExecutor is a java bean that allows for configuring a ThreadPoolExecutor in a bean style
    // by setting up the values for the instance variables like
    // corePoolSize, maxPoolSize, keepAliveSeconds, queueCapacity and exposing it as a Spring TaskExecutor.
    var executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(2);
    executor.initialize();

    for (int i = 0; i < 5; i++) {
      executor.execute(getRunnable(i));
    }
  }

  public static Runnable getRunnable(int i) {
    return () -> {
      System.out.println("Running process " + i);
      sleep(3000);
      System.out.printf("Process %s finished\n", i);
    };
  }
}
