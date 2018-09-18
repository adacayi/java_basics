package com.sanver.basics.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AggressiveMatching {

    public static void main(String... args) {
        String message = "abdullah.sanver.sancode.co.uk~~~";
        String patternString = "[\\w.]+\\.\\w{2,6}"; // . inside bracket means . literally.
        // But outside [] it means any character.
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(message);
        System.out.println(message);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
        System.out.printf("Is it a complete match: %s\n", matcher.matches() ? "Yes" : "No");
        System.out.printf("Is it a complete match: %s\n", message.matches(patternString) ? "Yes" : "No");
    }
}
