package com.sanver.basics.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class ZonedDateTimeSample {

    public static final String US_EASTERN = "US/Eastern";

    public static void main(String[] args) {
        var zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(US_EASTERN)); // Note that the time value did not change, only the zone information is added.
        // This is because LocalDateTime represents a date and time without any time zone information, so there is no way to convert it to a different zone, if there is no zone information.
        System.out.println(zonedDateTime);
        zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(US_EASTERN)); // Since instant.now() queries the system UTC clock to obtain the current instant,
        // it represents a specific point on the UTC (Coordinated Universal Time) timeline. It represents a timestamp since the epoch.
        // ofInstant method converts the UTC timestamp (the Instant) to a date and time representation within the specified time zone (US/Eastern).
        // Thus, the final time in ZonedDateTime is different from the Instant.now().
        System.out.println(zonedDateTime);

        demonstrateDayLightSaving();
    }

    private static void demonstrateDayLightSaving() {
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
