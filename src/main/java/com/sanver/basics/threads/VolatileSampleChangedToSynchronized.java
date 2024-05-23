package com.sanver.basics.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// This is to show how we can achieve the same result in VolatileSample code by using synchronized instead. But this has a performance impact.
// To see how drastic the performance impact is, look at the VolatileSampleStaleData class and remove volatile and make the isRunning method synchronized.
public class VolatileSampleChangedToSynchronized {
    private static final List<Counter> MAIN_THREAD_COUNTER = new ArrayList<>();
    private static final List<Counter> READER_COUNTER = new ArrayList<>();
    private static final int NUM_ITERATIONS = 10000000;
    private static int sharedCounter = 0; // This is the counter read by the reader thread and incremented by the main thread.

    public static int getSharedCounter() { // We cannot just use synchronized here, because synchronization with the increment operation is also necessary.
        // Thus, we use a shared lock object so that increment and read are done in synchronization.
        // You can try making this method synchronized and removing the below synchronized line to see that we end up with stale data.
        synchronized(LOCK) {
            return sharedCounter;
        }
    }

    public static final Object LOCK = new Object();

    public static void main(String[] args) {
        MAIN_THREAD_COUNTER.add(new Counter(System.nanoTime(), getSharedCounter()));
        long mainInstant;
        var reader = CompletableFuture.runAsync(() -> {
            long readerInstant;
            while (getSharedCounter() < NUM_ITERATIONS) {
                readerInstant = System.nanoTime();
                READER_COUNTER.add(new Counter(readerInstant, getSharedCounter()));
            }
        });

        while (getSharedCounter() < NUM_ITERATIONS) {
            synchronized (LOCK){
                sharedCounter++;
                mainInstant = System.nanoTime();
                MAIN_THREAD_COUNTER.add(new Counter(mainInstant, getSharedCounter()));
            }
        }

        reader.join();

        checkIfStaleDataExistsForReaderThread();
    }

    private static void checkIfStaleDataExistsForReaderThread() {
        int i = 0;
        long readInstant;
        int readValue;
        long mainInstant;
        int mainValue;
        boolean staleDataExists = false;

        for (var readerCounter : READER_COUNTER) {
            readInstant = readerCounter.instant();
            readValue = readerCounter.value();

            if (MAIN_THREAD_COUNTER.get(i).instant() <= readInstant) {
                do {
                    i++;
                }
                while (i < MAIN_THREAD_COUNTER.size() && MAIN_THREAD_COUNTER.get(i).instant() <= readInstant);
                i--;
            }

            mainInstant = MAIN_THREAD_COUNTER.get(i).instant();
            mainValue = MAIN_THREAD_COUNTER.get(i).value();

            if (readInstant > mainInstant && readValue < mainValue) { // Only checking for stale data.
                // There are cases when main thread value is written to the list later than the read thread because of the implementation,
                // but we only care no stale data ever happens. If you remove volatile in getSharedCounter(, stale data will appear.
                System.out.printf("Stale data %d in reader thread at instant %d, while main thread value is %d at instant %d%n", readValue, readInstant, mainValue, mainInstant);
                staleDataExists = true;
            }

        }

        if (!staleDataExists) {
            System.out.println("No stale data found.");
        }
    }

    record Counter(long instant, int value){}
}

