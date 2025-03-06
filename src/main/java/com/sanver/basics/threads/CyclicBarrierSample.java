package com.sanver.basics.threads;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * This class demonstrates the usage of the {@link java.util.concurrent.CyclicBarrier}
 * class in Java for synchronizing multiple threads.
 * </p>
 * <p>
 * <b>CyclicBarrier</b> is a synchronization aid that allows a set of threads to all wait for each other to reach a common barrier point.
 * Barriers are useful in programs involving a fixed sized party of threads that must occasionally wait for each other.
 * The barrier is called <i>cyclic</i> because it can be re-used after the waiting threads are released.
 * </p>
 * <p>
 * The {@code CyclicBarrier} class is part of the {@code java.util.concurrent} package and
 * provides a way to synchronize a group of threads at a certain point (the barrier).
 * It's particularly useful when you need to wait for multiple tasks to complete before
 * proceeding to the next stage of a computation or processing.
 * </p>
 *
 * <h2>Key Features of CyclicBarrier:</h2>
 * <ul>
 *     <li><b>Synchronization Point</b>: Acts as a barrier where threads wait for each other.</li>
 *     <li><b>Reusability</b>: Can be reset and used repeatedly after the barrier is tripped.</li>
 *     <li><b>Runnable Action</b>: An optional {@link Runnable} task can be provided to be executed
 *     when all threads reach the barrier.</li>
 *     <li><b>Parties</b>: The number of threads that must reach the barrier to trip it.</li>
 *     <li><b>Waiting</b>: Threads wait at the barrier using the {@link CyclicBarrier#await()} method.</li>
 *     <li><b>Barrier Tripping</b>: Once all threads have invoked {@link CyclicBarrier#await()},
 *     the barrier is tripped, the optional task is executed and the threads are released.</li>
 *     <li><b>Broken Barrier</b>: If a thread leaves the barrier early, the barrier is considered broken.</li>
 * </ul>
 *
 * <h2>Common Methods of CyclicBarrier:</h2>
 * <ul>
 *   <li>{@link CyclicBarrier#CyclicBarrier(int)}: Constructs a new {@code CyclicBarrier}
 *   that will trip when the given number of parties (threads) are waiting upon it.</li>
 *   <li>{@link CyclicBarrier#CyclicBarrier(int, Runnable)}: Constructs a new {@code CyclicBarrier}
 *   that will trip when the given number of parties (threads) are waiting upon it, and which
 *   will execute the given barrier action when the barrier is tripped.</li>
 *   <li>{@link CyclicBarrier#await()}: Waits until all parties have invoked {@code await} on this barrier.</li>
 *   <li>{@link CyclicBarrier#await(long, TimeUnit)}: Waits until all parties have invoked {@code await}
 *   on this barrier, or the specified waiting time elapses.</li>
 *   <li>{@link CyclicBarrier#reset()}: Resets the barrier to its initial state. If any parties are currently
 *   waiting at the barrier, they will return with a {@link BrokenBarrierException}.</li>
 *   <li>{@link CyclicBarrier#getParties()}: Returns the number of parties required to trip this barrier.</li>
 *   <li>{@link CyclicBarrier#isBroken()}: Queries if this barrier is in a broken state.</li>
 *   <li>{@link CyclicBarrier#getNumberWaiting()}: Returns the number of parties currently waiting at the barrier.</li>
 * </ul>
 *
 * <h2>Potential Exceptions</h2>
 * <ul>
 *   <li>{@link InterruptedException}: If the current thread is interrupted while waiting.</li>
 *   <li>{@link BrokenBarrierException}: If another thread was interrupted or timed out while
 *   waiting, or the barrier was reset, or the barrier action failed.</li>
 * </ul>
 */
public class CyclicBarrierSample {

    private static final int NUM_THREADS = 3;

    public static void main(String[] args) {
        // Create a CyclicBarrier that waits for 3 threads.
        // This barrier will execute the provided Runnable once all threads reach the barrier
        CyclicBarrier barrier = new CyclicBarrier(NUM_THREADS, () ->
                System.out.println("Barrier tripped! All threads have reached the synchronization point."));

        try (var executor = Executors.newFixedThreadPool(NUM_THREADS)) {
            for (int i = 0; i < NUM_THREADS; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    try {
                        System.out.println("Thread " + threadId + " is working...");
                        // Simulate some work being done.
                        TimeUnit.SECONDS.sleep(1 + threadId);
                        System.out.println("Thread " + threadId + " reached the barrier.");
                        // Wait for all threads to reach the barrier.
                        barrier.await();
                        System.out.println("Thread " + threadId + " passed the barrier. Continuing...");
                        TimeUnit.SECONDS.sleep(1 + threadId);
                        System.out.println("Thread " + threadId + " finished its second part of the work.");
                        barrier.await();
                        System.out.println("Thread " + threadId + " passed the second barrier. Finishing...");
                    } catch (InterruptedException | BrokenBarrierException e) {
                        System.out.println("Thread " + threadId + " interrupted or broken barrier: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}