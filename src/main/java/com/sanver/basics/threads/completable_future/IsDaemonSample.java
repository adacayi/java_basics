package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

public class IsDaemonSample {
    public static void main(String[] args) {
        // All async methods in CompletableFuture without an explicit Executor argument are performed using the ForkJoinPool.commonPool()
        // (unless it does not support a parallelism level of at least two, in which case, a new Thread is created to run each task).
        // The threads in ForkJoinPool.commonPool() are daemon threads.
        System.out.printf("Main thread  started. %s%n", getThreadInfo());
        var future = CompletableFuture.runAsync(() -> {
            System.out.printf("Process      started. %s%n", getThreadInfo());
            sleep(2000);
            System.out.printf("Process     finished. %s%n", getThreadInfo());
        });
        future.join(); // Since the thread in CompletableFuture is not a user thread (it is a daemon thread), main thread will exit before its processing is finished. We put join here to wait for it to complete.
        System.out.printf("Main thread finished. %s%n", getThreadInfo());
    }
}
