package com.sanver.basics.regex;

public class MatchesSample {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public static void main(String... args) {
        String[] emailCandidates = {"sdfa@asd.co", "@a.com", "sd#a@asd.co", "asdf@a~a.com", "abdullah.sanver@sancode.co.uk"};
        for (String candidate : emailCandidates)
            System.out.printf("%-40s %s an e-mail address\n", candidate,
                    candidate.matches(EMAIL_REGEX) ? "may be" : "is not");
    }
}
