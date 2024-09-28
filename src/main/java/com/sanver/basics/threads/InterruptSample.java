package com.sanver.basics.threads;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

public class InterruptSample {

    public static void main(String[] args) {
        Thread waitingThread = new Thread(() -> {
            try {
                System.out.println("Thread started. " + getThreadInfo());
                Thread.sleep(10_000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted. " + getThreadInfo());

                // If thread is interrupted, sleep will throw an InterruptedException and clear the interrupted flag.
                // Hence, the value is false.
                System.out.printf("isInterruptedValue = %s. %s%n", Thread.currentThread().isInterrupted(), getThreadInfo());

                Thread.currentThread().interrupt();
                System.out.printf("isInterruptedValue = %s.  %s%n ", Thread.currentThread().isInterrupted(), getThreadInfo());
            }
        });

        waitingThread.start();

        sleep(2000);
        waitingThread.interrupt();
    }
}
