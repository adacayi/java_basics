package com.sanver.basics.threads;

import com.sanver.basics.utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;
import static com.sanver.basics.utils.RethrowAsUnchecked.uncheck;
import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleepNano;

/**
 * Demonstrates the use of virtual threads in Java 21.
 * <p>Check <a href = "https://www.baeldung.com/java-virtual-thread-vs-thread">Virtual thread vs thread</a>
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
 * <a href = "https://www.javatpoint.com/context-switching-in-java">Context switching</a>
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

    public static final int THREAD_COUNT = 1_000_000;

    public static void main(String[] args) {
        System.out.printf("Example 1: Creating and starting multiple virtual threads with Thread.startVirtualThread()%n%n");
        var threads = IntStream.rangeClosed(1, 3).mapToObj(i -> Thread.startVirtualThread(getRunnable(i + ":"))).toList(); // Virtual threads are daemon threads with no name, but we can give name to them.
        threads.forEach(x -> uncheck(() -> x.join()));// Wait for the virtual threads to finish

        sleep();
        System.out.printf("%nExample 2: Creating and starting multiple platform threads with Thread.ofPlatform().unstarted()%n%n");
        System.out.println("While virtual threads are daemon threads, platform threads generated by Thread.ofPlatform() are not daemon threads by default, but they can be set to be daemon");
        Thread.Builder.OfPlatform builder = Thread.ofPlatform();
        builder.name("my-platform-thread-", 1); // start index will be incremented with each thread built with the Thread.Builder.
//        builder.daemon(true); // This is to show how to set platform threads as daemon
        threads = IntStream.rangeClosed(1, 3).mapToObj(i -> builder.unstarted(getRunnable(i + ":"))).toList(); // Creates a new Thread from the current state of the builder to run the given task.
        // The Thread's start method must be invoked to schedule the thread to execute.
        // Notice that the suffix is incremented by one for each thread.
        threads.forEach(Thread::start);
        threads.forEach(x -> uncheck(() -> x.join()));

        sleep();
        System.out.printf("%nExample 3: Creating and starting multiple virtual threads with Thread.ofVirtual().unstarted()%n%n");
        Thread.Builder.OfVirtual threadBuilder = Thread.ofVirtual();
        // Notice there is no daemon() method for Thread.Builder.OfVirtual, unlike Thread.Builder.OfPlatform.
        threadBuilder.name("my-virtual-thread-", 1); // start index will be incremented with each thread built with the Thread.Builder.
        threads = IntStream.rangeClosed(1, 3).mapToObj(i -> threadBuilder.unstarted(getRunnable(i + ":"))).toList(); // Creates a new Thread from the current state of the builder to run the given task.
        // The Thread's start method must be invoked to schedule the thread to execute.
        // Thread.currentThread().toString() also contains information about the carrier thread (the platform thread that is used for the virtual thread)
        // Notice that the suffix is incremented by one for each virtual thread.
        // Also notice that thread id is unique for each virtual thread even they might share the same platform thread
        threads.forEach(Thread::start);
        threads.forEach(x -> uncheck(() -> x.join()));


        sleep();
        System.out.printf("%nExample 4: Creating and starting multiple virtual threads with Thread.ofVirtual().start()%n%n");
        threads = IntStream.rangeClosed(1, 3).mapToObj(i -> threadBuilder.start(getRunnable(i + ":"))).toList(); // Notice that the thread name suffix caries on from the previous count.
        threads.forEach(x -> uncheck(() -> x.join()));

        sleep();
        System.out.printf("%nExample 5: Using an ExecutorService with virtual threads%n%n");
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Execute multiple tasks
            for (int i = 0; i < 10; i++) {
                int taskId = i; // Effectively final variable for use in the lambda
                executor.execute(() -> {
                            System.out.printf("Started  task %d in a virtual thread. %s%n", taskId, getThreadInfo());
                            sleep();
                            System.out.printf("Finished task %d in a virtual thread. %s%n", taskId, getThreadInfo());
                        }
                );
            }
        } // Automatically shuts down the executor

        sleep();
        System.out.printf("%nExample 6: Running a million tasks (not cpu bound) efficiently%n%n");
        measure(getRunnable(Executors::newVirtualThreadPerTaskExecutor, () -> doWorkWithSleep()), "Virtual  thread tasks");
        measure(getRunnable(Executors::newCachedThreadPool, () -> doWorkWithSleep()), "Platform thread tasks");

        System.out.printf("%nExample 7: Running a million task (cpu bound)%n%n");
        measure(getRunnable(Executors::newVirtualThreadPerTaskExecutor, () -> doActiveWork()), "Virtual  thread tasks");
        measure(getRunnable(Executors::newCachedThreadPool, () -> doActiveWork()), "Platform thread tasks");
    }

    private static void sleep() {
        Utils.sleep(7_000);
    }

    private static Runnable getRunnable(String message) {
        return () -> {
            var thread = Thread.currentThread();
            System.out.printf("Started  %-2s %-72s. %s%n", message, thread, getThreadInfo());
            sleep();
            System.out.printf("Finished %-2s %-72s. %s%n", message, thread, getThreadInfo());
        };
    }

    private static Runnable getRunnable(Supplier<ExecutorService> executorSupplier, Runnable work) {
        return () -> {
            try (var executor = executorSupplier.get()) {
                IntStream.range(0, THREAD_COUNT).forEach(i -> executor.execute(work));
            }
        };
    }

    /**
     * Simulates some work with Thread.sleep()
     */
    private static void doWorkWithSleep() {
        Utils.sleep(10);
    }

    /**
     * Simulates some work by a while loop with no sleep
     */
    private static void doActiveWork() {
        sleepNano(100_000);
    }
}

