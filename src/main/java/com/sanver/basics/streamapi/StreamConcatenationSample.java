package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamConcatenationSample {
    public static void main(String[] args) {
        var stream1 = IntStream.range(0, 5);
        var stream2 = IntStream.range(2, 8);
        var stream3 = IntStream.concat(stream1, stream2);
        var array = stream3.toArray();
        System.out.println(Arrays.toString(array));

        Stream<Integer> stream4 = Stream.of(1, 2, 3);
        Stream<Integer> stream5 = Stream.of(2, 8);
        Stream<Integer> stream6 = Stream.concat(stream4, stream5);
        System.out.println(stream6.toList());
    }
}
