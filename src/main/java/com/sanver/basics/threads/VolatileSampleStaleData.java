package com.sanver.basics.threads;

import java.text.NumberFormat;

class SharedFlag {
    private volatile boolean isRunning = true; // Shared flag. Remove volatile to see the difference.
    // If volatile is removed, the workerThread may work more with more iterations or even not stop working.

    public void stop() {
        isRunning = false; // Signal to stop
    }

    public boolean isRunning() { // We can remove volatile and make this method synchronized as well,
        // but in this case realize how the iteration count is drastically reduced (5-10 times fewer) compared to volatile,
        // showing the performance advantage of volatile.
        return isRunning;
    }
}

public class VolatileSampleStaleData {
    public static void main(String[] args) throws InterruptedException {
        SharedFlag sharedFlag = new SharedFlag();
        NumberFormat numberFormat = NumberFormat.getNumberInstance(); // For formatting

        long startTime = System.nanoTime(); // Start time

        Thread workerThread = new Thread(() -> {
            long iterations = 0;
            while (sharedFlag.isRunning()) {
                iterations++;
            }

            // Format iteration count
            System.out.printf("Worker thread ended after %s iterations.%n", numberFormat.format(iterations));
        });

        workerThread.start();
        Thread.sleep(2000);
        sharedFlag.stop();
        System.out.println("isRunning is set to false in main thread");
        workerThread.join();

        long endTime = System.nanoTime(); // End time
        long duration = endTime - startTime; // Duration in nanosecondsment

        // Calculate and print duration
        System.out.printf("Total execution time: %s nanoseconds%n", numberFormat.format(duration));
    }
}

