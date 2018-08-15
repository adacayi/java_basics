package com.sanver.basics.time;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class InstantSample {

	public static void main(String[] args) {
		Instant instant = Instant.now();
		System.out.println(instant);
		instant = instant.truncatedTo(ChronoUnit.HOURS);
		// instant=instant.truncatedTo(ChronoUnit.MONTHS);// ChronoUnit.MONTHS is not
		// supported. Hence it will give a runtime error.
		System.out.println(instant);
		instant = instant.plus(Duration.ofHours(1));
		System.out.println(instant);
	}

}
