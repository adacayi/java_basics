package com.sanver.basics.reactive;

import reactor.core.publisher.Mono;

import static com.sanver.basics.utils.Utils.sleep;

public class MonoFromSupplierSample {
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
        mono.subscribe(x -> System.out.printf("Result %d received. Thread id: %d%n", x, Thread.currentThread().getId())); // Note that this blocks the current thread and the supplier above runs at this point.
        System.out.println("Main thread finished. Thread id: " + Thread.currentThread().getId());
    }
}
