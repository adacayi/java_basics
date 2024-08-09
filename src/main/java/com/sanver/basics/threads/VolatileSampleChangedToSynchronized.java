package com.sanver.basics.threads;

import com.sanver.basics.threads.VolatileSample.InstantValue;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.sanver.basics.threads.VolatileSample.checkIfStaleDataExists;
import static com.sanver.basics.utils.Utils.sleepNano;

// This is to show how we can achieve the same result in VolatileSample code by using synchronized instead. But this has a performance impact.
// To see how drastic the performance impact is, look at the VolatileSampleStaleData class and remove volatile and make the isRunning method synchronized.
public class VolatileSampleChangedToSynchronized {
    private static final int LIMIT = 10_000_000;
    private static final List<InstantValue> mainThreadData = new ArrayList<>();
    private static final List<InstantValue> readerThreadData = new ArrayList<>();
    private static final Object lock = new Object();
    private static int value = 0;

    public static void main(String[] args) {
        var reader = CompletableFuture.runAsync(() -> {
            while (value < LIMIT) {
                synchronized (lock) {
                    readerThreadData.add(new InstantValue(System.nanoTime(), value));
                }
                sleepNano(100); // This is added so that main thread can more frequently acquire lock. Otherwise, we end up with too many data in the readerThreadData causing memory exception.
            }
        });

        for (int i = 0; i < LIMIT; i++) {
            synchronized (lock) {
                value++;
            }
            mainThreadData.add(new InstantValue(System.nanoTime(), value));
        }

        reader.join();

        System.out.println(NumberFormat.getInstance().format(readerThreadData.size()));
        checkIfStaleDataExists(mainThreadData, readerThreadData);
    }
}

