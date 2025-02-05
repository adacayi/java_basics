package com.sanver.basics.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class demonstrates the usage of the {@link java.time.format.DateTimeFormatter} class
 * in Java for formatting and parsing date-time objects.
 *
 * <p>The DateTimeFormatter class is part of the java.time package and provides
 * a modern, thread-safe approach to formatting and parsing dates and times.</p>
 *
 * <h2>Common Features of DateTimeFormatter:</h2>
 * <ul>
 *   <li>Thread-safe and immutable, unlike SimpleDateFormat.</li>
 *   <li>Supports predefined formatters like ISO_LOCAL_DATE, ISO_DATE_TIME.</li>
 *   <li>Allows custom formatting using pattern strings.</li>
 *   <li>Provides locale and timezone support.</li>
 * </ul>
 *
 * <h2>Comparison: DateTimeFormatter vs. SimpleDateFormat</h2>
 * <table border="1">
 *   <tr><th>Feature</th><th>DateTimeFormatter</th><th>SimpleDateFormat</th></tr>
 *   <tr><td>Introduced In</td><td>Java 8 (New Date-Time API, part of java.time package)</td><td>Java 1.1 (Legacy API)</td></tr>
 *   <tr><td>Immutable</td><td>Yes</td><td>No</td></tr>
 *   <tr><td>Thread Safety</td><td>Yes</td><td>No</td></tr>
 *   <tr><td>Leniency</td><td>Strict by default. Throws an exception for invalid dates unless explicitly configured otherwise.</td><td>May parse invalid dates (e.g., 32-Jan-2024 rolls over to 1-Feb-2024)</td></tr>
 *   <tr><td>Predefined Formats</td><td>Yes</td><td>No</td></tr>
 *   <tr><td>Locale Support</td><td>Yes</td><td>Limited</td></tr>
 *   <tr><td>Time Zone Handling</td><td>Supported via ZonedDateTime</td><td>Requires Calendar</td></tr>
 * </table>
 *
 * <h2>Formatter Symbols</h2>
 * <table border="1">
 *   <tr><th>Symbol</th><th>Meaning</th><th>Presentation</th><th>Examples</th></tr>
 *   <tr><td>G</td><td>era</td><td>text</td><td>AD; Anno Domini; A</td></tr>
 *   <tr><td>u</td><td>year</td><td>year</td><td>2004; 04</td></tr>
 *   <tr><td>y</td><td>year-of-era</td><td>year</td><td>2004; 04</td></tr>
 *   <tr><td>D</td><td>day-of-year</td><td>number</td><td>189</td></tr>
 *   <tr><td>M/L</td><td>month-of-year</td><td>number/text</td><td>7; 07; Jul; July; J</td></tr>
 *   <tr><td>d</td><td>day-of-month</td><td>number</td><td>10</td></tr>
 *   <tr><td>g</td><td>modified-julian-day</td><td>number</td><td>2451334</td></tr>
 *   <tr><td>Q/q</td><td>quarter-of-year</td><td>number/text</td><td>3; 03; Q3; 3rd quarter</td></tr>
 *   <tr><td>Y</td><td>week-based-year</td><td>year</td><td>1996; 96</td></tr>
 *   <tr><td>w</td><td>week-of-week-based-year</td><td>number</td><td>27</td></tr>
 *   <tr><td>W</td><td>week-of-month</td><td>number</td><td>4</td></tr>
 *   <tr><td>E</td><td>day-of-week</td><td>text</td><td>Tue; Tuesday; T</td></tr>
 *   <tr><td>e/c</td><td>localized day-of-week</td><td>number/text</td><td>2; 02; Tue; Tuesday; T</td></tr>
 *   <tr><td>F</td><td>day-of-week-in-month</td><td>number</td><td>3</td></tr>
 *   <tr><td>a</td><td>am-pm-of-day</td><td>text</td><td>PM</td></tr>
 *   <tr><td>B</td><td>period-of-day</td><td>text</td><td>in the morning</td></tr>
 *   <tr><td>h</td><td>clock-hour-of-am-pm (1-12)</td><td>number</td><td>12</td></tr>
 *   <tr><td>K</td><td>hour-of-am-pm (0-11)</td><td>number</td><td>0</td></tr>
 *   <tr><td>k</td><td>clock-hour-of-day (1-24)</td><td>number</td><td>24</td></tr>
 *   <tr><td>H</td><td>hour-of-day (0-23)</td><td>number</td><td>0</td></tr>
 *   <tr><td>m</td><td>minute-of-hour</td><td>number</td><td>30</td></tr>
 *   <tr><td>s</td><td>second-of-minute</td><td>number</td><td>55</td></tr>
 *   <tr><td>S</td><td>fraction-of-second</td><td>fraction</td><td>978</td></tr>
 *   <tr><td>A</td><td>milli-of-day</td><td>number</td><td>1234</td></tr>
 *   <tr><td>n</td><td>nano-of-second</td><td>number</td><td>987654321</td></tr>
 *   <tr><td>N</td><td>nano-of-day</td><td>number</td><td>1234000000</td></tr>
 * </table>
 * <p>For more information on format patterns, see:
 * <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/format/DateTimeFormatter.html">DateTimeFormatter Patterns</a>
 * </p>
 */
public class DateTimeFormatterSample {
    public static void main(String[] args) {
        // Example of using DateTimeFormatter with predefined format
        var now = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        var formattedDate = now.format(formatter);
        System.out.println("Formatted DateTime: " + formattedDate);

        // Parsing example
        var dateStr = "2025-02-05 14:30:00.172";
        var parsedDate = LocalDateTime.parse(dateStr, formatter);
        System.out.println("Parsed DateTime: " + parsedDate);
    }
}