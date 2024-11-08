package com.sanver.basics.strings;

import java.util.StringTokenizer;

/**
 * <p>This class demonstrates the usage of the <code>StringTokenizer</code> class in Java.
 * <code>StringTokenizer</code> is a utility class used for splitting strings into tokens.
 * It is part of the <code>java.util</code> package and provides a way to break a string
 * into tokens based on specified delimiters.
 * </p>
 *
 * <h3>StringTokenizer vs. Split Method in String:</h3>
 * <ol>
 *   <li><code>StringTokenizer</code> allows iteration through tokens without creating an array, which may be more memory-efficient in some cases.</li>
 *   <li><code>split()</code> method in <code>String</code> class returns an array of tokens, which may be more convenient but less efficient for large strings.</li>
 * </ol>
 *
 * <h3>Common Methods of StringTokenizer:</h3>
 * <ul>
 *   <li><code>hasMoreTokens()</code>: Checks if there are more tokens available.</li>
 *   <li><code>nextToken()</code>: Returns the next token as a <code>String</code>.</li>
 *   <li><code>countTokens()</code>: Returns the number of tokens remaining in the tokenizer.</li>
 * </ul>
 */
public class StringTokenizerSample {

    /**
     * Demonstrates the usage of <code>StringTokenizer</code> by splitting a sample sentence
     * into words based on specified delimiters.
     *
     * @param sentence  the sentence to be tokenized
     * @param delimiter the delimiter used for splitting the sentence
     */
    public static void demonstrateStringTokenizer(String sentence, String delimiter) {
        StringTokenizer tokenizer = new StringTokenizer(sentence, delimiter);

        System.out.println("Total tokens: " + tokenizer.countTokens());

        while (tokenizer.hasMoreTokens()) {
            System.out.println("Token: " + tokenizer.nextToken());
        }
    }

    public static void main(String[] args) {
        String sentence = "Java is fun to learn!";
        String delimiter = " ";

        System.out.println("Sentence : " + sentence);
        System.out.println("Delimiter: \"" + delimiter + "\"\n");

        demonstrateStringTokenizer(sentence, delimiter);
    }
}
