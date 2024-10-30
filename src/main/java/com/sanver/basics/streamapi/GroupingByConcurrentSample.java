package com.sanver.basics.streamapi;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.Utils.sleep;

public class GroupingByConcurrentSample {

    public static void main(String[] args) {
        List<Integer> list = IntStream.range(1, 10).boxed().toList();

        ConcurrentMap<String, List<Integer>> result = list.parallelStream().collect(Collectors.groupingByConcurrent(group())); // If we used Collectors.groupingBy instead of Collectors.groupingByConcurrent, collecting will still be done in parallel but the result will be ordered and will return a Map instead of a ConcurrentMap.
        print(result);

        Map<String, List<Integer>> result2 = list.parallelStream().collect(Collectors.groupingBy(group())); // Grouping will be done in parallel, but the final map values will be ordered
        print(result2);

        ConcurrentMap<String, List<Integer>> result3 = list.stream().collect(Collectors.groupingByConcurrent(group())); // Grouping will be done sequentially, since the stream is sequential
        print(result3);
    }

    private static Function<Integer, String> group() {
        return i -> {
            System.out.printf("Started  Grouping %2d. Thread id: %2d%n", i, threadId());
            sleep(5000);
            System.out.printf("Finished Grouping %2d. Thread id: %2d%n", i, threadId());
            return i % 2 == 0 ? "Even" : "Odd";
        };
    }

    private static long threadId() {
        return Thread.currentThread().getId();
    }

    private static void print(Map<String, List<Integer>> map) {
        System.out.println();
        map.forEach((k, v) ->
                System.out.printf("%-4s: %s%n", k, v.stream().map(x -> String.format("%2d", x)).collect(Collectors.joining(", "))));
        System.out.println();
    }
}
