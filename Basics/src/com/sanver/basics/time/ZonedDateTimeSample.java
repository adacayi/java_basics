package com.sanver.basics.time;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeSample {

	public static void main(String[] args) {
		Instant instant = Instant.now();
		ZonedDateTime zoned = ZonedDateTime.ofInstant(instant, ZoneId.of("Canada/Atlantic"));
		System.out.println(zoned);
	}

}
