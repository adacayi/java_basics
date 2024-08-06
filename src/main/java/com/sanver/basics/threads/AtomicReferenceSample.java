package com.sanver.basics.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.LambdaExceptionUtil.rethrowConsumer;
import static com.sanver.basics.utils.LambdaExceptionUtil.rethrowRunnable;

public class AtomicReferenceSample {

    private static final int THREAD_COUNT = 10;
    private static final AtomicReference<IntValue> ATOMIC_REFERENCE = new AtomicReference<>(new IntValue(0));
    private static final CountDownLatch COUNT_DOWN_LATCH = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) {
        var executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        Runnable increment = rethrowRunnable(() -> increment(ATOMIC_REFERENCE));
        var futures = IntStream
                .range(0, THREAD_COUNT)
                .mapToObj(x -> executorService.submit(increment))
                .toList();
        futures.forEach(rethrowConsumer(Future::get));
        System.out.println(ATOMIC_REFERENCE.get().getValue());
        executorService.shutdown();
    }

    public static void increment(AtomicReference<IntValue> value) throws InterruptedException {
        COUNT_DOWN_LATCH.countDown();
        COUNT_DOWN_LATCH.await();

        for (int i = 0; i < 1000; i++) {
            boolean incremented = false;

            while (!incremented) {
                var existingValue = value.get();
                var newValue = new IntValue(existingValue.getValue() + 1);
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
