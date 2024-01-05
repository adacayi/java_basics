package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;

import static com.sanver.basics.utils.Utils.sleep;

public class IsDaemonSample {
    public static void main(String[] args) {
        System.out.println("Main thread started");
        var future = CompletableFuture.runAsync(() -> {
            System.out.printf("Process started. Is daemon: %s%n", Thread.currentThread().isDaemon());
            sleep(2000);
            System.out.printf("Process finished.%n");
        });
        future.join(); // Since the thread in CompletableFuture is not a user thread (it is a daemon thread), main thread will exit before its processing is finished. We put join here to wait for it to complete.
        System.out.println("Main thread finished.");
    }
}
