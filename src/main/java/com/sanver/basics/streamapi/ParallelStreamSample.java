package com.sanver.basics.streamapi;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamSample {

    public static void main(String[] args) {
        int length = 10;
        List<Integer> idList = IntStream.range(0, length).boxed().collect(Collectors.toList());
        List<Integer> newList = new ArrayList<>();
        idList = idList.parallelStream().collect(Collectors.toList());// collect method preserves order
        System.out.println(idList);
        idList = idList.parallelStream().peek(x -> System.out.print(x + " ")).collect(Collectors.toList());
        // Collect method preserves order but peek shows that elements are accesses in parallel
        System.out.println();
        System.out.println(idList);
        idList.parallelStream().forEach(x -> newList.add(x));
        System.out.println(newList);
    }
}
