package com.sanver.basics.reactive;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.lang.invoke.MethodHandles;

import static com.sanver.basics.utils.Utils.sleep;

public class HotStreamSample {
    public static void main(String[] args) {
        var logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
        MDC.put("userName", "asanver");
        System.out.println("Main thread. Thread Id: " + Thread.currentThread().getId());
        ConnectableFlux<Object> flux = Flux.create(fluxSink -> {
            while (true) {
                fluxSink.next(System.currentTimeMillis());
                System.out.println("Published thread id: " + Thread.currentThread().getId());
                sleep(3000);
            }
        }).publish();
        flux.subscribe(x -> System.out.printf("Result: %d, subscriber thread id: %d%n", x ,Thread.currentThread().getId()));
        flux.subscribe(x -> logger.info("Result: {}, subscriber thread id {} ", x, Thread.currentThread().getId()));
        flux.connect(); // When we subscribe, ConnectableFlux doesn’t automatically start emitting, so we can subscribe multiple times and then call connect to start the ConnectableFlux to emit.
    }
}
