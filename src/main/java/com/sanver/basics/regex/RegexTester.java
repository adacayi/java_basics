package com.sanver.basics.regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Look into {@link com.sanver.basics.regex.RegexTesterTest} for tests.
 */
public class RegexTester {
    public static void main(String... args) {
        // Sample source: Selamunaleykum. This is a pattern which is used to test, test for ^ regex ^ patterns.
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter text for source for regex");
            String text = scanner.nextLine();
            String patternString;
            String firstFormat;
            String secondFormat;
            Pattern pattern;
            Matcher matcher;
            int groupCount;
            int start;
            int end;
            int cursor;

            while (true) {
                System.out.println("Enter regex pattern");
                patternString = scanner.nextLine();

                if (patternString.trim().isEmpty()) {
                    return;
                }

                try {
                    pattern = Pattern.compile(patternString);
                    matcher = pattern.matcher(text);
                    groupCount = matcher.groupCount();
                } catch (Exception e) {
                    System.out.println("Pattern is erroneous.");
                    continue;
                }

                cursor = 0;

                if (!matcher.find()) {
                    System.out.println("No match found");
                    continue;
                }

                matcher.reset(); // This is just to show the functionality of reset. We could use a do while instead below to avoid reset.

                while (matcher.find()) {
                    if (cursor == 0) {
                        System.out.println(text);
                    }

                    if (matcher.group().isEmpty()) { // Sometimes the group comes empty. e.g. if we use \w* it means 0 or more alphanumeric characters, in which case there can be zero. We need to skip these.
                        // Give the regex pattern as \w* to see empty groups.
                        continue;
                    }

                    start = matcher.start();
                    end = matcher.end();
                    firstFormat = "%" + (start - cursor + 1) + "s";
                    secondFormat = "%" + (end - start - 1) + "s";
                    if (start + 1 == end) {
                        System.out.printf(firstFormat, "|");
                    } else {
                        System.out.printf(firstFormat, "^");
                        System.out.printf(secondFormat, "^");
                    }

                    cursor = end;

                    if (groupCount > 0) {
                        printGroups(groupCount, matcher);
                        cursor = 0;
                    }
                }

                if(groupCount == 0) {
                    System.out.println();
                }
            }
        }
    }

    private static void printGroups(int groupCount, Matcher matcher) {
        System.out.printf("%nGroup%s: ", groupCount > 1 ? "s" : "");

        for (int i = 1; i < groupCount; i++) {
            System.out.printf("{%s} ", matcher.group(i));
        }

        System.out.printf("{%s}%n%n", matcher.group(groupCount));
    }
}
