package com.sanver.basics.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AggressiveMatching {

    public static void main(String... args) {
        String message = "abdullah.sanver.sancode.co.uk~~~";
        String patternString = "([\\w.]+?)\\.(\\w{2,})";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            int groupCount = matcher.groupCount();
            for (int i = 1; i <= groupCount; i++)
                System.out.printf("%d group: %s\n", i, matcher.group(i));
        }
        System.out.printf("Is it a complete match: %s\n", matcher.matches() ? "Yes" : "No");
        System.out.printf("Is it a complete match: %s\n", message.matches(patternString) ? "Yes" : "No");
    }
}
