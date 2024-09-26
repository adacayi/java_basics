package com.sanver.basics.threads;

import com.sanver.basics.utils.RethrowAsUnchecked.ThrowingCallable;
import com.sanver.basics.utils.RethrowAsUnchecked.ThrowingFunction;

import java.text.NumberFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.RethrowAsUnchecked.ThrowingRunnable;
import static com.sanver.basics.utils.Utils.sleepNano;

public class ThreadSleepPrecision {
    private static final int NANOS = 300_000_000;
    private static final int MILLIS = NANOS / 1_000_000;
    private static final NumberFormat FORMATTER = NumberFormat.getInstance();
    private static final ThrowingFunction<ThrowingCallable<Long>, Long> MEASURE = x -> {
        var start = System.nanoTime();
        var end = x.call(); // Execution finish time is get through the Callable to avoid calculating the time spent for future.get() for ScheduledExecutorService.
        var duration = end - start;
        System.out.println(FORMATTER.format(duration));
        return duration;
    };

    public static void main(String[] args) throws Throwable {
        var lock = new Object();
        var scheduler = Executors.newScheduledThreadPool(1);

        printResults("Loop with System.nanoTime to wait results (Busy waiting): ", () -> sleepNano(NANOS));
        printResults("Thread.sleep results:", () -> Thread.sleep(MILLIS));
        printResults("LockSupport.parkNanos results:", () -> LockSupport.parkNanos(NANOS));
        printResults("Object.wait results:", () -> {
            synchronized (lock) {
                lock.wait(MILLIS);
            }
        });
        printResults("ScheduledExecutorService.schedule results:", () -> scheduler.schedule(System::nanoTime, NANOS, TimeUnit.NANOSECONDS).get());

        scheduler.shutdown();
    }

    private static void printResults(String message, ThrowingRunnable operation) {
        printResults(message, () -> {
            operation.run();
            return System.nanoTime();
        });
    }

    private static void printResults(String message, ThrowingCallable<Long> operation) {
        System.out.println(message);
        var result = average(operation);
        System.out.printf("Average difference: %s%n%n", FORMATTER.format(Math.abs(result - NANOS)));
    }

    private static double average(ThrowingCallable<Long> operation) {
        var skip = 3;
        var count = 10;
        return IntStream.range(0, count + skip).mapToLong(x -> {
            try {
                return MEASURE.apply(operation);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }).skip(skip).average().orElse(0); // Skip initial results for System.nanoTime warmup
    }
}
