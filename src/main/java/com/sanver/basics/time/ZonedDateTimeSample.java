package com.sanver.basics.time;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class ZonedDateTimeSample {

    public static final String US_EASTERN = "US/Eastern";

    public static void main(String[] args) {
        var format = "%-73s : %s%n";
        var zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(US_EASTERN)); // Note that the time value did not change, only the zone information is added.
        // This is because LocalDateTime represents a date and time without any time zone information, so there is no way to convert it to a different zone, if there is no zone information.
        System.out.printf(format, "US_EASTERN", US_EASTERN);
        System.out.printf(format, "ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(US_EASTERN))", zonedDateTime);
        zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(US_EASTERN)); // Since instant.now() queries the system UTC clock to obtain the current instant,
        // it represents a specific point on the UTC (Coordinated Universal Time) timeline. It represents a timestamp since the epoch.
        // ofInstant method converts the UTC timestamp (the Instant) to a date and time representation within the specified time zone (US/Eastern).
        // Thus, the final time in ZonedDateTime is different from the Instant.now().
        System.out.printf(format, "ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(US_EASTERN))", zonedDateTime);

        var offsetDateTime = convertingZonedDateTimeToOffsetDateTime(format, zonedDateTime);
        convertingOffsetDateTimeToZonedDateTime(format, offsetDateTime);
        convertingZonedDateTimeToLocalDateTime(format, zonedDateTime);
        convertingZonedDateTimeToInstant(format, zonedDateTime);
        durationBetween(zonedDateTime, format, offsetDateTime);
        demonstrateDayLightSaving();
        System.out.println(Duration.between(LocalDateTime.now(), ZonedDateTime.now()));
        System.out.println(Duration.between(ZonedDateTime.now(), LocalDateTime.now()));
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

    private static void convertingZonedDateTimeToLocalDateTime(String format, ZonedDateTime zonedDateTime) {
        print("Converting ZonedDateTime to LocalDateTime");
        System.out.printf(format, "zonedDateTime", zonedDateTime);
        System.out.printf(format, "zonedDateTime.toLocalDateTime()", zonedDateTime.toLocalDateTime());
    }

    private static void convertingOffsetDateTimeToZonedDateTime(String format, OffsetDateTime offsetDateTime) {
        print("Converting OffsetDateTime to ZonedDateTime");
        System.out.printf(format, "offsetDateTime", offsetDateTime);
        System.out.printf(format, "offsetDateTime.toZonedDateTime()", offsetDateTime.toZonedDateTime());
    }

    private static OffsetDateTime convertingZonedDateTimeToOffsetDateTime(String format, ZonedDateTime zonedDateTime) {
        print("Converting ZonedDateTime to OffsetDateTime");
        System.out.printf(format, "zonedDateTime", zonedDateTime);
        var offsetDateTime = zonedDateTime.toOffsetDateTime();
        System.out.printf(format, "zonedDateTime.toOffsetDateTime()", offsetDateTime);
        return offsetDateTime;
    }

    private static void print(String message) {
        System.out.printf("%n--- %s ---%n%n", message);
    }

    private static void demonstrateDayLightSaving() {
        print("Demonstrating Daylight Saving");
        LocalDateTime ld1 = LocalDateTime.of(2025, Month.MARCH, 9, 0, 0);
        LocalDateTime ld2 = LocalDateTime.of(2025, Month.MARCH, 9, 2, 0); // Since at 2:00, the time is set to 3:00 there is no 2:00, thus the time is directly set to 3:00 for the US/Eastern Zone, which is 2 hours from midnight.
        print(ld1, ld2);
        ld1 = LocalDateTime.of(2025, Month.NOVEMBER, 2, 0, 0);
        ld2 = LocalDateTime.of(2025, Month.NOVEMBER, 2, 2, 0); // Since at 2:00, the time is set to 1:00 for the US/Eastern Zone, 2:00 can exist, so 2:00 at that date means 3 hours from midnight.
        System.out.println();
        print(ld1, ld2);
    }

    private static void print(LocalDateTime ld1, LocalDateTime ld2) {
        ZonedDateTime zd1 = ZonedDateTime.of(ld1, ZoneId.of(US_EASTERN));
        System.out.println("First zoned date time : " + zd1);

        ZonedDateTime zd2 = ZonedDateTime.of(ld2, ZoneId.of(US_EASTERN));
        System.out.println("Second zoned date time: " + zd2);
        System.out.println("Hours between: " + ChronoUnit.HOURS.between(zd1, zd2));
    }
}
