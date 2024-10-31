package com.sanver.basics.streamapi;

import java.util.stream.IntStream;

/**
 * Sonar issue for peek:
 * <p>
 * According to its JavaDocs, the intermediate Stream operation java.util.Stream.peek() “exists mainly to support debugging” purposes.
 * A key difference with other intermediate Stream operations is that the Stream implementation is free to skip calls to peek() for optimization purpose. This can lead to peek() being unexpectedly called only for some or none of the elements in the Stream.
 * As a consequence, relying on peek() without careful consideration can lead to error-prone code.
 */
public class PeekSample {

    public static void main(String[] args) {
        int personCount = 10;
        IntStream.range(0, personCount).parallel().peek(x -> System.out.printf("%d ", x)).findFirst();
        // findFirst is called because streams have lazy execution.
    }
}
