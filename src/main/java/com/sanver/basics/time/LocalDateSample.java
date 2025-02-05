package com.sanver.basics.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * The {@code LocalDateSample} class demonstrates the basic and common usages
 * of the {@link java.time.LocalDate} class from the {@code java.time} package.
 *
 * <p>The {@code LocalDate} class represents a date without time or time-zone
 * information. It is part of the Java 8 Date and Time API (JSR-310). The class
 * is immutable and thread-safe, meaning once an instance is created, it cannot
 * be changed.</p>
 *
 * <p>Common features and methods of {@code LocalDate} include:</p>
 * <ul>
 *   <li><b>Creating dates:</b>
 *       <ul>
 *           <li>{@code now()} - Gets the current date from the system clock.</li>
 *           <li>{@code of(int year, int month, int dayOfMonth)} - Creates an instance with the specified year, month, and day.</li>
 *           <li>{@code parse(CharSequence text)} - Parses a date from a string using the standard ISO-8601 format.</li>
 *       </ul>
 *   </li>
 *   <li><b>Manipulating dates:</b>
 *       <ul>
 *           <li>{@code plusDays(long days)}, {@code minusDays(long days)} - Adds or subtracts days.</li>
 *           <li>{@code plusMonths(long months)}, {@code minusMonths(long months)} - Adds or subtracts months.</li>
 *           <li>{@code withYear(int year)}, {@code withMonth(int month)} - Returns a copy with the specified year or month.</li>
 *       </ul>
 *   </li>
 *   <li><b>Retrieving information:</b>
 *       <ul>
 *           <li>{@code getYear()}, {@code getMonth()}, {@code getDayOfMonth()} - Gets specific parts of the date.</li>
 *           <li>{@code getDayOfWeek()} - Retrieves the day of the week.</li>
 *           <li>{@code lengthOfMonth()} - Returns the length of the month in days.</li>
 *       </ul>
 *   </li>
 *   <li><b>Comparing dates:</b>
 *       <ul>
 *           <li>{@code isBefore(LocalDate otherDate)}, {@code isAfter(LocalDate otherDate)} - Checks if a date is before or after another date.</li>
 *           <li>{@code equals(Object obj)} - Checks if two dates are equal.</li>
 *       </ul>
 *   </li>
 *   <li><b>Formatting and parsing:</b>
 *       <ul>
 *           <li>{@code format(DateTimeFormatter formatter)} - Formats the date using a specific pattern.</li>
 *           <li>{@code parse(CharSequence text, DateTimeFormatter formatter)} - Parses a date string using a custom formatter.</li>
 *       </ul>
 *   </li>
 * </ul>
 *
 * <p>This class demonstrates these features with simple examples.</p>
 */
public class LocalDateSample {

    public static void main(String[] args) {
        // Creating LocalDate instances
        var format = "%-56s : %s%n";
        var currentDate = LocalDate.now();
        var specificDate = LocalDate.of(2022, 12, 25);
        var parsedDate = LocalDate.parse("2023-04-15");

        System.out.printf(format, "currentDate = LocalDate.now()", currentDate);
        System.out.printf(format, "specificDate = LocalDate.of(2022, 12, 25)", specificDate);
        System.out.printf(format, "parsedDate = LocalDate.parse(\"2023-04-15\")", parsedDate);

        // Manipulating dates
        var yesterday = currentDate.minusDays(1);
        var nextWeek = currentDate.plusWeeks(1);
        var previousMonth = currentDate.minusMonths(1);
        var changedYear = specificDate.withYear(2025);

        System.out.printf(format, "currentDate.minusDays(1)", yesterday);
        System.out.printf(format, "currentDate.plusWeeks(1)", nextWeek);
        System.out.printf(format, "currentDate.minusMonths(1)",  previousMonth);
        System.out.printf(format, "specificDate.withYear(2025)", changedYear);

        // Retrieving information from dates
        System.out.printf(format, "currentDate.getYear()", currentDate.getYear());
        System.out.printf(format, "currentDate.getMonth()", currentDate.getMonth());
        System.out.printf(format, "currentDate.getDayOfMonth()", currentDate.getDayOfMonth());
        System.out.printf(format, "currentDate.getDayOfWeek()", currentDate.getDayOfWeek());

        // Comparing dates
        boolean isBefore = specificDate.isBefore(currentDate);
        boolean isAfter = parsedDate.isAfter(currentDate);

        System.out.printf(format, "specificDate.isBefore(currentDate)", isBefore);
        System.out.printf(format, "parsedDate.isAfter(currentDate)", isAfter);

        // Formatting dates
        var formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        var formattedDate = currentDate.format(formatter);

        System.out.printf(format, "formatter", "DateTimeFormatter.ofPattern(\"dd MMM yyyy\")");
        System.out.printf(format, "currentDate.format(formatter)", formattedDate);

        // Calculating the difference between dates
        long daysBetween = ChronoUnit.DAYS.between(specificDate, currentDate);
        System.out.printf(format, "ChronoUnit.DAYS.between(specificDate, currentDate)", daysBetween);
    }
}
