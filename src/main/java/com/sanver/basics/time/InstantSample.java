package com.sanver.basics.time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * {@link Instant} is an instantaneous point on the time-line.
 * <p>
 * This class models a single instantaneous point on the time-line.
 * This might be used to record event time-stamps in the application.
 * <p>
 * The range of an instant requires the storage of a number larger than a {@code long}.
 * To achieve this, the class stores a {@code long} representing epoch-seconds and an
 * {@code int} representing nanosecond-of-second, which will always be between 0 and 999,999,999.
 * The epoch-seconds are measured from the standard Java epoch of {@code 1970-01-01T00:00:00Z}
 * where instants after the epoch have positive values, and earlier instants have negative values.
 * For both the epoch-second and nanosecond parts, a larger value is always later on the time-line
 * than a smaller value.
 *
 * @since 1.8
 */
public class InstantSample {

    public static void main(String[] args) {
        Instant instant = Instant.now();
        var format = "%-56s : %s%n";
        var startInstant = Instant.ofEpochSecond(3).plusMillis(1300).plusNanos(987);
        System.out.printf(format, "startInstant: ", startInstant);
        System.out.printf(format, "startInstant.getEpochSecond()", startInstant.getEpochSecond());
        System.out.printf(format, "startInstant.getNano()", startInstant.getNano());
        System.out.printf(format, "instant", instant);
        System.out.printf(format, "instant.isBefore(startInstant): ", instant.isBefore(startInstant));
        System.out.printf(format, "instant.isAfter(startInstant): ", instant.isAfter(startInstant));
        System.out.printf(format, "instant.getNano()", instant.getNano());
        System.out.printf(format, "getting micros: Duration.between(instant.truncatedTo(ChronoUnit.MILLIS), instant.truncatedTo(ChronoUnit.MICROS)).toNanos()/1000", Duration.between(instant.truncatedTo(ChronoUnit.MILLIS), instant.truncatedTo(ChronoUnit.MICROS)).toNanos()/1000);
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        System.out.printf(format, "instant.atZone(ZoneId.systemDefault())", zonedDateTime);
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.UTC);
        System.out.printf(format, "instant.atOffset(ZoneOffset.UTC)", offsetDateTime);
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
        System.out.printf(format, "instant.atOffset(ZoneOffset.ofHours(3))", instant.atOffset(ZoneOffset.ofHours(3)));
        System.out.printf(format, "instant.truncatedTo(ChronoUnit.MICROS)", instant.truncatedTo(ChronoUnit.MICROS));
        System.out.printf(format, "instant.truncatedTo(ChronoUnit.MILLIS)", instant.truncatedTo(ChronoUnit.MILLIS));
        System.out.printf(format, "instant.truncatedTo(ChronoUnit.SECONDS)", instant.truncatedTo(ChronoUnit.SECONDS));
        System.out.printf(format, "instant.truncatedTo(ChronoUnit.MINUTES)", instant.truncatedTo(ChronoUnit.MINUTES));
        System.out.printf(format, "instant.truncatedTo(ChronoUnit.HOURS)", instant.truncatedTo(ChronoUnit.HOURS));
        System.out.printf(format, "instant.truncatedTo(ChronoUnit.DAYS)", instant.truncatedTo(ChronoUnit.DAYS));
        // instant=instant.truncatedTo(ChronoUnit.WEEKS);// WEEKS, MONTHS and YEARS result in
        // UnsupportedTemporalTypeException: Unit is too large to be used for truncation
        System.out.printf(format, "instant.plusNanos(12)", instant.plusNanos(12));
        System.out.printf(format, "instant.plusMillis(500)", instant.plusMillis(500));
        System.out.printf(format, "instant.plusSeconds(12)", instant.plusSeconds(12));
        System.out.printf(format, "instant.plus(Duration.ofHours(1))", instant.plus(Duration.ofHours(1)));
        System.out.printf(format, "instant.plus(5, ChronoUnit.DAYS)", instant.plus(5, ChronoUnit.DAYS));

        // Converting Instant to LocalDateTime

        /*
        Here are the key differences between LocalDateTime and Instant:
        LocalDateTime:

        Zone-agnostic date and time (just a date and time without timezone)
        Not tied to a specific point on the timeline
        Represents a date and time from a calendar perspective
        Good for representing events like "meeting at 2pm on March 1st"
        Cannot be directly compared for timeline ordering
        Does not track timezone or offset information

        Instant:

        Represents a specific point on the UTC timeline
        Tracks exact number of seconds/nanoseconds since epoch (1970-01-01T00:00:00Z)
        Always comparable (can determine which came first)
        Good for timestamps, logging, and exact time measurements
        Must be converted to a zone/offset to get human-readable date/time components
        More suitable for machine-oriented timestamps
         */
        System.out.printf(format, "LocalDateTime.ofInstant(instant, ZoneId.systemDefault())", LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));
        System.out.printf(format, "instant.atZone(ZoneId.systemDefault()).toLocalDateTime()", zonedDateTime.toLocalDateTime());
        System.out.printf(format, "instant.atOffset(ZoneId.systemDefault().getRules().getOffset(instant)).toLocalDateTime()", instant.atOffset(ZoneId.systemDefault().getRules().getOffset(instant)).toLocalDateTime());
    }
}
