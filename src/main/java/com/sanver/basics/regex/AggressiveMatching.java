package com.sanver.basics.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class demonstrates the three types of regex quantifiers in Java:
 * <ul>
 *   <li>Greedy (default): Matches as much text as possible.</li>
 *   <li>Reluctant: Matches as little text as possible, stopping at the first match.</li>
 *   <li>Possessive: Matches as much text as possible without backtracking.</li>
 * </ul>
 */
public class AggressiveMatching {

    public static void main(String... args) {
        String message = "<div><p>text<p></div>";
        String aggressive = "<.+>"; // Aggressive match +
        String aggressive2 = "<.*>"; // Aggressive match *
        String reluctant = "<.*?>"; // Reluctant match *? +? ??
        String possessive = "<.*+"; // Possessive match. Possessive quantifiers (like .*+) match as much as possible but do not backtrack. If the initial attempt does not satisfy the entire pattern, the regex engine will abandon the match entirely rather than attempt smaller matches.
        String possessive2 = "<.*+>"; // .*+ will match the entire message after <, but it would then need a > after that, so it needs to backtrack just one character, but since this is possessive, it won't backtrack, so there won't be any match.
        match(message, aggressive);
        match(message, aggressive2);
        match(message, reluctant);
        match(message, possessive);
        match(message, possessive2);
        match("12345", "[1-4]++5"); // Since [1-4]++ will match up to 4, not taking 5 and 5 will match the 5 in the pattern, so the pattern matches without backtracking, thus possessive match matches.
        match("abcd", "a([b,c]+)(.*)");
        match("abcd", "a([b,c]+?)(.*)");
        match("abcd", "a([b,c]*)(.*)");
        match("abcd", "a([b,c]*?)(.*)");
        match("abcd", "a([b,c]?)(.*)");
        match("abcd", "a([b,c]??)(.*)");
        match("abcd", "a([b,c]++)(.*)");
    }

    private static void match(String message, String patternString) {
        System.out.println("Message: " + message);
        System.out.println("Pattern: " + patternString);
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(message);

        boolean result = matcher.find();

        if (!result) {
            System.out.println("Not matched, even partially.");
        } else {
            do {
                System.out.println("Matched: " + matcher.group());
                int groupCount = matcher.groupCount();

                for (int i = 1; i <= groupCount; i++) {
                    System.out.printf("Group %d: %s%n", i, matcher.group(i));
                }
            } while (matcher.find());
        }

        System.out.printf("Is it a complete match: %s%n", matcher.matches() ? "Yes" : "No");
        System.out.printf("Is it a complete match: %s%n", message.matches(patternString) ? "Yes" : "No");
        System.out.println();
    }
}
