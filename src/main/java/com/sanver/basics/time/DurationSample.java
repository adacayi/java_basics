package com.sanver.basics.time;

import static com.sanver.basics.utils.ThreadUtils.sleep;

import java.time.Duration;
import java.time.LocalTime;

public class DurationSample {

  public static void main(String[] args) {
    LocalTime start = LocalTime.now();
    sleep(1310);
    Duration duration = Duration.between(start, LocalTime.now());
    Duration another = Duration.ofMinutes(40);// This can also be written as Duration.of(40, ChronoUnit.MINUTES);
    another = another.plus(Duration.ofSeconds(4));// This can also be written as Duration.of(4, ChronoUnit.SECONDS)
    another = another.plus(Duration.ofHours(1)); // This cannot be written as Duration.of(1, ChronoUnit.HOURS)
    showDuration(duration);
    showDuration(another);
  }

  public static void showDuration(Duration duration) {
    System.out.printf("Duration is %02d:%03d\n", duration.getSeconds(), duration.getNano() / 1_000_000);
  }
}
