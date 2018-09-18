package com.sanver.basics.regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTester {
    public static void main(String... args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter text for source for regex");
            String text = scanner.nextLine();
            String patternString;
            Pattern pattern;
            Matcher matcher;
            int groupCount, start, end, index;

            while (true) {
                System.out.print("Enter regex pattern: ");
                patternString = scanner.nextLine();

                if (patternString.trim().isEmpty())
                    return;

                pattern = Pattern.compile(patternString);
                matcher = pattern.matcher(text);
                groupCount = matcher.groupCount();
                index = 0;
                while (matcher.find()) {
                    if (index == 0) {
                        System.out.println(text);
                    }

                    if (matcher.group().isEmpty()) // Sometimes the group comes empty. Don't know the reason. To avoid mistakes we skip those groups.
                        // i.e. give the regex pattern as \w* to see empty groups.
                        continue;

                    start = matcher.start();
                    end = matcher.end() - 1;
                    if (start == end) {
                        System.out.printf("%" + (start + 1 - index) + "s", "|");
                    } else {
                        System.out.printf("%" + (start + 1 - index) + "s", "^");
                        index = start + 1;
                        System.out.printf("%" + (end + 1 - index) + "s", "^");
                    }
                    index = end + 1;
                    if (groupCount >= 1) {
                        System.out.printf("\nGroup%s: ", groupCount > 1 ? "s" : "");
                        for (int i = 1; i <= groupCount; i++)
                            System.out.printf("{%s} ", matcher.group(i));
                        System.out.println();
                        index = 0;
                    }
                }
                System.out.println();
            }
        }
    }
}
