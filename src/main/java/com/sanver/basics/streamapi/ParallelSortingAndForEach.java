package com.sanver.basics.streamapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

public class ParallelSortingAndForEach {

    private static final Comparator<Integer> threadPrintingComparator = (x, y) -> {
        System.out.printf("Started  comparing %d and %d. %s%n", x, y, getThreadInfo());
        sleep(1000);
        System.out.printf("Finished comparing %d and %d. %s%n", x, y, getThreadInfo());

        if (x == null) {
            return y == null ? 0 : -1;
        }

        return x.compareTo(y);
    };

    public static void main(String[] args) {
        System.out.println("Parallel stream sorting and then using forEach: ");
        Stream.of(3, 2, 1, 5, 6).parallel().sorted(threadPrintingComparator).forEach(x -> System.out.print(x + " ")); // Note that for small lists, parallelStream().sorted() often uses only one thread because the overhead of parallel processing would outweigh its benefits for small datasets.
        System.out.println("\n\nParallel stream findFirst: ");
        System.out.printf("%d", Stream.of(3, 2, 1, 5, 6).parallel().sorted().findFirst().orElse(0));
        System.out.println("\n\nWith forEachOrdered: ");
        Stream.of(3, 2, 1, 5, 6).parallel().sorted(threadPrintingComparator).forEachOrdered(x -> System.out.print(x + " "));
        System.out.println("\n\nWith sequential stream and forEach: ");
        Stream.of(3, 2, 1, 5, 6).parallel().sorted(threadPrintingComparator).sequential().forEach(x -> System.out.print(x + " "));
        System.out.println("\n\n");
        var largeList = new ArrayList<>(IntStream.range(0, 10_000).parallel().boxed().toList());
        Collections.shuffle(largeList);
        largeList.parallelStream().sorted(threadPrintingComparator).findFirst(); // This stream might be sorted with multiple threads because of its size.
    }
}
