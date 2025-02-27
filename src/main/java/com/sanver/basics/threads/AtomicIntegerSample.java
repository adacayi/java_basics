package com.sanver.basics.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * This class demonstrates the usage of {@link java.util.concurrent.atomic.AtomicInteger} for thread-safe
 * integer operations in a concurrent environment. It highlights the importance
 * of atomic operations when multiple threads modify a shared integer value.
 *
 * <p>
 * {@link java.util.concurrent.atomic.AtomicInteger} provides methods that guarantee atomicity, ensuring that
 * operations like incrementing, decrementing, adding, and setting the value
 * are performed as a single, uninterruptible unit. This prevents race conditions
 * and data corruption that can occur when multiple threads try to modify a
 * regular `int` variable simultaneously.
 * </p>
 *
 * <p>
 * <b>Commonly Used Methods:</b>
 * </p>
 * <ul>
 *   <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#get() get()}:</b>
 *     Retrieves the current value of the AtomicInteger. This method provides a
 *     snapshot of the current value. It does not modify the value, and it is
 *     guaranteed to return the most up-to-date value as of the moment it is called.
 *     <p>
 *       <i>Example:</i>
 *       {@code int currentValue = atomicInteger.get();}
 *     </p>
 *   </li>
 *   <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#set(int) set(int newValue)}:</b>
 *     Atomically sets the value to the given updated value. This operation is
 *     performed as a single, uninterruptible step, ensuring that no other thread
 *     can modify the value between the time it's read and when it's written.
 *     <p>
 *       <i>Example:</i>
 *       {@code atomicInteger.set(100);}
 *     </p>
 *   </li>
 *   <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#getAndSet(int) getAndSet(int newValue)}:</b>
 *     Atomically sets the value to the given updated value and returns the
 *     *previous* value. This is useful when you need to know the old value
 *     immediately after updating it. The operation is atomic, meaning it's
 *     performed as a single, uninterruptible step.
 *     <p>
 *       <i>Example:</i>
 *       {@code int previousValue = atomicInteger.getAndSet(200);}
 *     </p>
 *   </li>
 *   <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#incrementAndGet() incrementAndGet()}:</b>
 *     Atomically increments the current value by one and returns the *updated*
 *     value. This method is a thread-safe way to perform the `++` operation.
 *     <p>
 *       <i>Example:</i>
 *       {@code int newValue = atomicInteger.incrementAndGet();}
 *     </p>
 *   </li>
 *   <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#decrementAndGet() decrementAndGet()}:</b>
 *     Atomically decrements the current value by one and returns the *updated*
 *     value. This method is a thread-safe way to perform the `--` operation.
 *     <p>
 *       <i>Example:</i>
 *       {@code int newValue = atomicInteger.decrementAndGet();}
 *     </p>
 *   </li>
 *   <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#getAndIncrement() getAndIncrement()}:</b>
 *     Atomically increments the current value by one and returns the *previous*
 *     value.
 *     <p>
 *       <i>Example:</i>
 *       {@code int previousValue = atomicInteger.getAndIncrement();}
 *     </p>
 *   </li>
 *    <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#getAndDecrement() getAndDecrement()}:</b>
 *     Atomically decrements the current value by one and returns the *previous*
 *     value.
 *     <p>
 *       <i>Example:</i>
 *       {@code int previousValue = atomicInteger.getAndDecrement();}
 *     </p>
 *   </li>
 *   <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#addAndGet(int) addAndGet(int delta)}:</b>
 *     Atomically adds the given value to the current value and returns the
 *     *updated* value. This is equivalent to += in non-atomic operations.
 *     <p>
 *       <i>Example:</i>
 *       {@code int newValue = atomicInteger.addAndGet(5);}
 *     </p>
 *   </li>
 *    <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#getAndAdd(int) getAndAdd(int delta)}:</b>
 *     Atomically adds the given value to the current value and returns the
 *     *previous* value.
 *     <p>
 *       <i>Example:</i>
 *       {@code int previousValue = atomicInteger.getAndAdd(5);}
 *     </p>
 *   </li>
 *   <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#accumulateAndGet(int, java.util.function.IntBinaryOperator) accumulateAndGet(int x, java.util.function.IntBinaryOperator accumulatorFunction)}:</b>
 *     Atomically updates the current value with the results of applying the
 *     given function, returning the updated value. The `accumulatorFunction`
 *     takes two integer arguments: the current value and the given value.
 *     <p>
 *       <i>Example:</i>
 *       {@code int newValue = atomicInteger.accumulateAndGet(3, (prev, x) -> prev + 2 * x);}
 *     </p>
 *   </li>
 *   <li>
 *     <b>{@link java.util.concurrent.atomic.AtomicInteger#getAndUpdate(java.util.function.IntUnaryOperator) getAndUpdate(java.util.function.IntUnaryOperator updateFunction)}:</b>
 *        Atomically updates the current value with the results of applying the given function, returning the *previous* value. The `updateFunction`
 *        takes one integer argument: the current value.
 *     <p>
 *         <i>Example:</i>
 *         {@code int previousValue = atomicInteger.getAndUpdate(prev->prev+5);}
 *     </p>
 *   </li>
 *   <li>
 *      <b>{@link java.util.concurrent.atomic.AtomicInteger#updateAndGet(java.util.function.IntUnaryOperator) updateAndGet(java.util.function.IntUnaryOperator updateFunction)}:</b>
 *          Atomically updates the current value with the results of applying the given function, returning the *updated* value. The `updateFunction`
 *          takes one integer argument: the current value.
 *      <p>
 *          <i>Example:</i>
 *          {@code int newValue = atomicInteger.updateAndGet(prev->prev+5);}
 *      </p>
 *   </li>
 *
 * </ul>
 *
 * <p>
 * This example showcases the use of several of these methods to perform
 * multiple operations in a concurrent environment, ensuring that the final
 * result is accurate.
 * </p>
 */
public class AtomicIntegerSample {
    public static final AtomicInteger sum = new AtomicInteger(0); // Change this to int and increment sum by 3 inside the for loop to see the result might not be equal to 3,000,000.

    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i = 0; i < 100_000; i++) {
                sum.incrementAndGet(); // Increment by 1
                sum.accumulateAndGet(3, (prev, x) -> prev + 2 * x); // Increment by 6. The first operand in the binary operator (prev) is the AtomicInteger, and the second one (x) is the first parameter of the accumulateAndGet method (i.e. 3 in this example). Thus, this means incrementing the value by 6.
                sum.addAndGet(5);
                // sum.getAndSet(sum.get() - 1); // This is not thread-safe since different threads can get the same value with sum.get() - 1, before passing it to the getAndSet method
                // The primary use case for getAndSet() is when you need to:
                //Atomically set a variable to a new value.
                //Simultaneously know what the previous value was.
                sum.decrementAndGet(); // Decrement by 1
            }
        };

        var futures = IntStream.range(0, 10).mapToObj(i -> CompletableFuture.runAsync(runnable)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(futures).join();
        System.out.printf("Sum = %,d%n", sum.get());
    }
}
