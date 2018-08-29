package com.sanver.basics.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMathcerSample {
    private static final String EXAMPLE_TEST = "This is my small example string which I'm going to use for pattern matching.";

    public static void main(String[] args) {
        System.out.println(EXAMPLE_TEST);
        Pattern pattern = Pattern.compile("\\w+");
        // in case you would like to ignore case sensitivity,
        // you could use this statement:
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(EXAMPLE_TEST);
        // check all occurance
        while (matcher.find()) {
            System.out.printf("Start index: %d End Index: %d %s\n", matcher.start(), matcher.end(), matcher.group());
        }
        // now create a new pattern and matcher to replace whitespace with tabs
        Pattern space = Pattern.compile("\\s+");
        Matcher matcher2 = space.matcher(EXAMPLE_TEST);
        System.out.println("\n");
        System.out.println(EXAMPLE_TEST);
        System.out.println(matcher2.replaceAll("_"));
    }
}
