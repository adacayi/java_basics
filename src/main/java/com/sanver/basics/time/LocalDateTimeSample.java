package com.sanver.basics.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSSSSSSSS");
    private static final String FORMAT = "%-80s: %s%n";
    public static void main(String... args) {
        var now = LocalDateTime.now();
        var aPriorDate = now.plusDays(-2).plusWeeks(-1).
                plusMonths(-1).plusHours(2).plusMinutes(5).plusSeconds(25).plusNanos(345);
        print("LocalDateTime.now()", now);
        print("aPriorDate", aPriorDate);
        print("aPriorDate.adjustInto(now)", (LocalDateTime)aPriorDate.adjustInto(now));
        System.out.printf(FORMAT, "aPriorDate.isAfter(now)", aPriorDate.isAfter(now));
        System.out.printf(FORMAT, "aPriorDate.isBefore(now)", aPriorDate.isBefore(now));
        System.out.printf(FORMAT, "aPriorDate.toLocalDate()", aPriorDate.toLocalDate());
        OffsetDateTime offsetDateTime = aPriorDate.atOffset(ZoneOffset.UTC);
        System.out.printf(FORMAT, "aPriorDate.atOffset(ZoneOffset.UTC)", offsetDateTime);
        System.out.printf(FORMAT, "aPriorDate.atOffset(ZoneOffset.ofHours(3))", aPriorDate.atOffset(ZoneOffset.ofHours(3))); // Note that this does not change hours, while it did for Instant.atOffset
        System.out.printf(FORMAT, "aPriorDate.atZone(ZoneId.systemDefault())", aPriorDate.atZone(ZoneId.systemDefault()));
        /*
        Here are the key differences between OffsetDateTime and ZonedDateTime:
        OffsetDateTime:

        Only tracks the offset from UTC (e.g., +02:00, -05:00)
        Doesn't handle daylight savings time (DST) transitions
        Lighter weight, simpler calculations
        Good for timestamps in logs, databases, or APIs
        Better for fixed point-in-time records

        ZonedDateTime:

        Tracks full time zone information (e.g., "America/New_York")
        Handles DST transitions automatically
        Knows about time zone rules and changes
        Good for calendar-based applications, scheduling
        Better for human-oriented date/time operations
         */
        var period = Period.between(aPriorDate.toLocalDate(), now.toLocalDate());
        System.out.printf(FORMAT, "period = Period.between(aPriorDate.toLocalDate(), now.toLocalDate())", period);
        System.out.printf(FORMAT, "period.getMonths()", period.getMonths());
        System.out.printf(FORMAT, "period.getDays()", period.getDays());
        System.out.printf(FORMAT, "aPriorDate.until(now, ChronoUnit.DAYS)", aPriorDate.until(now, ChronoUnit.DAYS));
        System.out.printf(FORMAT, "aPriorDate.until(now, ChronoUnit.WEEKS)", aPriorDate.until(now, ChronoUnit.WEEKS));
        System.out.printf(FORMAT, "aPriorDate.until(now, ChronoUnit.MONTHS)", aPriorDate.until(now, ChronoUnit.MONTHS));
        System.out.printf(FORMAT, "aPriorDate.until(now, ChronoUnit.HOURS)", aPriorDate.until(now, ChronoUnit.HOURS));
        System.out.printf(FORMAT, "aPriorDate.until(now, ChronoUnit.MINUTES)", aPriorDate.until(now, ChronoUnit.MINUTES));
        System.out.printf(FORMAT, "aPriorDate.until(now, ChronoUnit.SECONDS)", aPriorDate.until(now, ChronoUnit.SECONDS));
        System.out.printf(FORMAT, "aPriorDate.until(now, ChronoUnit.NANOS)", aPriorDate.until(now, ChronoUnit.NANOS));
        var duration = Duration.between(aPriorDate, now);
        System.out.printf(FORMAT, "duration = Duration.between(aPriorDate, now)", duration);
        System.out.printf(FORMAT, "duration.toDays()", duration.toDays());
        System.out.printf(FORMAT, "duration.toHours()", duration.toHours());
        System.out.printf(FORMAT, "duration.toMinutes()", duration.toMinutes());
        System.out.printf(FORMAT, "duration.toSeconds()", duration.toSeconds());
        System.out.printf(FORMAT, "duration.toSecondsPart()", duration.toSecondsPart());
        System.out.printf(FORMAT, "duration.toNanos()", duration.toNanos());
        System.out.printf(FORMAT, "duration.toNanosPart()", duration.toNanosPart());
        System.out.printf(FORMAT, "duration.getSeconds()", duration.getSeconds());
        System.out.printf(FORMAT, "duration.getNano()", duration.getNano());

        // Converting to an Instant
        System.out.printf(FORMAT, "now.toInstant(ZoneOffset.UTC)", now.toInstant(ZoneOffset.UTC)); // Note that toInstant requires ZoneOffset, while ofInstant requires ZoneId. ZoneOffset extends ZoneId, thus ZoneOffset can be used in ofInstant as well.
        System.out.printf(FORMAT, "now.toInstant(ZoneOffset.ofHours(-5))", now.toInstant(ZoneOffset.ofHours(-5)));
        System.out.printf(FORMAT, "LocalDateTime.ofInstant(now.toInstant(ZoneOffset.UTC), ZoneId.of(\"GMT-5\"))", LocalDateTime.ofInstant(now.toInstant(ZoneOffset.UTC), ZoneId.of("GMT-5")));
        System.out.printf(FORMAT, "LocalDateTime.ofInstant(now.toInstant(ZoneOffset.UTC), ZoneOffset.ofHours(-5))", LocalDateTime.ofInstant(now.toInstant(ZoneOffset.UTC), ZoneOffset.ofHours(-5)));
        System.out.printf(FORMAT, "now.toInstant(ZoneOffset.ofHours(-5))", now.toInstant(ZoneOffset.ofHours(-5)));
        System.out.printf(FORMAT, "now.toInstant(ZoneId.systemDefault().getRules().getOffset(now))", now.toInstant(ZoneId.systemDefault().getRules().getOffset(now)));
    }

    private static void print(String content, LocalDateTime dateTime) {
        System.out.printf(FORMAT, content, formatter.format(dateTime));
    }
}
