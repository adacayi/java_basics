package com.sanver.basics.logging;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class CopyingMDCFromThreadToThread {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // To output the value in MDC we put %X{userName} in the pattern in the logback.xml
    var logger = LoggerFactory.getLogger(CopyingMDCFromThreadToThread.class);
    MDC.put("userName", "main thread user");
    var contextMap = MDC.getCopyOfContextMap();
    Function<Integer, Runnable> userLogger = x -> () -> {
      // Since MDC values are specific per thread, if we don't set the contextMap here,
      // it will be different from the main thread's contextMap
      MDC.setContextMap(contextMap);
      logger.info("Logger run with user: {}", x);
    };
    var combined = CompletableFuture.allOf(IntStream.rangeClosed(1, 3)
                                                    .mapToObj(x -> CompletableFuture.runAsync(userLogger.apply(x)))
                                                    .toArray(CompletableFuture[]::new));
    combined.get();
    logger.info("Completed");
  }
}
