package com.sanver.basics.streamapi;

import java.util.Arrays;
import java.util.Random;

public class RandomStream {
    public static void main(String[] args) {
        var random = new Random();
        var randomNumberStream = random.ints(10, 1, 11); // Generates an int stream of size 10, with random integers between 1 and 10
        System.out.println(Arrays.toString(randomNumberStream.toArray()));
    }
}
