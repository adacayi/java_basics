package com.sanver.basics.streamapi;

import java.util.stream.IntStream;

/**
 * A demonstration of the usage of the {@code sequential()} method in the Stream API.
 * <p>
 * The {@code sequential()} method is used to switch a stream to execute its pipeline in a sequential (non-parallel) mode.
 * Even if a stream was parallel before, calling {@code sequential()} switches it to sequential execution, ensuring that 
 * all operations on the stream will be executed in the order of the elements.
 * <p>
 * This example highlights the difference between parallel and sequential execution.
 */
public class SequentialSample {

    /**
     * The main method to demonstrate the usage of the {@code sequential()} method.
     *
     * @param args Command-line arguments (not used in this example).
     */
    public static void main(String[] args) {
        System.out.println("----- Running in parallel mode -----");
        IntStream.range(1, 10) // Generates a stream of integers from 1 to 9
                .parallel() // Operates in parallel mode
                .forEach(i -> System.out.printf("Processing %d in thread %d%n", i, Thread.currentThread().threadId()));

        System.out.printf("%n----- Switching to sequential mode -----%n");
        IntStream.range(1, 10) // Generates a stream of integers from 1 to 9
                .parallel() // Initially operates in parallel mode
                .sequential() // Switches to sequential mode
                .forEach(i -> System.out.printf("Processing %d in thread %d%n", i, Thread.currentThread().threadId()));
    }
}