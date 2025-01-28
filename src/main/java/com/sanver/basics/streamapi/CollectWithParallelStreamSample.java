package com.sanver.basics.streamapi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;
import static com.sanver.basics.utils.Utils.sleep;

/**
 * This class demonstrates how collect works in parallel in parallel streams but retains stream order
 */
public class CollectWithParallelStreamSample {
    public static void main(String[] args) {
        IntFunction<Integer> triple = i -> {
            System.out.printf("%d: Tripling %d Thread id: %d%n", i, i, Thread.currentThread().threadId());
            sleep(3000);
            return 3 * i;
        };
        IntConsumer print = i -> System.out.printf("%d: Thread id: %d%n", i, Thread.currentThread().threadId());
        IntConsumer printWithDelay = i -> {
            System.out.printf("%d: Thread id: %d%n", i, Thread.currentThread().threadId());
            sleep(3000);
        };
        System.out.printf("collect%n%n");
        measure(() -> System.out.println(IntStream.range(0, 10).mapToObj(triple).parallel().collect(listCollector(print))), "collect");// We used mapToObj to be able to use Collector<Integer, >, because the primitive stream IntStream does not allow Collector<Integer, >
        print("collect with delay in collect");
        measure(() -> System.out.println(IntStream.range(0, 10).mapToObj(triple).parallel().collect(listCollector(printWithDelay))), "collect");
    }

    private static void print(String title) {
        sleep(7000);
        System.out.printf("%n%s%n%n", title);
    }

    private static Collector<Integer, List<Integer>, List<Integer>> listCollector(IntConsumer consumer) {
        return Collector.of(ArrayList::new, (list, item) -> {
            consumer.accept(item);
            list.add(item);
        }, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        }, x -> x);
    }
}
