package com.sanver.basics.logging;

import static com.sanver.basics.utils.ThreadUtils.sleep;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class MDCSampleWithMultipleThreads {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // To output the value in MDC we put %X{userName} in the pattern in the logback.xml
    var logger = LoggerFactory.getLogger(MDCSampleWithMultipleThreads.class);
    MDC.put("userName", "main thread user");
    Function<Integer, Runnable> userLogger = x -> () -> {
      var userName = "user " + x;

      if (x < 4) {
        MDC.put("userName", userName);
      }

      if (x == 5) {
        MDC.clear();
      }
      sleep(2000);
      logger.info("Logger run with user: {}", x);
    };
    var executor = Executors.newFixedThreadPool(2);
    var combined = CompletableFuture.allOf(IntStream.rangeClosed(1, 8)
                                                    .mapToObj(x -> CompletableFuture.runAsync(userLogger.apply(x),
                                                        executor)).toArray(CompletableFuture[]::new));
    combined.get();
    // Note that MDC values are retained per thread. We need to call MDC.clear() to clear them for the relevant thread.
    logger.info("Completed");
  }
}
