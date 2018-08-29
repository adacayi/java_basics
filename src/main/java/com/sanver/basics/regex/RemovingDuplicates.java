package com.sanver.basics.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemovingDuplicates {
    public static void main(String... args) {
        String message = "Goodbye bye Bye in iN inthe";
        String regex = "(\\b\\w+)(\\W+\\1\\b)+";// \b is used to mean beginning or end of a word.
        // \1 is used to show the first captured group
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        System.out.println(message);
        while (matcher.find()) {
            System.out.println(matcher.group());
            message = message.replaceAll(matcher.group(), matcher.group(1));
            System.out.println(message);
        }
    }
}
