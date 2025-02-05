package com.sanver.basics.time;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * <p>System.nanoTime() is generally more precise than using Instant.now() for measuring
 * short durations of code execution within the same JVM instance.</p>
 *
 * <h2>Reasons:</h2>
 * <ul>
 *   <li><b>Higher Resolution:</b> {@code System.nanoTime()} provides nanosecond resolution,
 *       while {@code Instant.now()} typically has microsecond or millisecond precision,
 *       depending on the platform and JVM.</li>
 *   <li><b>Monotonic Clock:</b> {@code System.nanoTime()} is based on a monotonic clock,
 *       meaning it measures elapsed time from an arbitrary starting point.
 *       This makes it ideal for measuring durations, as it is not affected by
 *       system clock adjustments (such as time zone changes or manual modifications).
 *       {@code Instant.now()} may not always use a monotonic clock.</li>
 *   <li><b>Direct Measurement:</b> {@code System.nanoTime()} directly accesses
 *       the high-resolution timer of the underlying system. In contrast,
 *       {@code Instant.now()}, which internally relies on {@code System.currentTimeMillis()}
 *       or similar mechanisms, has additional overhead due to object creation
 *       and potential conversions.</li>
 * </ul>
 *
 * <h2>Considerations:</h2>
 * <ul>
 *   <li><b>Overhead:</b> {@code System.nanoTime()} has a small performance overhead.
 *       For extremely short code segments, this overhead could be significant relative
 *       to the duration being measured.</li>
 *   <li><b>Warm-up:</b> The first few calls to {@code System.nanoTime()} might take
 *       longer due to JVM optimizations and warm-up. It is advisable to perform
 *       a few dummy calls before starting actual measurements.</li>
 * </ul>
 */

public class NanoTimeSample {

    public static final int NANOS = 100_000_000;

    public static void main(String[] args) {
        demonstrateMeasurementIntervals();
        var latch = new CountDownLatch(1);
        var future1 = CompletableFuture.runAsync(() -> {
            uncheck(() -> latch.await());
            var start = System.nanoTime();
            sleepNano(NANOS);
            var end = System.nanoTime();
            System.out.printf("Duration calculated with System.nanoTime: %,11d ns. Notice this is always greater than (or equal to) %,d and much closer to %,d%n", end - start, NANOS, NANOS);
        });

        var future2 = CompletableFuture.runAsync(() -> {
            uncheck(() -> latch.await());
            var startInstant = Instant.now();
            sleepNano(NANOS);
            var endInstant = Instant.now();
            System.out.printf("Duration calculated with Instant.now    : %,11d ns. Notice this might be even less than %,d, because wait is done with System.nanoTime(), but this uses Instant.now(). The difference with %,d is much larger as well. %n", Duration.between(startInstant, endInstant).toNanos(), NANOS, NANOS);
        });
        latch.countDown();
        future1.join();
        future2.join();
        sleep(5_000);
    }

    private static void demonstrateMeasurementIntervals() {
        var formatter = NumberFormat.getInstance();
        final int length = 1_000_000;
        long[] duration = new long[length];
        var start = System.nanoTime();
        long end;
        for (int i = 0; i < length; i++) {
            end = System.nanoTime();
            duration[i] = end - start;
            start = end;
        }
        System.out.println("Distinct duration intervals");
        System.out.println(Arrays.stream(duration).distinct().sorted().limit(100).mapToObj(formatter::format).collect(Collectors.joining("%n".formatted())));
    }

    /**
     * Tries to achieve wait for the given nanos.
     * We introduced this method here, because with the {@link com.sanver.basics.utils.Utils#sleep(long)}, the durations become much higher.
     * Try removing this and using the {@link com.sanver.basics.utils.Utils#sleep(long)}
     *
     * @param nanos nanoseconds to wait for
     */
    private static void sleepNano(long nanos) {
        var start = System.nanoTime();
        while (System.nanoTime() - start < nanos) ;
    }
}
