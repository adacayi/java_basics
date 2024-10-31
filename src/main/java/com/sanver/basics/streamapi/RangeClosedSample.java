package com.sanver.basics.streamapi;

import java.util.stream.IntStream;

public class RangeClosedSample {
    public static void main(String[] args) {
        IntStream.rangeClosed(1, 10).forEach(System.out::println);
    }
}
