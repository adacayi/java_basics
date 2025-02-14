package com.sanver.basics.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * Demonstrates the usage of ReadWriteLock in Java.
 *
 * <p>
 * This example implements a shared resource (a simple list) where multiple threads
 * can read the data concurrently, but only one thread can write at a time.
 * </p>
 *
 * <h2>Key Features of ReadWriteLock</h2>
 * <ul>
 *   <li>Allows multiple threads to acquire a read lock simultaneously.</li>
 *   <li>Ensures that only one thread can acquire the write lock at any given time.</li>
 *   <li>Improves performance for scenarios with more frequent read operations.</li>
 * </ul>
 *
 * <h3>Common Methods of ReadWriteLock</h3>
 * <ul>
 *   <li>{@code readLock().lock()} - Acquires the read lock (multiple readers allowed).</li>
 *   <li>{@code readLock().unlock()} - Releases the read lock.</li>
 *   <li>{@code writeLock().lock()} - Acquires the write lock (exclusive access).</li>
 *   <li>{@code writeLock().unlock()} - Releases the write lock.</li>
 * </ul>
 */
public class ReadWriteLockExample {
    public static void main(String[] args) {
        var vault = new Vault<Integer>();
        int count = 10_000;

        measure(()-> {
                    var future1 = CompletableFuture.runAsync(() -> IntStream.range(0, count).parallel().forEach(i -> vault.get(0)));
                    var future2 = CompletableFuture.runAsync(() -> IntStream.range(0, count).parallel().forEach(vault::add));
                    CompletableFuture.allOf(future1, future2).join();
                });
        System.out.printf("Vault size: %,d%n", vault.size());
    }

    static class Vault<T> {
        private final List<T> values;
        private final ReadWriteLock lock = new ReentrantReadWriteLock(); // Try ReentrantLock instead to see the performance difference. Remove locks to see inconsistent behavior.

        public Vault() {
            this.values = new ArrayList<>();
        }

        public Vault(List<T> values) {
            this.values = values;
        }

        public boolean add(T value) {
            lock.writeLock().lock();
            try {
                return values.add(value);
            } finally {
                lock.writeLock().unlock();
            }
        }

        public T get(int i) {
            lock.readLock().lock();
            try {
                sleep(1); // This is added to show the benefit of acquiring multiple read locks simultaneously. Otherwise, managing overhead of read write locks will result in a ReentrantLock being more performant in a trivial operation like getLast.
                if (values.isEmpty()) {
                    return null;
                }

                return values.get(i);
            } finally {
                lock.readLock().unlock();
            }
        }

        public int size() {
            return values.size();
        }
    }
}
