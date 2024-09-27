package com.sanver.basics.threads.executors;

import java.text.NumberFormat;
import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.sanver.basics.utils.Utils.sleep;

public class ScheduledExecutorServiceSample {
    private static int count = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var executor = Executors.newScheduledThreadPool(3);
        var future1Start = System.nanoTime();
        var formatter = NumberFormat.getInstance();

        var future1 = executor.schedule(() -> {
            var duration = (System.nanoTime() - future1Start) / 1_000_000;
            System.out.printf("future1 started in %s milliseconds.%n", formatter.format(duration));
            return List.of(2, 3, 5, 7);
        }, 2, TimeUnit.SECONDS);

        var previousExecution = new ArrayDeque<Long>();
        previousExecution.push(System.nanoTime());

        var future2 = executor.scheduleAtFixedRate(() -> {
            var duration = (System.nanoTime() - previousExecution.pop()) / 1_000_000;
            if (count == 0) {
                System.out.printf("future2 started in %s milliseconds.%n", formatter.format(duration));
            }
            count++;
            System.out.printf("%3d. run. Interval: %s%n", count, formatter.format(duration));
            previousExecution.push(System.nanoTime());
        }, 4000, 100, TimeUnit.MILLISECONDS);

        var future1Result = future1.get();
        System.out.println("future1 result " + future1Result);
        // future2.get(); // This will never stop since the scheduler will run it in every 100 millisecond interval until the future is cancelled.
        sleep(5000);
        future2.cancel(true); // This will stop the next scheduled execution.
        // future2.get(); // If we called this at this point, we would get CancellationException.
        System.out.println("future2 cancelled");
        executor.shutdown();
    }
}
