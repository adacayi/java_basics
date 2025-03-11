package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class RandomStream {
    public static void main(String[] args) {
        var random = new Random();
        IntStream randomNumberStream = random.ints(10, 1, 11); // Generates an int stream of size 10, with random integers between 1 and 10
        System.out.println(Arrays.toString(randomNumberStream.toArray()));

        DoubleStream doubleStream = random.doubles(10);
        System.out.println(doubleStream.boxed().toList());

        doubleStream = random.doubles().limit(10);
        System.out.println(doubleStream.boxed().toList());


        doubleStream = DoubleStream.generate(random::nextDouble).limit(10);
        System.out.println(doubleStream.boxed().toList());
    }
}
