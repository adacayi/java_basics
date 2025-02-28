package com.sanver.basics.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class OffsetDateTimeSample {
    public static final String US_EASTERN_OFFSET = "-05:00";

    public static void main(String[] args) {
        var offsetDateTime = OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of(US_EASTERN_OFFSET)); // Note that we can also use ZoneOffset.ofHours(-5) here.
        // Also the time value did not change, only the offset information is added.
        // This is because LocalDateTime represents a date and time without any time zone information, so there is no way to convert it to a different zone, if there is no zone information.
        System.out.println(offsetDateTime);
        offsetDateTime = OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.of(US_EASTERN_OFFSET)); // Note that we can also use ZoneIf.of("US/Eastern"), since second parameter type is ZoneId.
        // Also note that oneOffset extends ZoneId.
        // Finally, Since instant.now() queries the system UTC clock to obtain the current instant,
        // it represents a specific point on the UTC (Coordinated Universal Time) timeline. It represents a timestamp since the epoch.
        // ofInstant method converts the UTC timestamp (the Instant) to a date and time representation within the specified time zone (US/Eastern).
        // Thus, the final time in OffsetDateTime is different from the Instant.now().
        System.out.println(offsetDateTime);

        demonstrateDayLightSaving();
    }

    private static void demonstrateDayLightSaving() {
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