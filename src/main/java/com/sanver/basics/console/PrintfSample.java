package com.sanver.basics.console;

/**
 * <h1>Java printf( ) Method Quick Reference</h1>
 * <p>
 * The {@code printf()} method in Java is used for formatted output.
 * The syntax is as follows:
 * </p>
 * <pre>
 * System.out.printf("format-string" [, arg1, arg2, ...]);
 * </pre>
 * <p>
 * <strong>Format String:</strong> A combination of literals and format specifiers.
 * Arguments are required only if there are format specifiers in the format string.
 * </p>
 * <h2>Format Specifiers</h2>
 * <p>
 * A format specifier has the following structure:
 * </p>
 * <pre>
 * % [flags] [width] [.precision] conversion-character
 * </pre>
 * <p>Square brackets denote optional parameters.</p>
 *
 * <h3>Flags:</h3>
 * <ul>
 *   <li>{@code -} : Left-justify (default is to right-justify).</li>
 *   <li>{@code +} : Output a plus (+) or minus (-) sign for numerical values.</li>
 *   <li>{@code 0} : Forces numerical values to be zero-padded (default is blank padding).</li>
 *   <li>{@code ,} : Comma grouping separator (for numbers > 1000).</li>
 *   <li>{@code (space)} : Displays a minus sign if the number is negative or a space if positive.<br>
 *       Example:
 *       <ul>
 *           <li>{@code System.out.printf("% d", 5);} ? {@code " 5"}</li>
 *           <li>{@code System.out.printf("% d", -5);} ? {@code "-5"}</li>
 *       </ul>
 *   </li>
 * </ul>
 *
 * <h3>Width:</h3>
 * <p>
 * Specifies the minimum number of characters to be written to the output.
 * Includes space for expected commas and a decimal point in the determination of the width for numerical values.
 * </p>
 *
 * <h3>Precision:</h3>
 * <p>
 * Used to restrict the output depending on the conversion. Specifies:
 * <ul>
 *   <li>The number of digits of precision for floating-point values.</li>
 *   <li>The length of a substring to extract from a {@code String}.</li>
 * </ul>
 * Numbers are rounded to the specified precision.
 * </p>
 *
 * <h3>Conversion Characters:</h3>
 * <ul>
 *   <li>{@code d} : Decimal integer (e.g., byte, short, int, long).</li>
 *   <li>{@code f} : Floating-point number (e.g., float, double).</li>
 *   <li>{@code c} : Character (Capital {@code C} will uppercase the letter).</li>
 *   <li>{@code s} : String (Capital {@code S} will uppercase all letters in the string).</li>
 *   <li>{@code h} : Hashcode (Hashcode in hexadecimal format).</li>
 *   <li>{@code n} : Newline (Platform-specific newline character; use {@code %n} instead of {@code \n} for greater compatibility).</li>
 * </ul>
 *
 * <h3>Examples:</h3>
 * <pre>
 * // Format a floating-point value with commas and two decimal places:
 * System.out.printf("Total is: $%,.2f%n", dblTotal);
 *
 * // Left-justify a floating-point value:
 * System.out.printf("Total: %-10.2f: ", dblTotal);
 *
 * // Pad a decimal integer with spaces:
 * System.out.printf("% 4d", intValue);
 *
 * // Restrict string length:
 * System.out.printf("%20.10s%n", stringVal);
 *
 * // Print a hashcode for an object:
 * String s = "Hello World";
 * System.out.printf("The String object %s is at hash code %h%n", s, s);
 * </pre>
 */

public class PrintfSample {
    public static void main(String[] args) {
        double number = 6123457.32514;
        double negativeNumber = -number;
        System.out.printf("Number %13s: %,f%n", "", number);
        System.out.printf("%-20s: %-,15.3f %n", "%-,15.3f", number);
        System.out.printf("%-20s: %-,15.2f %n", "%-,15.2f", number); // Notice that half up rounding is done
        System.out.printf("%-20s: %,15.2f %n", "%,15.2f", number);
        System.out.printf("%-20s: %0,15.2f %n", "%0,15.2f", number); // forces numerical values to be zero-padded ( default is blank padding )
        System.out.printf("%-20s: %0,20.8f %n", "%0,20.8f", number); // notice that padding is used for the integer part and the decimal part.
        System.out.printf("%-20s: %+,.2f %n", "%+,.2f", number);
        System.out.printf("%-20s: % ,.2f %n", "% ,.2f", number); // space will display a minus sign if the number is negative or a space if it
        System.out.printf("%-20s: % ,.2f %n", "% ,.2f", negativeNumber);
        var obj = new PrintfSample();
        var hashCode = obj.hashCode();
        System.out.printf("%s object.%nHashcode with %%h: %h.%nHashCode: %d.%nHashCode in hexadecimal: %x%nHashCode in octal: %o%n", obj, obj, hashCode, hashCode, hashCode); // Notice toString appends the hexadecimal hashCode to class name.
        var text = "metal";
        System.out.printf("First 2 characters of \"%s\": %.2s%n", text, text);
        numberSystems();
    }

    public static void numberSystems() {
        int i = 42;
        System.out.printf("%nShowing printing integers in different number systems%n");
        System.out.printf("%nDecimal: %d%n", i);
        System.out.printf("Binary : %s%n", Integer.toBinaryString(i));
        System.out.printf("Octal  : %o%n", i);
        System.out.printf("Hexadecimal (lowercase): %x%n", i);
        System.out.printf("Hexadecimal (uppercase): %X%n", i);
    }

    @Override
    public int hashCode() {
        return 1024;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PrintfSample;
    }
}
