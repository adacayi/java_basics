package com.sanver.basics.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcherSample {
    private static final String MESSAGE = "This is my small example string which I'm going to use for pattern matching.";

    public static void main(String[] args) {
        System.out.println(MESSAGE);
        Pattern pattern = Pattern.compile("\\w+");
        // in case you would like to ignore case sensitivity,
        // you could use this statement:
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(MESSAGE);
        // check all occurrences
        while (matcher.find()) {
            System.out.printf("Start index: %2d End Index: %2d matcher.group(): %-15s Substring: %-15s%n", matcher.start(), matcher.end(), matcher.group(), MESSAGE.substring(matcher.start(), matcher.end()));
        }
        // now create a new pattern and matcher to replace whitespace with tabs
        Pattern space = Pattern.compile("\\s+");
        Matcher matcher2 = space.matcher(MESSAGE);
        System.out.println("\n");
        System.out.println(MESSAGE);
        System.out.println(matcher2.replaceAll("_")); // Replaces every subsequence of the input sequence that matches the pattern with the given replacement string. The input sequence is not changed. A new one is returned. This first resets the matcher.
        System.out.println(matcher2.replaceAll(":")); // This is to show that matcher2.replaceAll does not change the input in the matcher.
        System.out.println(MESSAGE.replaceAll("\\s+", "_")); // Same result can be achieved with String.replaceAll as well.
    }
}
