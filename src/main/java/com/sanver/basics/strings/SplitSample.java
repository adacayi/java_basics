package com.sanver.basics.strings;

import java.util.Arrays;

public class SplitSample {
    public static void main(String... args) {
        String value = "Selamunaleykum. This#will    be_separated, won't it?";
        String[] split = value.split("[^A-Za-z'?,.]+");// This regex means excluding characters from A to z
        // (The characters in ASCII table between A and z which includes underscore.) and also excluding '?,.
        // So, we will see '?,. on the splits and "be_separated" won't be separated.
        // The separation is caused by the spaces and # in the above scenario.
        // We used + so that consecutive separator characters will be considered as one separator,
        // hence there won't be empty strings in the split array.
        Arrays.asList(split).forEach(x -> System.out.printf("[%s]%n", x));
        //Same cannot be achieved by StringTokenizer, since it does not take Regex.
    }
}
