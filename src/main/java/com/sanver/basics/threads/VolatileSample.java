package com.sanver.basics.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// https://www.baeldung.com/java-volatile
// Volatile ensures all threads see the latest value of a volatile object and also ensures code execution order.
// Note: Synchronize also provides this, but it is more costly.
// Volatile also ensures no reordering of execution of the volatile variable assignments.
// Reordering is an optimization technique for performance improvements.
// Different components may apply this optimization:
// The processor may flush its write buffer in an order other than the program order.
// The processor may apply an out-of-order execution technique.
// The JIT compiler may optimize via reordering.

// Finally, Happens-Before Ordering
// Suppose thread A writes to a volatile variable x, and then thread B reads the same volatile variable x.
// In such cases, the values that were visible to A before writing the volatile variable x will be visible to B after reading the volatile variable x in B.
// i.e. assume a normal variable y is set to 5 before changing x in thread A, and we read x in thread B and then read y in thread B, then y will be read as 5 in B as well.
public class VolatileSample {
    private static final List<Counter> MAIN_THREAD_COUNTER = new ArrayList<>();
    private static final List<Counter> READER_COUNTER = new ArrayList<>();
    private static final int NUM_ITERATIONS = 10000000;
    private static volatile int sharedCounter = 0; // This is the counter read by the reader thread and incremented by the main thread.
    // Removing volatile here will cause stale data for the reader thread.

    public static void main(String[] args) {
        MAIN_THREAD_COUNTER.add(new Counter(System.nanoTime(), sharedCounter));
        long mainInstant;
        var reader = CompletableFuture.runAsync(() -> {
            long readerInstant;
            while (sharedCounter < NUM_ITERATIONS) {
                readerInstant = System.nanoTime(); // Since readerInstant and readerCounter are volatile, first the readerInstant will be assigned to System.nanoTime and then the readerCounter will be set to sharedCounter.
                READER_COUNTER.add(new Counter(readerInstant, sharedCounter));
            }
        });

        while (sharedCounter < NUM_ITERATIONS) {
            sharedCounter++;
            mainInstant = System.nanoTime();
            MAIN_THREAD_COUNTER.add(new Counter(mainInstant, sharedCounter));
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
                // but we only care no stale data ever happens. If you remove volatile in sharedCounter, stale data will appear.
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

