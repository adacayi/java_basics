package com.sanver.basics.reactive;

import reactor.core.publisher.Mono;

import static com.sanver.basics.utils.Utils.sleep;

public class BlockSample {
    public static void main(String[] args) {
        System.out.println("Main thread started. Thread id: " + Thread.currentThread().getId());
        var mono = Mono.fromSupplier(() -> {
                    System.out.println("Current thread: " + Thread.currentThread().getId());
                    sleep(5000);
                    return 10;
                }
        );
        System.out.println("Trying to get the result");
        sleep(6000);
        var result = mono.block(); // Note that this blocks the current thread and the supplier above starts running at this point.
        System.out.printf("Main thread finished. Result: %d Thread id: %d%n", result, Thread.currentThread().getId());
    }
}
