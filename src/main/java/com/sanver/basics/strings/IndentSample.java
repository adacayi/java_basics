package com.sanver.basics.strings;

public class IndentSample {

    /**
     * Demonstrates the usage of the {@link String#indent(int)} method.
     * The {@code indent} method allows adding or removing leading spaces
     * to each line in a string. It is useful for formatting multi-line
     * strings or generating indented output.
     *
     * <p>Use Cases:
     * <ul>
     *   <li>Positive indentation (e.g., {@code indent(4)}) adds the specified number of spaces to each line.</li>
     *   <li>Negative indentation (e.g., {@code indent(-2)}) removes leading spaces up to the specified number from each line.</li>
     *   <li>Zero indentation (e.g., {@code indent(0)}) keeps the original spacing without any changes.</li>
     *   <li>For multi-line strings, indentation is applied to each line individually.</li>
     * </ul>
     */
    public static void main(String[] args) {
        String originalText = """
                Line 1     (Original text has no leading spaces)
                  Line 2   (Original text has 2  leading spaces)
                    Line 3 (Original text has 4  leading spaces)
                """;

        System.out.println("Original Text:");
        System.out.println(originalText);

        // Example 1: Indent each line by 4 spaces
        String indentedText = originalText.indent(4);
        System.out.println("Original text indented by 4 spaces:");
        System.out.println(indentedText);

        // Example 2: Reduce indentation by 2 spaces
        String reducedIndentText = originalText.indent(-2);
        System.out.println("Original text reduced indentation by 2 spaces:");
        System.out.println(reducedIndentText);

        // Example 3: Indent by 0 (no change)
        String noChangeText = originalText.indent(0);
        System.out.println("Original text indented by 0 spaces (no change):");
        System.out.println(noChangeText);

        // Example 4: Indent by a larger negative value than leading spaces
        String extraNegativeIndent = originalText.indent(-6);
        System.out.println("Original text indented by -6 spaces (removes all leading spaces):");
        System.out.println(extraNegativeIndent);
    }
}

