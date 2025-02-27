package com.sanver.basics.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class ZonedDateTimeSample {

    public static void main(String[] args) {
        Instant instant = Instant.now();
        ZonedDateTime zoned = ZonedDateTime.ofInstant(instant, ZoneId.of("Canada/Atlantic"));
        System.out.println(zoned);

        demonstrateDayLightSaving();
    }

    private static void demonstrateDayLightSaving() {
        LocalDateTime ld1 = LocalDateTime.of(2025, Month.MARCH, 9, 0, 0);
        LocalDateTime ld2 = LocalDateTime.of(2025, Month.MARCH, 9, 2, 0); // Since at 2:00, the time is set to 3:00 there is no 2:00, thus the time is directly set to 3:00, which is 2 hours from midnight.
        print(ld1, ld2);
        ld1 = LocalDateTime.of(2025, Month.NOVEMBER, 2, 0, 0);
        ld2 = LocalDateTime.of(2025, Month.NOVEMBER, 2, 2, 0); // Since at 2:00, the time is set to 1:00, 2:00 can exist, so 2:00 at that date means 3 hours from midnight.
        System.out.println();
        print(ld1, ld2);
    }

    private static void print(LocalDateTime ld1, LocalDateTime ld2) {
        ZonedDateTime zd1 = ZonedDateTime.of(ld1, ZoneId.of("US/Eastern"));
        System.out.println("First zoned date time : " + zd1);

        ZonedDateTime zd2 = ZonedDateTime.of(ld2, ZoneId.of("US/Eastern"));
        System.out.println("Second zoned date time: " + zd2);
        System.out.println("Hours between: " + ChronoUnit.HOURS.between(zd1, zd2));
    }

}
