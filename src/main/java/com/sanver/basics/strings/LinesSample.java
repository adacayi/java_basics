package com.sanver.basics.strings;

import java.util.stream.Stream;

public class LinesSample {
    public static void main(String[] args) {
        int[] count = {0};
        Stream<String> lines = """
                First line
                Second line
                Third line
                """.lines();

        lines.forEach(line -> System.out.printf("%d- %s%n", ++count[0], line));
    }
}
