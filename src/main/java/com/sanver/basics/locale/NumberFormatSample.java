package com.sanver.basics.locale;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * A sample class demonstrating the usage of {@link NumberFormat} class.
 * <p>
 * The {@link NumberFormat} class provides methods to format and parse numbers and currencies
 * in a locale-sensitive manner. Key features include:
 * </p>
 * <ul>
 *   <li>Formatting numbers with locale-specific decimal separators and grouping characters</li>
 *   <li>Formatting currency values with appropriate symbols and formatting rules</li>
 *   <li>Formatting percentages with proper scaling and symbols</li>
 *   <li>Parsing localized number strings back into numeric values</li>
 * </ul>
 *
 * <p>Common methods demonstrated in this class:</p>
 * <ul>
 *   <li>{@link NumberFormat#getInstance()} - Gets a general-purpose number format for the default locale</li>
 *   <li>{@link NumberFormat#getCurrencyInstance()} - Gets a currency format for the default locale</li>
 *   <li>{@link NumberFormat#getPercentInstance()} - Gets a percentage format for the default locale</li>
 *   <li>{@link NumberFormat#setMinimumFractionDigits(int)} - Controls minimum decimal digits</li>
 *   <li>{@link NumberFormat#setMaximumFractionDigits(int)} - Controls maximum decimal digits</li>
 *   <li>{@link NumberFormat#format(double)} - Formats a numeric value into a string</li>
 *   <li>{@link NumberFormat#parse(String)} - Parses a string into a numeric value</li>
 * </ul>
 */
public class NumberFormatSample {
    public static void main(String[] args) {
        // Example 1: Basic number formatting with default locale
        NumberFormat numFormat = NumberFormat.getInstance();
        String formattedNumber = numFormat.format(12345.6789);
        System.out.println("Default locale number: " + formattedNumber);

        // Example 2: Basic number formatting with default locale and compact number instance
        numFormat = NumberFormat.getCompactNumberInstance();
        formattedNumber = numFormat.format(12345.6789);
        System.out.println("Default locale compact number (Style SHORT): " + formattedNumber);

        numFormat = NumberFormat.getCompactNumberInstance(Locale.getDefault(), NumberFormat.Style.SHORT);
        formattedNumber = numFormat.format(12345.6789);
        System.out.println("Default locale compact number SHORT style: " + formattedNumber);

        numFormat = NumberFormat.getCompactNumberInstance(Locale.getDefault(), NumberFormat.Style.LONG);
        formattedNumber = numFormat.format(12345.6789);
        System.out.println("Default locale compact number LONG style: " + formattedNumber);

        // Example 3: Currency formatting with US locale
        NumberFormat usCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        System.out.println("US currency: " + usCurrencyFormat.format(12345.6789));

        // Example 4: Currency formatting with German locale
        NumberFormat deCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        System.out.println("German currency: " + deCurrencyFormat.format(12345.6789));

        // Example 5: Currency formatting with default locale
        NumberFormat localCurrencyFormat = NumberFormat.getCurrencyInstance(); // We can also use NumberFormat.getCurrencyInstance(Locale.getDefault())
        System.out.println("Local currency: " + localCurrencyFormat.format(12345.6789));

        // Example 6: Percentage formatting
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        System.out.println("Percentage: " + percentFormat.format(0.8567));

        // Example 7: Percentage formatting with another locale
        percentFormat = NumberFormat.getPercentInstance(Locale.of("tr"));
        System.out.println("Turkish Percentage: " + percentFormat.format(0.8567));

        // Example 8: Controlling decimal places
        NumberFormat preciseFormat = NumberFormat.getInstance();
        preciseFormat.setMinimumFractionDigits(3);
        preciseFormat.setMaximumFractionDigits(5);
        System.out.println("Precise number: " + preciseFormat.format(123.456789));
        System.out.println("Precise number: " + preciseFormat.format(123.45));

        // Example 9: Parsing numeric strings
        try {
            Number parsedNumber = NumberFormat.getInstance(Locale.of("en")).parse("12,345.679");
            System.out.println("Parsed value: " + parsedNumber.doubleValue());
        } catch (ParseException e) {
            System.err.println("Parse error: " + e.getMessage());
        }
    }
}