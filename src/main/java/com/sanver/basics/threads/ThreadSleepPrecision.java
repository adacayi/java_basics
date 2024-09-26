package com.sanver.basics.threads;

import com.sanver.basics.utils.RethrowAsUnchecked.ThrowingFunction;

import java.text.NumberFormat;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.RethrowAsUnchecked.ThrowingRunnable;
import static com.sanver.basics.utils.Utils.sleepNano;

public class ThreadSleepPrecision {
    private static final int NANOS = 1_000_000_000;
    private static final int MILLIS = NANOS / 1_000_000;
    private static final NumberFormat FORMATTER = NumberFormat.getInstance();
    private static final ThrowingFunction<ThrowingRunnable, Long> MEASURE = x -> {
        var start = System.nanoTime();
        x.run();
        var end = System.nanoTime();
        var duration = end - start;
        System.out.println(FORMATTER.format(duration));
        return duration;
    };

    public static void main(String[] args) throws Throwable {
        var lock = new Object();
        printResults("Loop with System.nanoTime to wait results (Busy waiting): ", () -> sleepNano(NANOS));
        printResults("Thread.sleep results:", () -> Thread.sleep(MILLIS));
        printResults("LockSupport.parkNanos results:", () -> LockSupport.parkNanos(NANOS));
        printResults("Object.wait results:", () -> {
            synchronized(lock) {
                lock.wait(MILLIS);
            }
        });
    }

    private static void printResults(String message, ThrowingRunnable operation) {
        System.out.println(message);
        var result = average(operation);
        System.out.printf("Average difference: %s%n%n", FORMATTER.format(Math.abs(result - NANOS)));
    }

    private static double average(ThrowingRunnable operation) {
        return IntStream.range(0, 11).mapToLong(x -> {
            try {
                return MEASURE.apply(operation);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }).skip(1).average().orElse(0); // Skip the first result for System.nanoTime warmup
    }
}
