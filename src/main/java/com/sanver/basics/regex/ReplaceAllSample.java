package com.sanver.basics.regex;

public class ReplaceAllSample {
    public static void main(String... args) {
        String message="This is a text  .   It is to show how to \n\t remove " +
                "redundant whitespaces\t. Let' s \n begin  \r\n. ";
        System.out.println(message);
        String removeMultiple = message.replaceAll("\\s+", " ");
        System.out.println(removeMultiple);
        String removedSpacesBeforeCommaAndDot=removeMultiple.replaceAll("(\\s+)([.,])","$2");
        System.out.println(removedSpacesBeforeCommaAndDot);
        String apostropheSpaceRemoved = removedSpacesBeforeCommaAndDot.replaceAll("'\\s", "'");
        System.out.println(apostropheSpaceRemoved);
    }
}
