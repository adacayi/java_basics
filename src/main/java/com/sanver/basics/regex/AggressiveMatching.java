package com.sanver.basics.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AggressiveMatching {

    public static void main(String... args) {
        String message = "abdullah.sanver.sancode.co.uk~~~";
        Pattern pattern = Pattern.compile("[\\w.]+\\.\\w{2,6}");
        Matcher matcher = pattern.matcher(message);
        System.out.println(message);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
        System.out.printf("Is it a complete match: %s\n", matcher.matches() ? "Yes" : "No");
    }
}
