package com.sanver.basics.streamapi;

import java.util.stream.IntStream;

public class CloseSample {
    public static void main(String[] args) {
        var stream = IntStream.range(0, 5).map(x -> {
            System.out.printf("Mapping %d to %d", x, 2 * x);
            return 2 * x;
        });

        System.out.println("Notice that the stream is not executed");
        stream.close(); // Closes this stream, causing all close handlers for this stream pipeline to be called. Notice that map is not executed.
//        stream.forEach(System.out::println); // This will throw java.lang.IllegalStateException: stream has already been operated upon or closed
    }
}
