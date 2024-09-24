package com.sanver.basics.threads;

import java.util.concurrent.FutureTask;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;
import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

public class ExceptionHandlingInThreads {
    public static void main(String[] args) {
        Runnable process = () -> {
            for (int i = 1; i < 100; i++) {
                System.out.print(i + " ");
                sleep(300);

                if (i == 10) {
                    System.out.printf("%nException will be thrown in the thread. %s%n%n", getThreadInfo());
                    throw new IllegalArgumentException("Not designed to process over 9");
                }
            }
        };

        unhandled(process);
        handledInTheRaisedThread(process);
        handledInTheMainThread(process);
    }

    private static void unhandled(Runnable process) {
        var thread = new Thread(process);

        try {
            thread.start();
            sleep(5000);
            System.out.printf("%nMain thread is still running without an exception. %s%n%n", getThreadInfo()); // This is to show that the exception is not propagated to the main thread.
            thread.join();
        } catch (Exception e1) {
            System.out.println("Main thread main caught exception " + e1); // There won't be any exception caught in the main thread. Even when thread.join() is called the exception is not caught.
        }

        System.out.printf("%nUnhandled block finished%n%n");
    }

    private static void handledInTheRaisedThread(Runnable process) {
        var thread = new Thread(process);
        thread.setUncaughtExceptionHandler((t, e) ->
                System.out.printf("Exception thrown in thread: Thread id: %d Thread name: %s Is Daemon: %s. Handled in thread: %s. Exception: %s%n", t.getId(), t.getName(), t.isDaemon(), getThreadInfo(), e));
        thread.start();
        uncheck(() -> thread.join());
        System.out.printf("%nMain thread running without an exception. %s%n%n", getThreadInfo()); // This is to show that the exception is not propagated to the main thread even when thread.join() is called.
        System.out.printf("%nhandledInTheRaisedThread block finished%n%n");
    }

    private static void handledInTheMainThread(Runnable process) {
        var future = new FutureTask<>(process, null);
        var thread = new Thread(future);

        try {
            thread.start();
            sleep(5000); // Notice no exception is thrown in the user thread as well.
            System.out.printf("%nMain thread is still running without an exception. %s%n%n", getThreadInfo()); // This is to show that the exception is not propagated to the main thread.
            thread.join();
        } catch (Exception e1) {
            System.out.println("Main thread main caught exception " + e1); // There won't be any exception caught in the main thread. Even when thread.join() is called the exception is not caught.
        }

        try {
            future.get();
        } catch (Exception e) {
            System.out.printf("Exception handled in thread: %s. Exception: %s%n", getThreadInfo(), e);
        }

        System.out.printf("%nhandledInTheMainThread block finished%n%n");
    }
}
