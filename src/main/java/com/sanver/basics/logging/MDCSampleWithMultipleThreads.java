package com.sanver.basics.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.getThreadInfo;

public class MDCSampleWithMultipleThreads {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // To output the value in MDC we put %X{userName} in the pattern in the logback.xml
        // https://www.baeldung.com/mdc-in-log4j-2-logback
        var logger = LoggerFactory.getLogger(MDCSampleWithMultipleThreads.class);
        final var userNameKey = "userName";
        MDC.put(userNameKey, "main thread user");

        IntFunction<Runnable> userLogger = x -> () -> {
            var userName = "user " + x;

            if (x < 3) {
                MDC.put(userNameKey, userName);
            }
            var mdcUser = MDC.get(userNameKey);
            logger.info("Task: {}, MDC.get(\"userName\"): {} {}", x, mdcUser, getThreadInfo());
        };

        var executor = Executors.newFixedThreadPool(2);
        var futures = CompletableFuture.allOf(IntStream.rangeClosed(1, 4)
                .mapToObj(x -> CompletableFuture.runAsync(userLogger.apply(x), executor))
                .toArray(CompletableFuture[]::new));
        futures.join();
        // Note that MDC values are retained per thread. We need to call MDC.clear() to clear them for the relevant thread.
        logger.info("Completed");
        executor.shutdown();
    }
}
