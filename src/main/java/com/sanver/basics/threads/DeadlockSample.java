package com.sanver.basics.threads;

import java.util.concurrent.CompletableFuture;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

public class DeadlockSample {
    private final String instanceName;

    public DeadlockSample(final String instanceName) {
        this.instanceName = instanceName;
    }
    synchronized void first(DeadlockSample other) {
        System.out.printf("First  operation started  for instance %s. %s%n", instanceName, getThreadInfo());
        sleep(1000);
        other.second(); // Since locks are on the instances of a and b, if we just called second() here, there won't be any deadlock between the two threads executing a.first and b.first.
        System.out.printf("First  operation finished for instance %s. %s%n", instanceName, getThreadInfo());
    }

    synchronized void second() { // The synchronization happens on the intrinsic lock (this) of the current instance. Thus, when the thread executing a.first() calls b.second(), b.first() is already called by the other thread and not finished so that other thread owns the lock on b instance, making the thread calling b.second() wait.
        // Similarly, when the other thread calls a.second(), since the first thread is waiting for the other thread to release the lock on the b object, it still owns the lock on the lock on the "a" object, so the second thread will wait for the first thread to finish as well, causing the deadlock.
        System.out.printf("Second operation started  for instance %s. %s%n", instanceName, getThreadInfo());
        sleep(1000);
        second();
        System.out.printf("Second operation finished for instance %s. %s%n", instanceName, getThreadInfo());
    }
    public static void main(String[] args) {
        var a = new DeadlockSample("A");
        var b = new DeadlockSample("B");
        var future1 = CompletableFuture.runAsync(() -> a.first(b));
        var future2 = CompletableFuture.runAsync(() -> b.first(a));
        CompletableFuture.allOf(future1, future2).join();
    }
}
