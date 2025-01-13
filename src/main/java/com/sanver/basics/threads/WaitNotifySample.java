package com.sanver.basics.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static com.sanver.basics.utils.Utils.sleep;

public class WaitNotifySample {
    private static final Object lock = new Object();
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        Runnable read = getRead(list);

        Runnable write = getWrite(list);

        try (var executor = Executors.newFixedThreadPool(2)) {
            executor.execute(read);
            executor.execute(write);
        }
    }

    private static Runnable getRead(List<Integer> list) {
        return () -> {
            for (int i = 0; i < 10; i++) {
                synchronized (lock) {
                    while (list.isEmpty()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    System.out.println(list.removeFirst() + " removed");
                    lock.notifyAll();
                    sleep(1000); // The other thread does not regain the lock until this lock is released by the current thread.
                }
            }
        };
    }

    private static Runnable getWrite(List<Integer> list) {
        return () -> {
            for (int i = 1; i < 11; i++) {
                synchronized (lock) {
                    while (!list.isEmpty()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    list.add(i);
                    System.out.println(i + " added");
                    lock.notifyAll(); // The other thread does not regain the lock until this lock is released by the current thread.
                    sleep(1000);
                }
            }
        };
    }
}
