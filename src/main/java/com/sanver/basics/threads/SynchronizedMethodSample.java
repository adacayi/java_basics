package com.sanver.basics.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * There are different usages of the synchronized keyword in Java at different levels:
 * <ul>
 *   <li><strong>Instance methods:</strong> Synchronize the entire method to ensure only one thread can execute it at a time for a given object.</li>
 *   <li><strong>Static methods:</strong> Synchronize the entire method at the class level, allowing only one thread to execute it across all instances.
 *   static synchronized methods are synchronized on the Class object associated with the class.
 *   Since only one Class object exists per JVM per class, only one thread can execute inside a static synchronized method per class.</li>
 *   <li><strong>Code blocks:</strong> Synchronize specific portions of code, offering more granular control over thread access.</li>
 * </ul>
 *
 * <p>When using a synchronized block, Java internally employs a monitor,
 * also known as a <strong>monitor lock</strong> or <strong>intrinsic lock</strong>, to provide synchronization.
 * These monitors are associated with objects, ensuring that all synchronized blocks
 * of the same object can only have one thread executing them at a time.
 *
 * <h2>Reentrancy</h2>
 *
 * <p>The lock behind synchronized methods and blocks is reentrant. This means that
 * the current thread can acquire the same synchronized lock multiple times while already holding it.</p>
 *
 * <p>For example:</p>
 *
 * <pre>
 * {@code
 * Object lock = new Object();
 * synchronized (lock) {
 *     System.out.println("First time acquiring it");
 *
 *     synchronized (lock) {
 *         System.out.println("Entering again");
 *
 *         synchronized (lock) {
 *             System.out.println("And again");
 *         }
 *     }
 * }
 * }
 * </pre>
 *
 * <p>As shown in the code above, while inside a synchronized block, the same monitor lock
 * can be repeatedly acquired by the same thread without causing a deadlock.</p>
 *
 * <a href = "https://www.baeldung.com/java-synchronized">Source</a>
 */
public class SynchronizedMethodSample {
    public static void main(String[] args) {
        var p1 = new SynchronizedMethodSample();
        var p2 = new SynchronizedMethodSample();

        try (var executor = Executors.newFixedThreadPool(3)) {
            CompletableFuture.runAsync(p1::first, executor);
            sleep(3000);
            CompletableFuture.runAsync(p1::second, executor); // The thread calling p.first() owns the lock on the p1 instance, thus the thread calling p1.second() will be blocked until that lock is released.
            sleep(100);
            CompletableFuture.runAsync(p2::third, executor); // There is no lock on the p2 instance so this is executed without blocking.
            sleep(100);
            System.out.println("Notice that the third thread in the thread pool is assigned to execute the third task, since the second thread is currently running to execute the second method, but is blocked.");
        }

        System.out.printf("%nFirst, second and third tasks are finished.%n%n");

        try (var executor = Executors.newFixedThreadPool(2)) {
            CompletableFuture.runAsync(() -> p1.fourth(), executor); // static synchronized methods are synchronized on the Class object associated with the class.
            // Since only one Class object exists per JVM per class, only one thread can execute inside a static synchronized method per class,
            // irrespective of the number of instances it has.
            sleep(100);
            CompletableFuture.runAsync(() -> p2.fifth(), executor); // The thread calling p1.fourth() owns the lock on the SynchronizedMethodSample.class, thus the thread calling p2.fifth() will be blocked until that lock is released.
        }
    }

    static synchronized void fourth() { // static synchronized methods are synchronized on the Class object associated with the class.
        // Since only one Class object exists per JVM per class, only one thread can execute inside a static synchronized method per class,
        // irrespective of the number of instances it has.
        task("fourth");
    }

    static synchronized void fifth() {
        task("fifth");
    }

    private static void task(String name) {
        System.out.printf("%-6s task  started. %s%n", name, getThreadInfo());
        sleep(7_000);
        System.out.printf("%-6s task finished. %s%n", name, getThreadInfo());
    }

    synchronized void first() { // Instance methods are synchronized over the instance of the class owning the method, which means only one thread per instance of the class can execute this method.The synchronization happens on the intrinsic lock (this) of the current instance.
        task("first");
    }

    synchronized void second() { // The synchronization happens on the intrinsic lock (this) of the current instance.
        task("second");
    }

    synchronized void third() { // The synchronization happens on the intrinsic lock (this) of the current instance.
        task("third");
    }
}
