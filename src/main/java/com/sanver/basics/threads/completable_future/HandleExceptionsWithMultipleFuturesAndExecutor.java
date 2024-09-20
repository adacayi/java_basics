package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

public class HandleExceptionsWithMultipleFuturesAndExecutor {

    private static final AtomicInteger count = new AtomicInteger(0);
    private static final AtomicInteger idCount = new AtomicInteger(0);

    public static void main(String[] args) {
        Supplier<String> supplier = () -> {
            var id = idCount.addAndGet(1);
            System.out.printf("%d: started. %s%n", id, getThreadInfo());
            sleep(13000);

            if (id == 2 || id == 3) {
                System.out.printf("%d: will throw an exception. %s%n", id, getThreadInfo());
                throw new RuntimeException(String.format("Some error occurred in process %d %s", id, getThreadInfo()));
            }

            System.out.printf("%d: completed. %s%n", id, getThreadInfo());
            return "completed";
        };

        var executor = Executors.newFixedThreadPool(5);
        var future1 = CompletableFuture.supplyAsync(supplier, executor);
        sleep(3000);
        var future2 = CompletableFuture.supplyAsync(supplier, executor);
        sleep(3000);
        var future3 = CompletableFuture.supplyAsync(supplier, executor);
        sleep(3000);
        var future4 = CompletableFuture.supplyAsync(supplier, executor);
        sleep(3000);
        var future5 = CompletableFuture.supplyAsync(supplier, executor);

        var combined = CompletableFuture.allOf(future1, future2, future3, future4, future5);

        var handled = combined.handle(
                (r, e) -> {
                    if (e == null) {
                        System.out.printf("No error occurred. Return value is: %s %s%n", r, getThreadInfo());
                        count.addAndGet(1);
                        return -1;
                    }
                    System.out.printf("Handle executed. %s. Error details in handle: %s %n", getThreadInfo(), e);
                    return count.addAndGet(1);
                })
                .thenAccept(x -> System.out.printf("Then accept executed. %s. The return value is: %s%n", getThreadInfo(), x));

        handled.join();
        executor.shutdown();
    }
}
