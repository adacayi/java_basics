package com.sanver.basics.threads;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.getThreadInfo;

/**
 * Demonstrates the use of virtual threads in Java 21.
 *
 * <p>
 * Virtual threads, introduced as a part of Project Loom, are lightweight threads
 * managed by the Java Virtual Machine (JVM) rather than the operating system. They
 * allow applications to handle thousands or even millions of concurrent tasks efficiently.
 * </p>
 *
 * <h2>Key Benefits of Virtual Threads</h2>
 * <ul>
 * <li>Low memory footprint compared to platform threads.</li>
 * <li>Efficient scheduling and management by the JVM.</li>
 * <li>Better scalability for concurrent applications.</li>
 * </ul>
 *
 * <p>
 * This example demonstrates how to use virtual threads for concurrent tasks
 * using the new `Thread.ofVirtual()` factory and `ExecutorService` for virtual threads.
 * </p>
 *
 * <h2>Disadvantages of Virtual Threads</h2>
 *
 * <p>While virtual threads offer significant benefits, such as lightweight concurrency
 * and scalability, they also come with some limitations and trade-offs. Developers
 * should consider these disadvantages before adopting virtual threads in their applications.</p>
 *
 * <h3>1. Blocking Operations and Compatibility</h3>
 * <ul>
 *   <li><b>Traditional Blocking I/O:</b> Virtual threads work best with non-blocking
 *   or properly integrated blocking I/O. Older libraries or custom code using non-standard
 *   blocking operations may not fully benefit from virtual threads.</li>
 *   <li><b>Third-party Library Compatibility:</b> Not all libraries are designed to
 *   work seamlessly with virtual threads, especially those that manage threads explicitly
 *   or expect a traditional threading model.</li>
 * </ul>
 *
 * <h3>2. Debugging and Monitoring Challenges</h3>
 * <ul>
 *   <li><b>Thread Explosion:</b> The ability to create millions of virtual threads
 *   can make debugging and monitoring more complex. For instance, thread dumps
 *   may become harder to interpret with a large number of threads.</li>
 *   <li><b>Tooling Limitations:</b> Older debugging, profiling, or logging tools
 *   may not yet fully support virtual threads, potentially complicating diagnostics.</li>
 * </ul>
 *
 * <h3>3. Context Switching Costs</h3>
 * <p>
 * While virtual threads reduce the cost of context switching compared to platform
 * threads, there is still some overhead. For scenarios with extremely tight CPU-bound
 * loops or microtasks, the overhead may outweigh the benefits.
 * </p>
 *
 * <h3>4. Increased Garbage Collection Pressure</h3>
 * <ul>
 *   <li><b>Short-lived Tasks:</b> Rapid creation and destruction of millions of virtual
 *   threads can increase garbage collection frequency, potentially impacting application
 *   performance.</li>
 * </ul>
 *
 * <h3>5. Deterministic Scheduling</h3>
 * <ul>
 *   <li><b>Non-deterministic Execution:</b> The JVM uses cooperative scheduling for
 *   virtual threads, which may lead to non-deterministic task execution. This could be
 *   problematic for applications requiring precise timing or prioritization.</li>
 * </ul>
 *
 * <h3>6. Overhead of Misuse</h3>
 * <ul>
 *   <li><b>Over-subscription:</b> Creating excessive virtual threads without need or
 *   controlling shared resources can lead to resource contention, degrading performance.</li>
 *   <li><b>Improper Design:</b> Over-reliance on virtual threads may encourage inefficient
 *   designs that create threads unnecessarily, instead of optimizing task execution.</li>
 * </ul>
 *
 * <h3>7. JVM Dependency</h3>
 * <p>
 * Virtual threads are managed entirely by the JVM. In multi-language applications, where
 * interoperability is required with other languages (e.g., Python or C++), the benefits of
 * virtual threads may not fully translate.
 * </p>
 *
 * <h3>8. Increased Complexity for Mixed Models</h3>
 * <p>
 * Applications combining both platform threads (e.g., for real-time guarantees) and virtual
 * threads may introduce complexity in coordinating between the two models.
 * </p>
 *
 * <h3>When Not to Use Virtual Threads</h3>
 * <ul>
 *   <li><b>Real-Time Systems:</b> Applications requiring hard real-time guarantees may not
 *   benefit from virtual threads due to the JVM's cooperative scheduling model.</li>
 *   <li><b>CPU-bound Workloads:</b> Virtual threads excel in I/O-bound tasks. For CPU-bound
 *   workloads, other concurrency techniques (e.g., {@code ForkJoinPool}) might be more appropriate.</li>
 *   <li><b>Uninstrumented Blocking Operations:</b> Applications relying on legacy or native
 *   libraries performing blocking operations may experience inefficiencies when using virtual threads.</li>
 * </ul>
 */

public class VirtualThreadSample {

    public static void main(String[] args) throws InterruptedException {
        // Example 1: Creating and starting a single virtual thread
        Thread virtualThread = Thread.ofVirtual().start(() ->
                System.out.println("Hello from a virtual thread! " + getThreadInfo())
        );

        // Wait for the virtual thread to finish
        virtualThread.join();

        // Example 2: Using an ExecutorService with virtual threads
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit multiple tasks
            for (int i = 0; i < 10; i++) {
                int taskId = i; // Effectively final variable for use in the lambda
                executor.submit(() ->
                        System.out.println("Task " + taskId + " running in a virtual thread. " + getThreadInfo())
                );
            }
        } // Automatically shuts down the executor

        // Example 3: Running thousands of tasks efficiently
        var threadIds = new ConcurrentSkipListSet<Long>();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100_000).forEach(i ->
                    executor.submit(() -> {
                                // Simulate some work
                                try {
                                    threadIds.add(Thread.currentThread().threadId());
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    System.err.println("Task interrupted");
                                }
                            }
                    ));
        }
        System.out.println("Submitted 100,000 tasks to virtual threads.");
        System.out.printf("Virtual thread count: %,d%n", threadIds.size());
        System.out.println(threadIds);

        System.out.println("All tasks completed.");
    }
}

