package com.sanver.basics.reactive;

import org.slf4j.MDC;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

public class MonoSample {
    public static void main(String[] args) {
        var result = new ArrayList<Integer>();
        MDC.put("userName", "asanver"); // This is also to show that when we subscribe to mono, the same thread is used, hence the same MDC context is used for logs.
        System.out.println("Main thread. Thread Id: " + Thread.currentThread().getId());
        Mono<Integer> mono = Mono.just(4);
        mono.log().subscribe(x -> {
            result.add(x);
            System.out.printf("%d added to the list. Thread id: %d%n", x, Thread.currentThread().getId());
        }); // Note, logging wouldn't log anything if we didn't subscribe.
        System.out.println(result);
    }
}
