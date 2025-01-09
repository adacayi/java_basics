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

    private static IntValue shared = new IntValue(0);

    public static void main(String[] args) {
        var numberFormat = NumberFormat.getInstance();
        var futures = IntStream.range(0, 10).mapToObj(x -> CompletableFuture.runAsync(() -> increment())).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.printf("Result without atomic reference: %,d%n", shared.getValue());

        shared = new IntValue(0);
        var atomicReference = new AtomicReference<>(shared);
        futures = IntStream.range(0, 10).mapToObj(x -> CompletableFuture.runAsync(() -> increment(atomicReference))).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.println("Result with AtomicReference using compareAndSet: " + numberFormat.format(atomicReference.get().getValue()));

        Runnable accumulate = () -> {
            for (int i = 0; i < 100_000; i++) {
                atomicReference.accumulateAndGet(new IntValue(4), (prev, x) -> new IntValue(prev.getValue() + x.getValue()));
                // This executes the binary operator and sets the value to the atomic reference if the previous value at the start of the operation remained the same (reference equality).
                // If not, it will set the previous value to current value and apply the binary operator again until it is successful.
                // So no need for a loop here, contrary to the case in compareAndSet.
                // Important: Make sure that the binary operator returns an object not equal (!=) to the prev object, otherwise even though its properties might have changes, since the reference is the same,
                // other threads concurrently changing the same value will think no change has occurred after they read the initial value, so they will apply their change to an older data, resulting in data loss.
                // For example, try this, and you  might not see the desired sum: atomicReference.accumulateAndGet(new IntValue(4), (prev, x) -> prev.increment(x)) because prev.increment() is equal to prev.
            }
        };

        futures = IntStream.range(0, 10).mapToObj(x -> CompletableFuture.runAsync(accumulate)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.println("Result after accumulating to compare and set value: " + numberFormat.format(atomicReference.get().getValue()));
    }

    private static void increment() {
        for (int i = 0; i < 100_000; i++) {
            shared = new IntValue(shared.getValue() + 3);
        }
    }

    private static void increment(AtomicReference<IntValue> value) {
        for (int i = 0; i < 100_000; i++) {
            boolean incremented = false;

            while (!incremented) { // If this while block did not exist, the final value will be less than 3,000,000.
                IntValue existingValue = value.get();
                IntValue newValue = new IntValue(existingValue.getValue() + 3);
                // This will try to set the AtomicReference value to the new value, if the current value is the same object
                // (not equals) i.e. existingValue object. If that is the case it will return true, else it will return false.
                incremented = value.compareAndSet(existingValue, newValue);
            }
        }
    }

    private static class IntValue {
        private int value;

        public IntValue(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public IntValue increment(IntValue increment) {
            value += increment.getValue();
            return this;
        }
    }
}
