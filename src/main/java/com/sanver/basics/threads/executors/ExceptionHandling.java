package com.sanver.basics.threads.executors;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.printCurrentThread;
import static com.sanver.basics.utils.Utils.sleep;

public class ExceptionHandling {
    public static void main(String[] args) throws InterruptedException {
        printCurrentThread("Main thread info");
        Runnable printWithException = () -> {
            System.out.println("Task started. " + getThreadInfo());
            sleep(1000);
            System.out.println("Runtime exception will be thrown. " + getThreadInfo());
            throw new RuntimeException("Runtime exception for the task thrown");
        };

        var thread = new Thread(printWithException);
        thread.start();
        sleep(2000); // This is to show that without calling join method, we will be able to see the RuntimeException.
        // This exception is not propagated to the main thread. That is also the case when thread.join() is called.
        System.out.printf("Thread execution finished.%n%n");
        thread.join(); // This call is to show that no exception is thrown here, even though RuntimeException was thrown in the thread itself.

        var executor = Executors.newFixedThreadPool(2);
        executor.execute(printWithException);
        sleep(2000);

        System.out.printf("Executor execution finished for execute.%n%n"); // Notice no exception is propagated to the main thread, but the exception is printed to the stdout.

        var future = executor.submit(printWithException);
        sleep(2000);

        System.out.println("Executor execution finished for submit."); // Notice no exception is propagated to the main thread, and the exception is not printed to the stdout.
        try {
            future.get(); // The original exception can be caught and if needed logged if the future.get() method is called. It will wrap the original exception into an ExecutionException.
        } catch (ExecutionException e) {
            System.out.println("Exception is caught on calling the future.get(): " + e);
            System.out.println("Original exception: " + e.getCause());
        }

        System.out.println();

        List<Future<Void>> futures = executor.invokeAll(List.of(() -> {
            printWithException.run();
            return null;
        })); // invokeAll returns the result when all futures are complete.

        System.out.println("Executor invokeAll finished."); // Notice no exception is propagated to the main thread, and the exception is not printed to the stdout.

        try {
            futures.get(0).get(); // The original exception can be caught and if needed logged if the future.get() method is called. It will wrap the original exception into an ExecutionException.
        } catch (ExecutionException e) {
            System.out.println("Exception is caught on calling the future.get(): " + e);
            System.out.println("Original exception: " + e.getCause());
        }

        executor.shutdown();

        System.out.println();
        var completableFuture = CompletableFuture.runAsync(printWithException);
        sleep(2000);
        System.out.println("CompletableFuture finished."); // Notice no exception is propagated to the main thread, and the exception is not printed to the stdout.

        try {
            completableFuture.get(); // The original exception can be caught and if needed logged if the completableFuture.get() or completableFuture.join() methods are called. It will wrap the original exception into an ExecutionException for get() and CompletionException for join().
            // We can also use handle() and whenComplete() methods to manage the exception handling. Look for the ExceptionHandling class under completable_future package.
        } catch (ExecutionException e) {
            System.out.println("Exception is caught on calling the completableFuture.get(): " + e);
            System.out.println("Original exception: " + e.getCause());
        }
    }
}
