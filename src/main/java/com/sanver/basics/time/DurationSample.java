package com.sanver.basics.time;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static com.sanver.basics.utils.Utils.sleep;

public class DurationSample {

    public static void main(String[] args) {
        LocalTime start = LocalTime.now();
        sleep(1310);
        Duration duration = Duration.between(start, LocalTime.now());
        Duration other = Duration.ofMinutes(40);// This can also be written as Duration.of(40, ChronoUnit.MINUTES)
        other = other.plus(Duration.ofSeconds(4));// This can also be written as Duration.of(4, ChronoUnit.SECONDS)
        other = other.plus(Duration.ofHours(1)); // This can also be written as Duration.of(1, ChronoUnit.HOURS)
        // Since Duration is immutable in java if we did not set other = other.plus(), other wouldn't change.
        showDuration(duration);
        showDuration(other);
        other = Duration.of(35, ChronoUnit.DAYS).plus(29, ChronoUnit.HOURS); // WEEKS, MONTHS and YEARS result into "UnsupportedTemporalTypeException: Unit must not have an estimated duration"
        System.out.println(other.toDays());
        System.out.println(other.toHours());
        System.out.println(other.toHoursPart());
    }

    public static void showDuration(Duration duration) {
        var format = "Duration is %02d:%02d:%03d%n";
        System.out.printf(format, duration.toMinutesPart(), duration.toSecondsPart(), duration.toMillisPart());
        System.out.printf(format, duration.toMinutes(), duration.toSecondsPart(), duration.toMillisPart());
        System.out.printf(format, duration.toMinutes(), duration.toSecondsPart(), duration.getNano() / 1_000_000); // getNano returns the number of nanoseconds within the second in this duration, not the total nanoseconds in the duration. For that, use toNanos()
        System.out.printf(format, duration.toMinutes(), duration.toSeconds() - duration.toMinutes() * 60, duration.toMillis() - duration.toMinutes() * 60_000 - (duration.toSeconds() - duration.toMinutes() * 60) * 1000); // Another way to calculate the milliseconds
    }
}
