package com.sanver.basics.regex;

public class ReplaceFirstSample {
    public static void main(String... args) {
        String message = "aaacccc";
        System.out.println(message.replaceFirst("a*", "b"));
        System.out.println(message.replaceFirst("a*?", "b"));//? after a quantifier makes it a
        // reluctant quantifier. It tries to find the smallest match.
        // This makes the regular expression stop at the first match.
    }
}
