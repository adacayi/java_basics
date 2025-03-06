package com.sanver.basics.strings;

import java.util.Arrays;

public class SplitSample {
    public static void main(String... args) {
        var message = "this-is-a-message---";
        var format = "%-36s : %s%n";
        System.out.printf(format, "message", message);
        System.out.printf(format, "message.split(\"-\")", Arrays.toString(message.split("-"))); // Note that trailing empty strings are not included
        System.out.printf(format, "message.split(\",\")", Arrays.toString(message.split(","))); // If the expression does not match any part of the input then the resulting array has just one element, namely this string.
        System.out.printf(format, "\",,,,,a,,\".split(\",\")", Arrays.toString(",,,,,a,,".split(","))); // Note that trailing empty strings are not included
        System.out.printf(format, "\"a,,,,,b,,\".split(\",\")", Arrays.toString("a,,,,,b,,".split(",")));
        System.out.printf(format, "message.split(\"-\",2)", Arrays.toString(message.split("-",2))); // This means it splits limit - 1 times at most, thus resulting in an array of length limit at most.
        System.out.printf(format, "message.split(\"-\",0)", Arrays.toString(message.split("-",0))); // This means no limit and this is same as split("-"). This does not include trailing empty strings.
        System.out.printf(format, "message.split(\"-\",-1)", Arrays.toString(message.split("-",-1))); // This includes trailing empty strings
        System.out.printf(format, "(message.splitWithDelimiters(\"-\", 0)", Arrays.toString(message.splitWithDelimiters("-", 0)));
        System.out.printf(format, "message.splitWithDelimiters(\"-\", -1)", Arrays.toString(message.splitWithDelimiters("-", -1)));

        System.out.println();

        String value = "Selamunaleykum. This#will    be_separated, won't it?# #a";
        String[] split = value.split("[^A-z'?,.]+");// This regex means excluding characters from A to z
        // (The characters in ASCII table between A and z which includes underscore.) and also excluding '?,.
        // So, we will see '?,. on the splits and "be_separated" won't be separated.
        // The separation is caused by the spaces and # in the above scenario.
        // We used + so that consecutive separator characters will be considered as one separator,
        // hence there won't be empty strings in the split array.
        // Also note that the split method does not include trailing empty strings in the resulting array (the ones caused by # # at the end of `value`).
        // Put one more character to the end of `value` to see the empty strings in the resulting array.
        Arrays.asList(split).forEach(x -> System.out.printf("[%s]%n", x));
        //Same cannot be achieved by StringTokenizer, since it does not take Regex.
    }
}
