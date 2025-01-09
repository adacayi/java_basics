package com.sanver.basics.threads.executors;

import java.text.NumberFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.sanver.basics.utils.Utils.printThreadPool;
import static com.sanver.basics.utils.Utils.sleep;

public class ScheduledExecutorServiceSample {
    private static int count = 0;
    private static long previousExecution;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var executor = Executors.newScheduledThreadPool(1)) {
            printThreadPool(executor, "Scheduled thread pool initial state");
            sleep(7_000);
            var future1Start = System.nanoTime();
            var formatter = NumberFormat.getInstance();

            var future1 = executor.schedule(() -> { // This has overloads for Runnable and Callable
                var duration = (System.nanoTime() - future1Start) / 1_000_000;
                System.out.printf("future1 started in %s milliseconds.%n", formatter.format(duration));
                printThreadPool(executor, "Thread pool state when running the first task");
                return List.of(2, 3, 5, 7);
            }, 7, TimeUnit.SECONDS);

            printThreadPool(executor, "Scheduled thread pool after executor.schedule()");
            var future1Result = future1.get();
            sleep(7_000);
            System.out.println("future1 result " + future1Result);

            printThreadPool(executor, "Scheduled thread pool after future1.get() is executed");

            sleep(7_000);
            previousExecution = System.nanoTime();

            var future2 = executor.scheduleAtFixedRate(() -> { // There is only one version excepting Runnable
                var duration = (System.nanoTime() - previousExecution) / 1_000_000;
                if (count == 0) {
                    System.out.printf("future2 started in %s milliseconds.%n", formatter.format(duration));
                }
                count++;
                System.out.printf("%3d. run. Interval: %s%n", count, formatter.format(duration));
                printThreadPool(executor, "Thread pool state when running the second task");
                previousExecution = System.nanoTime();
            }, 7, 7, TimeUnit.SECONDS);

            printThreadPool(executor, "Scheduled thread pool after executor.scheduleAtFixedRate()");

            // future2.get(); // This will never stop since the scheduler will run it in every 100 millisecond interval until the future is cancelled.
            sleep(25_000);
            printThreadPool(executor, "Scheduled thread pool before future2.cancel(true) is executed");
            sleep(7_000);
            future2.cancel(true); // This will stop the next scheduled execution.
            // future2.get(); // If we called this at this point, we would get CancellationException.
            System.out.println("future2 cancelled");
            printThreadPool(executor, "Scheduled thread pool after future2.cancel(true) is executed");
        }
    }
}
