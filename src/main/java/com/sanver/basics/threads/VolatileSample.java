package com.sanver.basics.threads;

import java.text.NumberFormat;
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
    private static final int LIMIT = 10_000_000;
    private static final List<InstantValue> mainThreadData = new ArrayList<>();
    private static final List<InstantValue> readerThreadData = new ArrayList<>();
    private static volatile int value = 0; // This is the value read by the reader thread and incremented by the main thread.
    // Removing volatile here will cause stale data for the reader thread.

    public static void main(String[] args) {
        var reader = CompletableFuture.runAsync(() -> {
            while (value < LIMIT) {
                readerThreadData.add(new InstantValue(System.nanoTime(), value));
            }
        });

        for (int i = 0; i < LIMIT; i++) {
            value++;
            mainThreadData.add(new InstantValue(System.nanoTime(), value));
        }

        reader.join();

        checkIfStaleDataExists(mainThreadData,  readerThreadData);
    }

    static void checkIfStaleDataExists(List<InstantValue> mainThreadData, List<InstantValue> readerThreadData) {
        var staleDataExists = false;

        int j = 0;
        var numberFormat = NumberFormat.getInstance();

        for (var readerData : readerThreadData) {
            var readerThreadTime = readerData.instant;
            var readerThreadValue = readerData.value;

            while (++j < mainThreadData.size() && mainThreadData.get(j).instant <= readerThreadTime); // We start checking from j = 1, because if the first element added to readerThreadData is before the first element of mainThreadData, j-- will result in j = -1 otherwise.

            j--;

            var mainThreadTime = mainThreadData.get(j).instant;
            var mainThreadValue = mainThreadData.get(j).value;

            if (mainThreadTime < readerThreadTime && mainThreadValue > readerThreadValue) {
                System.out.println("mainThreadTime   = " + numberFormat.format(mainThreadTime) + " mainThreadValue   = " + numberFormat.format(mainThreadValue));
                System.out.println("readerThreadTime = " + numberFormat.format(readerThreadTime) + " readerThreadValue = " + numberFormat.format(readerThreadValue));
                System.out.println();
                staleDataExists = true;
            }
        }

        var staleDataState = (staleDataExists ? "exists" : "does not exist") + ".";
        System.out.println("Stale data " + staleDataState);
    }

    record InstantValue(long instant, int value) {
    }
}

