package com.sanver.basics.time;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.locks.LockSupport;

import static com.sanver.basics.utils.Utils.sleepNano;

/*
System.nanoTime() directly is generally more precise than using Instant.now() for measuring short durations of code execution within the same JVM instance. Here's why:
1.	Higher Resolution: System.nanoTime() provides nanosecond resolution, while Instant.now() usually has a resolution of microseconds or even milliseconds, depending on the platform and JVM.
2.	Monotonic Clock: System.nanoTime() is based on a monotonic clock, meaning it measures the time elapsed since some arbitrary starting point. This makes it ideal for measuring durations, as it's not affected by adjustments to the system clock (like time zone changes or user adjustments). Instant may not use the monotonic clock.
3.	Direct Measurement: System.nanoTime() directly accesses the high-resolution timer of the underlying system. Instant.now(), while internally using System.currentTimeMillis() or a similar mechanism, involves additional overhead due to object creation and potential conversions.

Considerations:
Overhead:  System.nanoTime() does have a small overhead. For very short code segments, this overhead could be significant relative to the duration being measured.
Warm-up:  The first few calls to System.nanoTime() might take longer due to JVM warm-up. Consider making a few dummy calls before starting the actual measurement.
 */
public class NanoTimeSample {
    public static void main(String[] args) throws InterruptedException {
        var formatter = NumberFormat.getInstance();
        var thread1 = new Thread(() -> {
            LockSupport.park();
            var start = System.nanoTime();
            sleepNano(10);
            var end = System.nanoTime();
            var duration = formatter.format(end - start);
            System.out.printf("Duration calculated with System.nanoTime: %s ns%n", duration);
        });
        var thread2 = new Thread(() -> {
            LockSupport.park();
            var startInstant = Instant.now();
            sleepNano(10);
            var endInstant = Instant.now(); // Since Instant.now() is not in nanosecond precision, startInstant end endInstant get the same value,
            // hence the duration becomes zero.
            // However, this is does not mean Instant.now is running faster, actually the opposite is true, System.nano is faster,
            // since thread2 is unlocked first but thread1 output comes first to the console suggesting it finishes first, although started later.
            var duration = formatter.format(Duration.between(startInstant, endInstant).toNanos());
            System.out.printf("Duration calculated with Instant.now    : %s ns%n", duration);
        });
        thread1.start();
        thread2.start();
        LockSupport.unpark(thread2);
        LockSupport.unpark(thread1);
        thread1.join();
        thread2.join();
    }
}
