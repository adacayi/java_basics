package com.sanver.basics.threads.completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * {@code copy()} method creates a new {@link CompletableFuture} that reflects the completion state of the original
 * {@code CompletableFuture}.
 * <p>
 * The new {@code CompletableFuture} completes in the same manner as this one:
 * <ul>
 *   <li>If this {@code CompletableFuture} completes normally, the new {@code CompletableFuture}
 *       completes with the same result.</li>
 *   <li>If this {@code CompletableFuture} completes exceptionally, the new {@code CompletableFuture}
 *       completes exceptionally with a {@link CompletionException} containing the original exception
 *       as its cause.</li>
 * </ul>
 * <p>
 * This method is useful for creating a "defensive copy" of a {@code CompletableFuture}.
 * For example, it can prevent clients from completing the original {@code CompletableFuture}
 * while still allowing them to observe its state or arrange dependent actions.
 */
public class CopySample {
    public static void main(String[] args) {
        System.out.printf("Example demonstrating client completing the original future without its execution actually finishing%n%n");
        Runnable runnable = () -> {
            System.out.println("Runnable  started " + getThreadInfo());
            sleep(7_000);
            System.out.println("Runnable finished " + getThreadInfo());
        };

        var future = CompletableFuture.runAsync(runnable);
        sleep(4_000);
        future.complete(null); // Simulates completion of the original future by client. Since it's state is SUCCESS, future.join() will not wait for the finish of the actual execution of the runnable.
        System.out.println("Client called future.complete(null)");
        future.join();
        System.out.println("future.join() executed. Note that at this stage we don't see the \"Finished \" printed out");
        sleep(4_000);
        System.out.println("Finally the runnable execution is finished");

        sleep(7_000);
        System.out.printf("%nExample, preventing clients from completing the original future%n%n");

        future = CompletableFuture.runAsync(runnable);
        var copy = future.copy();
        sleep(4_000);
        copy.complete(null); // Simulates completion of the copied future by client. Since this does not change the original future's state, future.join() will wait for the finish of the actual execution of the runnable.
        System.out.println("Client called copy.complete(null)");
        future.join();
        System.out.println("future.join() executed. Note that at this stage \"Finished \" is already printed out.");

        sleep(7_000);
        System.out.printf("%nExample, original state affecting the copy state%n%n");

        future = CompletableFuture.runAsync(runnable);
        copy = future.copy();
        copy.thenRun(() -> {
            sleep(1000);
            System.out.println("Copy thenRun1 finished. " + getThreadInfo());
        }).thenRun(()->{
            sleep(1000);
            System.out.println("Copy thenRun2 finished. " + getThreadInfo());
        });

        future.join(); // When this is finished, original future's state will be SUCCESS, making the copy state SUCCESS as well.
        System.out.println("future.join() executed");
        System.out.println(future.state());
        System.out.println(copy.state()); // Since future state is SUCCESS, copy state becomes SUCCESS as well.
        copy.join(); // This will not wait for the thenRun parts to execute since copy state is already SUCCESS.
        System.out.println("copy.join() executed. Notice thenRun parts are not executed yet, because join will not wait since copy state is already SUCCESS");
        sleep(3000); // This is to show that thenRuns will still be executed if we waited enough.
        System.out.println("thenRun parts finally executed");

        sleep(7_000);
        System.out.printf("%nExample, original state change after copy.join() not affecting copy.join()%n%n");

        future = CompletableFuture.runAsync(runnable);
        copy = future.copy();
        copy.thenRun(() -> {
            sleep(1000);
            System.out.println("Copy thenRun1 finished. " + getThreadInfo());
        }).thenRun(()->{
            sleep(1000);
            System.out.println("Copy thenRun2 finished. " + getThreadInfo());
        });

        System.out.println("State before copy.join()");
        System.out.println(future.state());
        System.out.println(copy.state());
        copy.join(); // This will wait for the thenRun parts to execute since at this point copy state is not SUCCESS.
        System.out.println("copy.join() executed. Notice thenRun parts are executed");
    }
}
