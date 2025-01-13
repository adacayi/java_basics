package com.sanver.basics.threads;

import java.util.concurrent.CompletableFuture;

public class ThreadLocalSample {
    public static final ThreadLocal<String> traceId = new ThreadLocal<>();

    public static void main(String[] args) {
        System.out.printf("Main started with thread id %s. Trace id not set yet.%n", threadId());
        printContext();
        traceId.set("12345");
        System.out.printf("Trace id set for main thread %s.%n", threadId());
        printContext();
        CompletableFuture.runAsync(getRunnable()).join();
        printContext();
        traceId.remove();
        System.out.printf("Trace id removed for main thread %s.%n", threadId());
        printContext();
    }

    private static long threadId() {
        return Thread.currentThread().threadId();
    }

    private static void printContext() {
        System.out.printf("Thread: %s Trace id: %s.%n%n", threadId(), traceId.get());
    }

    public static Runnable getRunnable() {
        return () -> {
            System.out.printf("New runnable started with thread id %s. Trace id not set yet.%n", threadId());
            // Note, as can be seen when the below code executed, traceId will be null, since this is a new thread.
            printContext();
            traceId.set("abc");
            System.out.printf("Trace id set for thread %s.%n", threadId());
            printContext();
            System.out.printf("Trace id removed for thread %s.%n", threadId());
            traceId.remove();
            printContext();
        };
    }
}
