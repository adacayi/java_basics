package com.sanver.basics.threads;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerSample {

    public static void main(String[] args) {
        AtomicInteger value = new AtomicInteger(0);
        Runnable increment = () -> {
            for (int i = 0; i < 10000; i++, value.incrementAndGet())
                ;
        };
        Thread first = new Thread(increment);
        Thread second = new Thread(increment);
        first.start();
        second.start();
        try {
            first.join();
            second.join();
            System.out.println(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
