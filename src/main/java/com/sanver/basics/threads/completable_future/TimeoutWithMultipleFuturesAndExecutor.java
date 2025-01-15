package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

public class TimeoutWithMultipleFuturesAndExecutor {

    private static final AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        Runnable runnable = () -> {
            var id = count.addAndGet(1);
            System.out.printf("%d:   started. %s%n", id, getThreadInfo());
            if (id == 2 || id == 3) {
                sleep(7000);
            }
            System.out.printf("%d: completed. %s%n", id, getThreadInfo());
        };

        var executor = Executors.newFixedThreadPool(2);
        var future1 = CompletableFuture.runAsync(runnable, executor).orTimeout(1000, TimeUnit.MILLISECONDS);
        var future2 = CompletableFuture.runAsync(runnable, executor).orTimeout(1000, TimeUnit.MILLISECONDS);
        var future3 = CompletableFuture.runAsync(runnable, executor).orTimeout(1000, TimeUnit.MILLISECONDS);
        var future4 = CompletableFuture.runAsync(runnable, executor).orTimeout(8000, TimeUnit.MILLISECONDS);// Since executor has 2 threads, if timeout was set to 1000, this will already be timed out before executed, so, the executor won't execute it, so we set it to 8 seconds since there is a 7 seconds wait in task 2 and 3.
        var future5 = CompletableFuture.runAsync(runnable, executor).orTimeout(8000, TimeUnit.MILLISECONDS);// Since executor has 2 threads, if timeout was set to 1000, this will already be timed out before executed, so, the executor won't execute it, so we set it to 8 seconds since there is a 7 seconds wait in task 2 and 3.
        var combined = CompletableFuture.allOf(future1, future2, future3, future4, future5);

        // Note that all futures are executed before handle is executed. Since future4 and future5 start after future2 and/or future3 are/is executed and a thread is freed in the executor, we see the completion of them as well before handle is executed.
        // If we did not have future4 and future5 in the combined, we wouldn't see future2 and future3 completed before handle is executed.
        combined.handle((r, e) -> {
            if (e != null) {
                System.out.printf("Handle executed. Some error occurred: %s%n", e);
                return 0;
            }
            System.out.printf("Handle executed: Completed successfully.%n");
            return 1;
        });

        CompletableFuture.runAsync(() ->
                        System.out.println(
                                "This thread cannot be acquired while the other 2 timed-out threads are still executing, because the "
                                        + "pool size is 2 and although they are timed out they are still executing. " + getThreadInfo()),
                executor
        );

        executor.close();
        System.out.println("Final line");
    }
}
