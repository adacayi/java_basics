package com.sanver.basics.strings;

import java.util.StringJoiner;

/**
 * A sample class demonstrating the usage of the {@link StringJoiner} class in Java.
 * <p>
 * The {@code StringJoinerSample} class includes examples of:
 * <ul>
 *   <li>Adding elements to a {@link StringJoiner}</li>
 *   <li>Merging multiple {@link StringJoiner} instances</li>
 *   <li>Using empty values in {@link StringJoiner}</li>
 * </ul>
 * This class is designed to showcase how {@link StringJoiner} helps with building
 * formatted strings with customizable prefixes, delimiters, and suffixes.
 * </p>
 */
public class StringJoinerSample {

    public static void main(String[] args) {
        // Creating a StringJoiner with a delimiter, prefix, and suffix
        StringJoiner joiner = new StringJoiner(", ", "[", "]");

        // Adding elements
        joiner.add("Apple");
        joiner.add("Banana");
        joiner.add("Cherry");

        // Printing the result
        System.out.println("Joined String: " + joiner);

        // Merging with another StringJoiner
        StringJoiner anotherJoiner = new StringJoiner(" - ", "{", "}");
        anotherJoiner.add("Date");
        anotherJoiner.add("Elderberry");

        // Printing the result
        System.out.println("Another Joiner: " + anotherJoiner);

        joiner.merge(anotherJoiner);
        System.out.println("After merging: " + joiner);

        // Using emptyValue before adding elements
        StringJoiner emptyJoiner = new StringJoiner(", ");
        emptyJoiner.setEmptyValue("No elements");
        System.out.println("Empty Joiner: " + emptyJoiner);

        // Showing how emptyValue disappears after adding elements
        emptyJoiner.add("Fig");
        emptyJoiner.add("Grape");
        System.out.println("Non-empty Joiner: " + emptyJoiner);
    }
}


