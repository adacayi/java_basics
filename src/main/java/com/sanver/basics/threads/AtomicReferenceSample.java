package com.sanver.basics.threads;

import java.text.NumberFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

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

    public static void main(String[] args) {
        var atomicReference = new AtomicReference<>(new IntValue(0));
        var futures = IntStream.range(0, 10).mapToObj(x -> CompletableFuture.runAsync(() -> increment(atomicReference))).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.println(NumberFormat.getInstance().format(atomicReference.get().getValue()));
    }

    public static void increment(AtomicReference<IntValue> value) {
        for (int i = 0; i < 100_000; i++) {
            boolean incremented = false;

            while (!incremented) { // If this while block did not exist, the final value will be less than 10,000.
                IntValue existingValue = value.get();
                IntValue newValue = new IntValue(existingValue.getValue() + 3);
                // This will try to set the AtomicReference value to the new value, if the current value is the same object
                // (not equals) i.e. existingValue object. If that is the case it will return true, else it will return false.
                incremented = value.compareAndSet(existingValue, newValue);
            }
        }
    }

    private static class IntValue {
        private final int value;

        public IntValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
