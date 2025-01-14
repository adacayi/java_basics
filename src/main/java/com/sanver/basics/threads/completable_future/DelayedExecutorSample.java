package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * Returns a new Executor that submits a task to the given base executor after the given delay (or no delay if non-positive).
 * Each delay commences upon invocation of the returned executor's execute method.
 */
public class DelayedExecutorSample {
    public static void main(String[] args) {
        var executor = Executors.newCachedThreadPool();
        var delayedExecutor = CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS, executor);
        var start = System.nanoTime();
        CompletableFuture.runAsync(() -> {
            System.out.printf("Task  started after %,d milliseconds. %s%n", (System.nanoTime() - start) / 1_000_000, getThreadInfo());
            sleep(10_000);
            System.out.println("Task finished " + getThreadInfo());
        }, delayedExecutor);

        System.out.println("runAsync executed");
        sleep(4_000);
        printThreadPool(executor, "%nexecutor state".formatted());
        System.out.println("executor.close() will be invoked");
        executor.close();
        System.out.println("executor.close() finished.");
        sleep(3_000);
        printThreadPool(executor, "%nexecutor state".formatted());
    }
}
