package com.sanver.basics.reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class ConvertFluxToMonoSample {
    public static void main(String[] args) {
        var flux = Flux.just(1, 2, 3, 4);
        Mono<List<Integer>> mono = flux.collectList(); // This converts a Flux<Integer> to Mono<List<Integer>>
        mono.subscribe(System.out::println);
    }
}
