package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;

public class IsDaemonSample {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main thread started");
        var future = CompletableFuture.runAsync(() -> {
            System.out.printf("Process started. Is daemon: %s%n", Thread.currentThread().isDaemon());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("Process finished.%n");
        });
        future.join(); // Since the thread in CompletableFuture is not a user thread (it is a daemon thread), main thread will exit before its processing is finished. We put join here to wait for it to complete.
        System.out.println("Main thread finished.");
    }
}
