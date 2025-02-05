package com.sanver.basics.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * {@link LocalDateTime} is a date-time without a time-zone in the ISO-8601 calendar system,
 * such as {@code 2007-12-03T10:15:30}.
 * <p>
 * {@code LocalDateTime} is an immutable date-time object that represents a date-time,
 * often viewed as year-month-day-hour-minute-second. Other date and time fields,
 * such as day-of-year, day-of-week and week-of-year, can also be accessed.
 * Time is represented to nanosecond precision.
 * For example, the value "2nd October 2007 at 13:45.30.123456789" can be
 * stored in a {@code LocalDateTime}.
 * <p>
 * This class does not store or represent a time-zone.
 * Instead, it is a description of the date, as used for birthdays, combined with
 * the local time as seen on a wall clock.
 * It cannot represent an instant on the time-line without additional information
 * such as an offset or time-zone.
 * <p>
 * The ISO-8601 calendar system is the modern civil calendar system used today
 * in most of the world. It is equivalent to the proleptic Gregorian calendar
 * system, in which today's rules for leap years are applied for all time.
 * For most applications written today, the ISO-8601 rules are entirely suitable.
 * However, any application that makes use of historical dates, and requires them
 * to be accurate will find the ISO-8601 approach unsuitable.
 * <p>
 *
 * @since 1.8
 */

public class LocalDateTimeSample {
    public static void main(String... args) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime aPriorDate = now.plusDays(-2).plusWeeks(-1).
                plusMonths(-1).plusHours(2).plusMinutes(5).plusSeconds(25).plusNanos(345);
        print(now);
        print(aPriorDate);
        System.out.printf("%-10s: %s\n", "Days", aPriorDate.until(now, ChronoUnit.DAYS));
        System.out.printf("%-10s: %s\n", "Weeks", aPriorDate.until(now, ChronoUnit.WEEKS));
        System.out.printf("%-10s: %s\n", "Months", aPriorDate.until(now, ChronoUnit.MONTHS));
        System.out.printf("%-10s: %s\n", "Hours", aPriorDate.until(now, ChronoUnit.HOURS));
        System.out.printf("%-10s: %s\n", "Minutes", aPriorDate.until(now, ChronoUnit.MINUTES));
        System.out.printf("%-10s: %s\n", "Seconds", aPriorDate.until(now, ChronoUnit.SECONDS));
        System.out.printf("%-10s: %s\n", "Nanos", aPriorDate.until(now, ChronoUnit.NANOS));
        Duration duration = Duration.between(aPriorDate, now);
        System.out.printf("\n%s\n\n", duration);
        System.out.printf("%-10s: %s\n", "Days", duration.toDays());
        System.out.printf("%-10s: %s\n", "Hours", duration.toHours());
        System.out.printf("%-10s: %s\n", "Minutes", duration.toMinutes());
        System.out.printf("%-10s: %s\n", "Nanos", duration.toNanos());
        System.out.printf("%-10s: %s\n", "Get Seconds", duration.getSeconds());
        System.out.printf("%-10s: %s\n", "Get Nanos", duration.getNano());
    }

    private static void print(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm:ss.SSS");
        System.out.printf("%s\n", formatter.format(dateTime));
    }
}
