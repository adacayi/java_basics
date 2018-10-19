package com.sanver.basics.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemovingDuplicates {
    public static void main(String... args) {
        boolean isReplaced;
        //This application is to remove any duplicate words.
        String message = "Goodbye bye Bye in iN in the end bye good";
        String regex = "(\\b\\w+\\b)(.*?)(\\1\\b\\s*)+";// \b is used to mean beginning or end of a word.
        // \1 is used to show the first captured group
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        System.out.println(message);

        do {
            Matcher matcher = pattern.matcher(message);
            isReplaced = false;
            System.out.println("\n---\n");
            while (matcher.find()) {
                System.out.println(matcher.group());
                message = message.replaceAll(matcher.group(), matcher.group(1) + matcher.group(2));
                System.out.println(message);
                isReplaced = true;
            }
        } while (isReplaced);
    }
}
