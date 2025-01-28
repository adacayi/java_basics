package com.sanver.basics.streamapi;

import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static com.sanver.basics.utils.PerformanceComparer.measure;
import static com.sanver.basics.utils.Utils.sleep;

public class ForEachOrderedSample {
    public static void main(String[] args) {
        IntUnaryOperator triple = i -> {
            System.out.printf("%d: Tripling %d Thread id: %d%n", i, i, Thread.currentThread().threadId());
            sleep(3000);
            return 3 * i;
        };
        IntConsumer print = i -> System.out.printf("%d: Thread id: %d%n", i, Thread.currentThread().threadId());
        IntConsumer printWithDelay = i -> {
            System.out.printf("%d: Thread id: %d%n", i, Thread.currentThread().threadId());
            sleep(3000);
        };
        System.out.printf("forEach%n%n");
        measure(() -> IntStream.range(0, 10).map(triple).parallel().forEach(print), "forEach");
        print("forEachOrdered");
        measure(() -> IntStream.range(0, 10).map(triple).parallel().forEachOrdered(print), "forEachOrdered");
        print("forEach with delay in forEach");
        measure(() -> IntStream.range(0, 10).map(triple).parallel().forEach(printWithDelay), "forEach");
        print("forEachOrdered with delay in forEachOrdered");
        measure(() -> IntStream.range(0, 10).map(triple).parallel().forEachOrdered(printWithDelay), "forEachOrdered");
    }

    private static void print(String title) {
        sleep(7000);
        System.out.printf("%n%s%n%n",title);
    }
}
