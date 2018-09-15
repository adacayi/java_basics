package com.sanver.basics.time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
