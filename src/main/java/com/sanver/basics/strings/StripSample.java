package com.sanver.basics.strings;

public class StripSample {
    /**
     * In Java, both {@code String.strip()} and {@code String.trim()} methods are used to remove whitespace
     * from the beginning and end of a string. However, there are key differences in how they handle whitespace
     * characters and the specific versions of Java in which they were introduced.
     *
     * <h2>Key Differences between strip() and trim()</h2>
     *
     * <h3>Whitespace Handling:</h3>
     * <p><b>trim():</b> Only removes ASCII whitespace characters (i.e., characters with Unicode values less
     * than or equal to U+0020), which includes spaces (' '), tabs ('\t'), newlines ('\n'), and a few other ASCII
     * control characters.</p>
     * <p><b>strip():</b> Removes all types of whitespace characters according to the Unicode standard,
     * which includes a much broader set of whitespace characters (e.g., non-breaking spaces and other Unicode
     * whitespace characters).</p>
     * <p><b>stripLeading():</b> Removes all types of whitespace characters from the beginning of the String. There is no trim equivalent for this.</p>
     * <p><b>stripTrailing():</b> Removes all types of whitespace characters from the end of the String. There is no trim equivalent for this.</p>
     *
     * <h3>Introduction Version:</h3>
     * <p><b>trim():</b> Available since Java 1.0.</p>
     * <p><b>strip():</b> Introduced in Java 11.</p>
     *
     * <h3>Internal Implementation:</h3>
     * <p><b>trim():</b> Checks each character in the string until it finds a non-whitespace ASCII character,
     * then returns the substring.</p>
     * <p><b>strip():</b> Uses {@code Character.isWhitespace()} method, which checks a broader set of Unicode
     * whitespace characters.</p>
     */

    public static void main(String[] args) {
        System.out.printf("Character.isWhitespace('\\u2000'): %s%n%n", Character.isWhitespace('\u2000'));

        // Strings with leading and trailing spaces, including Unicode whitespace
        String regularSpaces = "   Hello, World!   ";
        String unicodeSpaces = "\u2000\u2001\u2002Hello, World!\u2002\u2001\u2000";

        // Using trim()
        String trimmedRegular = regularSpaces.trim();
        String trimmedUnicode = unicodeSpaces.trim();

        // Using strip()
        String strippedRegular = regularSpaces.strip();
        String strippedUnicode = unicodeSpaces.strip();

        // Print original and processed strings
        System.out.println("Original string with regular spaces: '" + regularSpaces + "'");
        System.out.println("After trim() : '" + trimmedRegular + "'");
        System.out.println("After strip(): '" + strippedRegular + "'");
        System.out.println();

        System.out.println("Original string with Unicode spaces: '" + unicodeSpaces + "'");
        System.out.println("After trim() : '" + trimmedUnicode + "'");
        System.out.println("After strip(): '" + strippedUnicode + "'");

        System.out.println("unicodeSpaces.stripLeading() : " + unicodeSpaces.stripLeading());
        System.out.println("unicodeSpaces.stripTrailing(): " + unicodeSpaces.stripTrailing());

        var message = """
                First Line
                   Second Line
                        Third Line
                Last Line  
                """;
        System.out.println(message);
        System.out.println(removeIndentation(message));
    }

    /**
     * Removes indentation from the given string for each line by using <code>String.replaceAll("(?m)^\\s+", "")</code>.
     * <ul>
     * <li><code>(?m)</code>: Enables multiline mode, so <code>^</code> matches the start of each line (not just the start of the whole string).</li>
     * <li><code>^</code>: Matches the beginning of each line.</li>
     * <li><code>\\s+</code>: Matches one or more whitespace characters.</li>
     * </ul>
     * @param input a String indentation of which will be removed for each line
     * @return indentation removed String
     */
    public static String removeIndentation(String input) {
        return input.replaceAll("(?m)^\\s+", "");
    }
}
