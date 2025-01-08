package com.sanver.basics.threads.executors;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.printCurrentThread;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * This class is to show how exceptions are handled in different scenarios in threads other than the main thread.
 * If there is no Future class involved, the exception is output to stdout in the relevant thread and {@code Thread.join()} in main does not throw any exceptions.
 * If there is a Future class involved in the task execution like {@code new Thread(new FutureTask<>()), pool.invokeAll(), pool.submit()}
 * then the exception is not output and if we call the relevant future's {@code future.get()}, then we will get the exception wrapped into an
 * {@code ExecutionException} class and can acquire the original exception by {@code e.getCause()}.
 */
public class ExceptionHandling {

    public static final long MILLIS_BEFORE_THROWING_EXCEPTION = 5_000L;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Example: new Thread(runnable).start()");
        printCurrentThread("Main thread info");
        Runnable printWithException = () -> {
            System.out.println("Task started. " + getThreadInfo());
            sleep(MILLIS_BEFORE_THROWING_EXCEPTION - 2000);
            System.out.println("Runtime exception will be thrown. " + getThreadInfo());
            sleep(2000);
            throw new RuntimeException("Runtime exception for the task thrown");
        };

        var thread = new Thread(printWithException);
        thread.start();
        sleepTillExceptionIsThrown(); // This is to show that the RuntimeException is output to stdout but the exception is not propagated to the main thread.
        // That is also the case when thread.join() is called.
        System.out.printf("Thread execution finished.%n%n");
        thread.join(); // This call is to show that no exception is thrown here, even though RuntimeException was thrown in the thread itself.

        waitForNextExample("new Thread(new FutureTask<>()).start()");

        var futureTask = new FutureTask<Void>(() -> {
            printWithException.run();
            return null;
        });
        thread = new Thread(futureTask);
        thread.start();
        sleepTillExceptionIsThrown(); // This is to show that the RuntimeException is not output to stdout and the exception is not propagated to the main thread since the Runnable is wrapped in a FutureTask..
        // No exception will be thrown when thread.join() is called.
        System.out.printf("Thread execution finished.%n%n");
        thread.join(); // This call is to show that no exception is thrown here, even though RuntimeException was thrown in the thread itself.

        try {
            printExceptionCatching();
            futureTask.get();  // The original exception can be caught and if needed logged if the future.get() method is called. It will wrap the original exception into an ExecutionException and the original exception can be acquired by calling the getCause() method.
        } catch (ExecutionException e) {
            print(e);
        }

        waitForNextExample("executor.execute()");

        try (var executor = Executors.newFixedThreadPool(2)) {
            executor.execute(printWithException);
            sleepTillExceptionIsThrown();
            System.out.printf("Executor execution finished for execute.%n%n"); // Notice no exception is propagated to the main thread, but the exception is printed to the stdout.

            waitForNextExample("executor.submit()");

            var future = executor.submit(printWithException);
            sleepTillExceptionIsThrown();
            System.out.println("Executor execution finished for submit."); // Notice no exception is propagated to the main thread, and the exception is not printed to the stdout.

            try {
                printExceptionCatching();
                future.get(); // The original exception can be caught and if needed logged if the future.get() method is called. It will wrap the original exception into an ExecutionException and the original exception can be acquired by calling the getCause() method.
            } catch (ExecutionException e) {
                print(e);
            }

            waitForNextExample("executor.invokeAll()");

            List<Future<Void>> futures = executor.invokeAll(List.of(() -> {
                printWithException.run();
                return null;
            })); // invokeAll returns the result when all futures are complete.

            System.out.println("Executor invokeAll finished."); // Notice no exception is propagated to the main thread, and the exception is not printed to the stdout.

            try {
                printExceptionCatching();
                futures.get(0).get(); // The original exception can be caught and if needed logged if the future.get() method is called. It will wrap the original exception into an ExecutionException and the original exception can be acquired by calling the getCause() method.
            } catch (ExecutionException e) {
                print(e);
            }
        }

        waitForNextExample("CompletableFuture.runAsync()");

        var completableFuture = CompletableFuture.runAsync(printWithException);
        sleepTillExceptionIsThrown();
        System.out.println("CompletableFuture finished."); // Notice no exception is propagated to the main thread, and the exception is not printed to the stdout.

        try {
            printExceptionCatching();
            completableFuture.get(); // The original exception can be caught and if needed logged if the completableFuture.get() or completableFuture.join() methods are called.
            // It will wrap the original exception into an ExecutionException for get() and CompletionException for join().
            // We can also use handle() and whenComplete() methods to manage the exception handling. Look for the ExceptionHandling class under completable_future package.
        } catch (ExecutionException e) {
            print(e);
        }
    }

    private static void printExceptionCatching() {
        System.out.println("No exception output to stdout");
        sleep(1000);
        System.out.println("Catching exception in main by calling future.get()");
        sleep(2000);
    }

    private static void sleepTillExceptionIsThrown() {
        sleep(MILLIS_BEFORE_THROWING_EXCEPTION + 500);
    }

    private static void waitForNextExample(String message) {
        sleep(4000);
        System.out.println("Next example: " +message);
        sleep(2000);
    }
    private static void print(ExecutionException e) {
        System.out.println("Exception is caught on calling the future.get(): " + e);
        System.out.printf("Original exception: %s%n%n", e.getCause());
    }
}
