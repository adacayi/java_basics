package com.sanver.basics.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemovingDuplicates {
    public static void main(String... args) {
        //This application removes any duplicate words case insensitively with the accompanying spaces.
        String message = "Goodbye bye byeBye in iN in the end Bye byE to you, bYe good bye";
        String regex = "(\\b\\w+\\b)(.+?)((\\b\\1\\b\\s?)+)";// \b is used to mean beginning or end of a word.
        // \1 is used to show the first captured group
        // \\s* is used to capture the spaces after the duplicate words, so they can be removed as well.
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        boolean duplicateExists;
        System.out.printf("Message %8s: %s%n", "",message);

        do {
            matcher = pattern.matcher(message);
            System.out.println("\n");
            duplicateExists = matcher.find();
            if (duplicateExists) {
                System.out.println("Repetition group: " + matcher.group());
                System.out.println("Repeating word  : " + matcher.group(1));
                System.out.println("Repetition      : " + matcher.group(3));
                message = message.replace(matcher.group(), matcher.group(1) + matcher.group(2));
                System.out.printf("After removal   : %s%n", message);
            }
        } while (duplicateExists);
    }
}
