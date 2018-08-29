package com.sanver.basics.regex;

import java.util.Scanner;

public class IPMatching {
    public static void main(String... args) {
        String zeroTo255 = "(\\d{1,2}|[01]\\d{2}|2[0-4]\\d|25[0-5])";
        //Paranthesis are required in the beginning and at the end because we will join this 4 times with dots.
        String pattern = zeroTo255 + "." + zeroTo255 + "." + zeroTo255 + "." + zeroTo255;
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {

                System.out.print("Ip: ");
                String text = scanner.nextLine();
                if (text.trim().isEmpty())
                    return;
                System.out.println(text.matches(pattern) ? "Valid IP" : "Not valid IP");
            }
        }
    }
}
