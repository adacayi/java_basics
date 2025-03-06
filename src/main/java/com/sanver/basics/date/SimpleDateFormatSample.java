package com.sanver.basics.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class demonstrates the usage of the {@link SimpleDateFormat} class in Java
 * for formatting and parsing dates.
 *
 * <p>
 * The {@code SimpleDateFormat} class is part of the {@code java.text} package and
 * is used for formatting and parsing dates in a locale-sensitive manner. It
 * allows you to define custom date and time formats using a pattern string.
 * </p>
 *
 * <h2>Key Features of SimpleDateFormat:</h2>
 * <ul>
 *   <li><b>Formatting</b>: Converts a {@link Date} object into a {@link String} representation.</li>
 *   <li><b>Parsing</b>: Converts a {@link String} representation of a date into a {@link Date} object.</li>
 *   <li><b>Customizable Patterns</b>: Supports various formatting patterns to represent dates and times.</li>
 *   <li><b>Locale-Sensitive</b>: Can be configured to work with different locales.</li>
 * </ul>
 *
 * <h2>Comparison: SimpleDateFormat vs. DateTimeFormatter</h2>
 * <table border="1">
 *   <tr><th>Feature</th><th>SimpleDateFormat</th><th>DateTimeFormatter</th></tr>
 *   <tr><td>Introduced In</td><td>Java 1.1 (Legacy API)</td><td>Java 8 (New Date-Time API, part of java.time package)</td></tr>
 *   <tr><td>Immutable</td><td>No</td><td>Yes</td></tr>
 *   <tr><td>Thread Safety</td><td>No</td><td>Yes</td></tr>
 *   <tr><td>Leniency</td><td>May parse invalid dates (e.g., 32-Jan-2024 rolls over to 1-Feb-2024)</td><td>Strict by default. Throws an exception for invalid dates unless explicitly configured otherwise.</td></tr>
 *   <tr><td>Parse Exception</td><td>Throws checked exception {@link java.text.ParseException}</td><td>Throws unchecked exception {@link java.time.format.DateTimeParseException}</td></tr>
 *   <tr><td>Predefined Formats</td><td>No</td><td>Yes</td></tr>
 *   <tr><td>Locale Support</td><td>Limited</td><td>Yes</td></tr>
 *   <tr><td>Time Zone Handling</td><td>Requires Calendar</td><td>Supported via ZonedDateTime</td></tr>
 * </table>
 *
 * <h2>Formatter Symbols</h2>
 * <table border="1">
 *   <tr><th>Symbol</th><th>Meaning</th><th>Presentation</th><th>Examples</th></tr>
 *   <tr><td>G</td><td>Era designator</td><td>Text</td><td>AD</td></tr>
 *   <tr><td>y</td><td>Year</td><td>Year</td><td>1996; 96</td></tr>
 *   <tr><td>M</td><td>Month in year</td><td>Month</td><td>July; Jul; 07</td></tr>
 *   <tr><td>w</td><td>Week in year</td><td>Number</td><td>27</td></tr>
 *   <tr><td>W</td><td>Week in month</td><td>Number</td><td>2</td></tr>
 *   <tr><td>D</td><td>Day in year</td><td>Number</td><td>189</td></tr>
 *   <tr><td>d</td><td>Day in month</td><td>Number</td><td>10</td></tr>
 *   <tr><td>F</td><td>Day of week in month</td><td>Number</td><td>2</td></tr>
 *   <tr><td>E</td><td>Day name in week</td><td>Text</td><td>Tuesday; Tue</td></tr>
 *   <tr><td>a</td><td>Am/pm marker</td><td>Text</td><td>PM</td></tr>
 *   <tr><td>H</td><td>Hour in day (0-23)</td><td>Number</td><td>0</td></tr>
 *   <tr><td>k</td><td>Hour in day (1-24)</td><td>Number</td><td>24</td></tr>
 *   <tr><td>K</td><td>Hour in am/pm (0-11)</td><td>Number</td><td>0</td></tr>
 *   <tr><td>h</td><td>Hour in am/pm (1-12)</td><td>Number</td><td>12</td></tr>
 *   <tr><td>m</td><td>Minute in hour</td><td>Number</td><td>30</td></tr>
 *   <tr><td>s</td><td>Second in minute</td><td>Number</td><td>55</td></tr>
 *   <tr><td>S</td><td>Millisecond</td><td>Number</td><td>978</td></tr>
 *   <tr><td>z</td><td>Time zone</td><td>General time zone</td><td>Pacific Standard Time; PST; GMT-08:00</td></tr>
 *   <tr><td>Z</td><td>Time zone</td><td>RFC 822 time zone</td><td>-0800</td></tr>
 * </table>
 *
 * <p>
 * <b>Note:</b> {@code SimpleDateFormat} is not thread-safe. If multiple threads access a format concurrently, it must be synchronized externally.
 * It is recommended to use {@link java.time.format.DateTimeFormatter} instead which is immutable and thread-safe.
 * </p>
 */
public class SimpleDateFormatSample {

    public static void main(String[] args) {
        // Example 1: Formatting a Date
        Date now = new Date();
        var formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        var formattedDate = formatter.format(now);
        System.out.println("Formatted Date: " + formattedDate);

        // Example 2: Parsing a Date
        String dateString = "2024-03-15 10:30:00";
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        try {
            Date parsedDate = parser.parse(dateString);
            System.out.println("Parsed Date: " + parsedDate);
        } catch (ParseException e) { // Note that parse throws a checked exception, unlike DateTimeFormatter.parse() which throws an unchecked exception.
            System.out.println("Error parsing date: " + e.getMessage());
            //Handle the exception.
        }

        // Example 3: Showing that it is not strict by default.
        String dateString2 = "2024-02-30 10:30:00";
        SimpleDateFormat parser2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date parsedDate2 = parser2.parse(dateString2);
            System.out.println("Parsed Date2: " + parsedDate2); // Note that this will be parsed as 2024-03-01 10:30:00
        } catch (ParseException e) {
            System.out.println("Error parsing date2: " + e.getMessage());
            //Handle the exception.
        }

        // Example 4: Using different patterns
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate2 = formatter2.format(now);
        System.out.println("Formatted Date 2: " + formattedDate2);

        SimpleDateFormat formatter3 = new SimpleDateFormat("E, MMM dd yyyy", Locale.US);
        String formattedDate3 = formatter3.format(now);
        System.out.println("Formatted Date 3: " + formattedDate3);
    }
}