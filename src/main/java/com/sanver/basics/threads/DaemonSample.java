package com.sanver.basics.threads;

public class DaemonSample {
    public static void main(String... args) {
        Thread counter = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.printf("%d ", i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        counter.setDaemon(true); // Marks this thread as either a daemon thread or a user thread.
        // The Java Virtual Machine exits when the only threads running are all daemon threads.
        //This method must be invoked before the thread is started.
        counter.start();//The counter will not print out numbers since the thread is set to daemon.
        // As default threads are user threads, hence JVM won't quit until they are finished.
    }
}
