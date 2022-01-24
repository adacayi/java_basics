package com.sanver.basics.threads.executors;

import static com.sanver.basics.utils.Utils.sleep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
public class ThreadPoolTaskExecutorSample {

  public static void main(String[] args) {
    // ThreadPoolTaskExecutor is a java bean that allows for configuring a ThreadPoolExecutor in a bean style
    // by setting up the values for the instance variables like
    // corePoolSize, maxPoolSize, keepAliveSeconds, queueCapacity and exposing it as a Spring TaskExecutor.
    var executor = new ThreadPoolTaskExecutor();
    // This will set thread name prefix, which can be seen in the logs
    executor.setThreadNamePrefix("Thread pool sample threads - ");
    executor.setCorePoolSize(2); // For detailed examples look into ThreadPoolTaskExecutorPropertiesSample class
    executor.setMaxPoolSize(2); // For detailed examples look into ThreadPoolTaskExecutorPropertiesSample class
    executor.initialize();

    for (int i = 0; i < 5; i++) {
      executor.execute(getRunnable(i));
    }
  }

  public static Runnable getRunnable(int i) {
    return () -> {
      log.info("Running process {}", i);
      sleep(3000);
      log.info("Process %s finished {}", i);
    };
  }
}
