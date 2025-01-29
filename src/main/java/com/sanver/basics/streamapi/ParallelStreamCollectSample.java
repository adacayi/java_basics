package com.sanver.basics.streamapi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

public class ParallelStreamCollectSample {
    public static void main(String[] args) {
        IntFunction<Integer> peek = i -> {
            System.out.printf("%-2d %s%n", i, threadId());
            sleep(2_000);
            return i;
        };
        var collector = Collector.<Integer, List<Integer>>of(
                ArrayList::new, (list, item) -> {
                    System.out.printf("Collecting %d. Existing list: %s. %s%n", item, list, threadId());
                    sleep(1000);
                    list.add(item);
                }, (list1, list2) -> {
                    System.out.printf("Merging %s and %s. %s%n", list1, list2, threadId());
                    sleep(1000);
                    list1.addAll(list2);
                    return list1;
                });
        var list = IntStream.range(0, 10).boxed().parallel().map(peek::apply).collect(collector); // Collect is collecting elements in parallel but returns the result in original order.
        list.add(10);
        System.out.println(list);

        sleep(7_000);
        var list2 = IntStream.range(0, 10).boxed().parallel().map(peek::apply).toList();
        System.out.printf("%n%s%n", list2);
    }

    private static String threadId() {
        return "Thread id: %-2d".formatted(Thread.currentThread().threadId());
    }
}
