package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.sanver.basics.utils.Utils.threadId;

public class StreamExecutionOrderSample {

    public static void main(String[] args) {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5).filter(x -> {
            System.out.println("Filter " + x);
            return x % 2 == 0;
        });
        System.out.println("Note that stream is not executed yet, since there is no terminal operation in it.");
        stream.forEach(x -> System.out.println("Foreach " + x));
        System.out.println("Now the stream is executed since there is a forEach, a terminal operation.");
        System.out.printf(
                "%nBe aware that when filtering is done for an element, it is immediately passed to forEach, not waiting to finish all filtering first.%n" +
                        "The values passed filtering will be immediately passed to other methods like map and reduce as well. %nThis behavior is different in sort, however. %n Sort first sorts all the stream and then passes it to methods like map, reduce, forEach etc.%n%n");

        var result = Stream.of(3, 2, 1, 4, 7, 8, 5, 6, 9).parallel().sorted((x, y) -> {
            System.out.printf("Comparing %d and %d. Thread id: %d%n", x, y, threadId());
            return Integer.compare(x, y);
        }).map(x -> {
            System.out.printf("Mapping %d to %d. Thread id: %d%n", x, 2 * x, threadId());
            return 2 * x;
        }).toList();

        System.out.println("Sorted list: " + result);

        System.out.printf("%nDemonstrating passing values to reduce after sort: %n");

        var sum = Stream.of(3, 2, 1, 4, 7, 8, 5, 6, 9).parallel().sorted((x, y) -> {
            System.out.printf("Comparing %2d and %2d. Thread id: %d%n", x, y, threadId());
            return Integer.compare(x, y);
        }).reduce((x, y) -> {
            System.out.printf("Reducing %3d and %2d to %2d. Thread id: %d%n", x, y, x + y, threadId());
            return x + y;
        }).orElse(0);

        System.out.println("Reduce result: " + sum);

        System.out.printf("%nNote that executing a terminal operation makes the stream inaccessible%n");
        var multiplicationStream = Stream.of(2, 4, 6);
        var multiplicationResult = multiplicationStream.reduce(1, (x, y) -> x * y);
        System.out.println("Multiplication result: " + multiplicationResult);
//        var count = multiplicationStream.count(); // This throws java.lang.IllegalStateException: stream has already been operated upon or closed

        System.out.printf("%nThis is to show the order importance in the stream method chaining%n%n");
        System.out.printf("Skip placed after call to map. Note that in this case there are 5 calls to map, although we have skip(2).%n%n");
        var array1 = IntStream.range(0, 5).map(x -> {
            System.out.printf("Mapping %d to %d%n", x, 2 * x);
            return 2 * x;
        }).skip(2).toArray();

        System.out.printf("%nArray1: %s%n", Arrays.toString(array1));
        System.out.printf("%nWhen skip is placed first. Note that in this case there are 3 calls to map instead of 5.%n%n");
        var array2 = IntStream.range(0, 5).skip(2).map(x -> { // When we prioritize skip over map, we avoid unnecessary mapping for the skipped items. Intermediate operations which reduce the size of the stream should be placed before operations which are applying to each element.
            System.out.printf("Mapping %d to %d%n", x, 2 * x);
            return 2 * x;
        }).toArray();

        System.out.printf("%nArray2: %s%n", Arrays.toString(array2));

        System.out.println("In general, intermediate operations which reduce the size of the stream should be placed before operations which are applying to each element to avoid redundant operations.");
    }
}
