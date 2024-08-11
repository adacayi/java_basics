package com.sanver.basics.threads;

import java.text.NumberFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

/**
 * Semaphore is a synchronization construct that controls access to a shared resource by maintaining a set of permits.
 * It's essentially a counter that determines how many threads can access the resource simultaneously.
 * You can create a fair semaphore (new Semaphore(int permits, true)) to ensure that threads are granted permits in the order they requested them. This prevents thread starvation.
 * <br><br>
 * <b>Key Concepts:</b><br>
 * <br><b>Permits:</b> Each Semaphore has a certain number of permits. A thread must acquire a permit before it can access the shared resource.
 * <br><b>acquire():</b> A thread calls acquire() to request a permit. If a permit is available, the thread takes it and proceeds. If not, the thread blocks (waits) until a permit becomes available.
 * <br><b>release():</b> When a thread is done using the resource, it calls release() to return the permit to the Semaphore. This makes the permit available for another thread to acquire.
 * <br><br>
 * <b>Key Points:</b><br>
 * <ul>
 * <li>Semaphores are useful when you need to control access to a shared resource but don't necessarily need mutual exclusion (where only one thread can access the resource at a time).</li>
 * <li>Unlike locks, semaphores don't have an owner. Any thread can release a permit, even if it didn't acquire it.</li>
 * </ul>
 */
public class SemaphoreSample {
    public static void main(String[] args) {
        var value = new AtomicInteger();
        var concurrentProcessCount = new AtomicInteger();
        var permits = 3;
        var semaphore = new Semaphore(permits);

        var futures = IntStream.range(0, 10).mapToObj(x -> CompletableFuture.runAsync(()->{
            for (int i = 0; i < 2_000; i++) {
                semaphore.acquireUninterruptibly();
                try {
                    concurrentProcessCount.getAndIncrement();
                    value.getAndIncrement();
                    sleep(1); // Simulate some work
                    concurrentProcessCount.getAndDecrement();
                } finally {
                    semaphore.release();
                }
            }
        })).toArray(CompletableFuture[]::new);

        var all = CompletableFuture.allOf(futures);
        checkConcurrentProcessCount(all, concurrentProcessCount, permits);
        all.join();
        var numberFormat = NumberFormat.getInstance();
        System.out.println("Final value: " + numberFormat.format(value.get()));
    }

    private static void checkConcurrentProcessCount(CompletableFuture<Void> all, AtomicInteger processing, int permits) {
        CompletableFuture.runAsync(()->{
            while (!all.isDone()) {
                var processCount = processing.get();

                if (processCount > permits) {
                    System.out.println("Too many concurrent processes: " + processCount);
                }
            }
        });
    }
}
