package com.sanver.basics.time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class OffsetDateTimeSample {
    public static final String US_EASTERN_OFFSET = "-05:00";

    public static void main(String[] args) {
        var format = "%-73s : %s%n";
        var offsetDateTime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of(US_EASTERN_OFFSET)); // Note that we can also use ZoneOffset.ofHours(-5) here.
        // Also the time value did not change, only the offset information is added.
        // This is because LocalDateTime represents a date and time without any time zone information, so there is no way to convert it to a different zone, if there is no zone information.
        System.out.printf(format, "US_EASTERN_OFFSET", US_EASTERN_OFFSET);
        System.out.printf(format, "OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of(US_EASTERN_OFFSET))", offsetDateTime);
        offsetDateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.of(US_EASTERN_OFFSET)); // Note that we can also use ZoneId.of("US/Eastern"), since second parameter type is ZoneId.
        // Also note that ZoneOffset extends ZoneId.
        // Finally, Since instant.now() queries the system UTC clock to obtain the current instant,
        // it represents a specific point on the UTC (Coordinated Universal Time) timeline. It represents a timestamp since the epoch.
        // ofInstant method converts the UTC timestamp (the Instant) to a date and time representation within the specified time zone (US/Eastern).
        // Thus, the final time in OffsetDateTime is different from the Instant.now().
        System.out.printf(format, "OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.of(US_EASTERN_OFFSET))", offsetDateTime);

        convertingOffsetDateTimeToZonedDateTime(format, offsetDateTime);
        var zonedDateTime = convertingZonedDateTimeToOffsetDateTime(format);
        convertingOffsetDateTimeToLocalDateTime(format, offsetDateTime);
        convertingZonedDateTimeToInstant(format, zonedDateTime);
        durationBetween(zonedDateTime, format, offsetDateTime);
        demonstrateDayLightSaving();
    }

    private static void durationBetween(ZonedDateTime zonedDateTime, String format, OffsetDateTime offsetDateTime) {
        // The Duration.between method computes the duration between two temporal objects.
        // If the objects are of different types, then the duration is calculated based on the type of the first object, meaning second object is converted to first object.
        // This results in Duration.between(LocalDateTime.now(), ZonedDateTime.now()) working fine,
        // but a runtime error for Duration.between(ZonedDateTime.now(), LocalDateTime.now()),
        // because LocalDateTime has no zone information.
        // DateTimeException: Unable to obtain ZonedDateTime from TemporalAccessor.
        print("Duration between OffsetDateTime and ZonedDateTime");
        zonedDateTime = zonedDateTime.plus(1, ChronoUnit.MONTHS).plus(2, ChronoUnit.WEEKS).plus(2, ChronoUnit.HOURS).plus(175, ChronoUnit.SECONDS);
        System.out.printf(format, "zonedDateTime", zonedDateTime);
        System.out.printf(format, "offsetDateTime", offsetDateTime);
        System.out.printf(format, "Duration.between(zonedDateTime, offsetDateTime)", Duration.between(zonedDateTime, offsetDateTime));
        System.out.printf(format, "Duration.between(offsetDateTime, zonedDateTime)", Duration.between(offsetDateTime, zonedDateTime));
    }

    private static void convertingZonedDateTimeToInstant(String format, ZonedDateTime zonedDateTime) {
        print("Converting ZonedDateTime to Instant");
        System.out.printf(format, "zonedDateTime", zonedDateTime);
        System.out.printf(format, "zonedDateTime.toInstant()", zonedDateTime.toInstant());
    }

    private static void convertingOffsetDateTimeToLocalDateTime(String format, OffsetDateTime offsetDateTime) {
        print("Converting OffsetDateTime to LocalDateTime");
        System.out.printf(format, "offsetDateTime", offsetDateTime);
        System.out.printf(format, "offsetDateTime.toLocalDateTime()", offsetDateTime.toLocalDateTime());
    }

    private static ZonedDateTime convertingZonedDateTimeToOffsetDateTime(String format) {
        print("Converting ZonedDateTime to OffsetDateTime");
        var zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("US/Eastern"));
        System.out.printf(format, "ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(\"US/Eastern\"))", zonedDateTime);
        System.out.printf(format, "zonedDateTime.toOffsetDateTime()", zonedDateTime.toOffsetDateTime());
        return zonedDateTime;
    }

    private static void convertingOffsetDateTimeToZonedDateTime(String format, OffsetDateTime offsetDateTime) {
        print("Converting OffsetDateTime to ZonedDateTime");
        System.out.printf(format, "offsetDateTime", offsetDateTime);
        System.out.printf(format, "offsetDateTime.toZonedDateTime()", offsetDateTime.toZonedDateTime());
    }

    private static void print(String message) {
        System.out.printf("%n--- %s ---%n%n", message);
    }

    private static void demonstrateDayLightSaving() {
        print("Demonstrating Daylight Saving");
        LocalDateTime ld1 = LocalDateTime.of(2025, Month.MARCH, 9, 0, 0);
        LocalDateTime ld2 = LocalDateTime.of(2025, Month.MARCH, 9, 2, 0); // Since offset will stay the same in OffsetDateTime, daylight saving changes won't have any effect.
        // This is not the case with ZonedDateTime, which takes into account the daylight savings.
        print(ld1, ld2);
        ld1 = LocalDateTime.of(2025, Month.NOVEMBER, 2, 0, 0);
        ld2 = LocalDateTime.of(2025, Month.NOVEMBER, 2, 2, 0); // Since offset will stay the same in OffsetDateTime, daylight saving changes won't have any effect.
        System.out.println();
        print(ld1, ld2);
    }

    private static void print(LocalDateTime ld1, LocalDateTime ld2) {
        OffsetDateTime zd1 = OffsetDateTime.of(ld1, ZoneOffset.of(US_EASTERN_OFFSET));
        System.out.println("First offset date time : " + zd1);

        OffsetDateTime zd2 = OffsetDateTime.of(ld2, ZoneOffset.of(US_EASTERN_OFFSET));
        System.out.println("Second offset date time: " + zd2);
        System.out.println("Hours between: " + ChronoUnit.HOURS.between(zd1, zd2));
    }
}