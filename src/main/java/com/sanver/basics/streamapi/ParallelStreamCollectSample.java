package com.sanver.basics.streamapi;

import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

public class ParallelStreamCollectSample {
    public static void main(String[] args) {
        IntConsumer peek = i -> {
            sleep(1000);
            System.out.printf("%-2d Thread id: %-2d%n", i, Thread.currentThread().getId());
        };
        var list = IntStream.range(0, 10).boxed().parallel().peek(peek::accept).collect(Collectors.toList()); // Collect is collecting elements in the original order, but does not cause the stream to be processed in sequential order, like the forEachOrdered does.
        list.add(10);
        System.out.println(list);

        var list2 = IntStream.range(0, 10).boxed().parallel().peek(peek::accept).toList();
        System.out.println(list2);
    }
}
