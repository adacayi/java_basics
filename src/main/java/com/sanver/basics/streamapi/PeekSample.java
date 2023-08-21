package com.sanver.basics.streamapi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PeekSample {

    public static void main(String[] args) {
        int personCount = 10;
        List<Integer> idList = IntStream.range(0, personCount).boxed().collect(Collectors.toList());
        idList.parallelStream().peek(x->System.out.printf("%d ",x)).collect(Collectors.toList());
        // Collect is called because streams have lazy execution.
    }
}
