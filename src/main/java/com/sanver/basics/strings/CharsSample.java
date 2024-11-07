package com.sanver.basics.strings;

import java.util.stream.Collectors;

public class CharsSample {
    public static void main(String[] args) {
        var message = "This is a message to James";
        System.out.println("Note that chars returns an IntStream, since there is no CharStream");
        var allLowerCaseLetters = message.chars().filter(x -> x >= 'a' && x <= 'z').distinct().sorted().mapToObj(x -> String.valueOf((char) x)).collect(Collectors.joining(", "));
        System.out.println("Message: " + message);
        System.out.println("All lower case letters: " + allLowerCaseLetters);
    }
}
