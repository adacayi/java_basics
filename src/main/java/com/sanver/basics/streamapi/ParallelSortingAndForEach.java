package com.sanver.basics.streamapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.function.Consumer;
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
        Consumer<Integer> printWithThreadInfo = x -> {
            System.out.printf("Printing %d. %s%n", x, getThreadInfo());
            sleep(1000);
        };

        print("Parallel stream sorting and then using forEach");
        Stream.of(3, 2, 1, 5, 6).parallel().sorted(threadPrintingComparator).forEach(printWithThreadInfo); // Note that for small lists, parallelStream().sorted() often uses only one thread because the overhead of parallel processing would outweigh its benefits for small datasets.
        // Note that after sorted, the stream is still a parallel stream.
        print("Parallel stream sorting and then unordered");
        System.out.println(Stream.of(3, 2, 1, 5, 6).parallel().sorted().unordered().toList());
        print("Parallel stream findFirst");
        System.out.printf("%d", Stream.of(3, 2, 1, 5, 6).parallel().sorted().findFirst().orElse(0));
        print("Parallel stream sorted then unordered findFirst");
        System.out.printf("%d", Stream.of(3, 2, 1, 5, 6).parallel().sorted().unordered().findFirst().orElse(0));
        print("Parallel stream sorted findAny");
        System.out.printf("%d", Stream.of(3, 2, 1, 5, 6).parallel().sorted().findAny().orElse(0));
        print("With forEachOrdered");
        Stream.of(3, 2, 1, 5, 6).parallel().sorted(threadPrintingComparator).forEachOrdered(printWithThreadInfo);
        print("With sequential stream and forEach");
        Stream.of(3, 2, 1, 5, 6).parallel().sorted(threadPrintingComparator).sequential().forEach(printWithThreadInfo);
        print("Parallel stream sorting a large list");
        var largeList = new ArrayList<>(IntStream.range(0, 10_000).parallel().boxed().toList());
        Collections.shuffle(largeList);
        System.out.println("First item = " + largeList.parallelStream().sorted(threadPrintingComparator).findFirst().orElse(-1)); // This stream might be sorted with multiple threads because of its size.
    }

    private static void print(String message) {
        sleep(3_000);
        System.out.printf("%n%n%s%n%n",message);
        sleep(3_000);
    }
}
