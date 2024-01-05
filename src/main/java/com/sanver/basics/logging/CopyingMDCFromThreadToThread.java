package com.sanver.basics.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.CompletableFuture;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class CopyingMDCFromThreadToThread {

  public static void main(String[] args) {
    // To output the value in MDC we put %X{userName} in the pattern in the logback.xml
    var logger = LoggerFactory.getLogger(CopyingMDCFromThreadToThread.class);
    MDC.put("userName", "main thread user");
    var contextMap = MDC.getCopyOfContextMap();
    IntFunction<Runnable> userLogger = x -> () -> {
      // Since MDC values are specific per thread, if we don't set the contextMap here,
      // it will be different from the main thread's contextMap
      MDC.setContextMap(contextMap);
      logger.info("Logger run with user: {}", x);
    };
    var combined = CompletableFuture.allOf(IntStream.rangeClosed(1, 3)
                                                    .mapToObj(x -> CompletableFuture.runAsync(userLogger.apply(x)))
                                                    .toArray(CompletableFuture[]::new));
    combined.join();
    logger.info("Completed");
  }
}
