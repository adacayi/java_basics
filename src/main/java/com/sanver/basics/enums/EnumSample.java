package com.sanver.basics.enums;

import java.time.LocalDate;

enum Days {
    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY
}

public class EnumSample {
    public static void main(String... args) {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        Days[] days = Days.values();
        Days today = days[dayOfWeek];

        System.out.printf("Today is %s\n\n",today);

        for (Days day : days)
            System.out.println(day);

        System.out.printf("\nThe ordinal of Saturday is %d\n",Days.SATURDAY.ordinal());
    }
}
