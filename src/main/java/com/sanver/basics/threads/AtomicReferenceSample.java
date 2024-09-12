package com.sanver.basics.threads;

import java.text.NumberFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;

/**
 * The AtomicReference class in Java is part of the java.util.concurrent.atomic package and provides a way to work with object references atomically.
 * This is useful in concurrent programming where multiple threads may need to update a shared reference without causing race conditions.
 * <br><br>
 * <b>Key Points:</b><br>
 * <br><b>Atomic Operations:</b> Operations such as get, set, compareAndSet, and getAndSet on an AtomicReference are atomic. This means they are performed as a single, indivisible operation.
 * <br><b>Thread Safety:</b> AtomicReference ensures that when one thread updates the reference, other threads see the updated value immediately without additional synchronization.
 * <br><b>Use Cases:</b> It's commonly used in scenarios like non-blocking algorithms, where you need to ensure that a reference is updated only if it currently holds a specific value.
 */
public class AtomicReferenceSample {

    private static final int THREAD_COUNT = 10;
    private static final AtomicReference<IntValue> ATOMIC_REFERENCE = new AtomicReference<>(new IntValue(0));
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        var executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        Callable<Void> increment = () -> {
            uncheck(() -> increment(ATOMIC_REFERENCE));
            return null;
        };
        var list = IntStream.range(0, 10).mapToObj(x -> increment).toList();
        executorService.invokeAll(list);
        System.out.println(NumberFormat.getInstance().format(ATOMIC_REFERENCE.get().getValue()));
        executorService.shutdown();
    }

    public static void increment(AtomicReference<IntValue> value) throws InterruptedException {
        COUNT_DOWN_LATCH.countDown();
        COUNT_DOWN_LATCH.await();

        for (int i = 0; i < 1000; i++) {
            boolean incremented = false;

            while (!incremented) { // If this while block did not exist, the final value will be less than 10,000.
                IntValue existingValue = value.get();
                IntValue newValue = new IntValue(existingValue.getValue() + 1);
                // This will try to set the AtomicReference value to the new value, if the current value is the same object
                // (not equals) i.e. existingValue object. If that is the case it will return true else false.
                incremented = value.compareAndSet(existingValue, newValue);
            }
        }
    }

    static class IntValue {
        private final int value;

        public IntValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
