package com.sanver.basics.strings;

import java.util.Arrays;

public class SplitSample {
    public static void main(String... args) {
        String value = "Selamunaleykum. This will be_separated, won't it?";
        String[] split = value.split("[^A-z'?,.]");// This regex means excluding characters from A to z
        // (The characters in ASCII table between A and z which includes underscore.) and '?,.
        Arrays.asList(split).forEach(System.out::println);
        //Same cannot be achieved by StringTokenizer, since it does not take Regex.
    }
}
