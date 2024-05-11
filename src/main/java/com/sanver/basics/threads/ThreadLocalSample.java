package com.sanver.basics.threads;

public class ThreadLocalSample {
    public static final ThreadLocal<String> traceId = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.printf("Main started with thread id %s. Trace id not set yet.%n", Thread.currentThread().getId());
        printContext();
        traceId.set("12345");
        System.out.printf("Trace id set for main thread %s.%n", Thread.currentThread().getId());
        printContext();
        var thread1 = new Thread(getRunnable());
        thread1.start();
        thread1.join();
        printContext();
        traceId.remove();
        System.out.printf("Trace id removed for main thread %s.%n", Thread.currentThread().getId());
        printContext();
    }

    private static void printContext() {
        System.out.printf("Thread: %s Trace id: %s.%n", Thread.currentThread().getId(), traceId.get());
    }

    public static Runnable getRunnable() {
        return () -> {
            System.out.printf("New runnable started with thread id %s. Trace id not set yet.%n", Thread.currentThread().getId());
            // Note, as can be seen when the below code executed, traceId will be null, since this is a new thread.
            printContext();
            traceId.set("abc");
            System.out.printf("Trace id set for thread %s.%n", Thread.currentThread().getId());
            printContext();
            System.out.printf("Trace id removed for thread %s.%n", Thread.currentThread().getId());
            traceId.remove();
            printContext();
        };
    }
}
