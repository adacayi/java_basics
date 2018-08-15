package com.sanver.basics.strings;

import java.time.Duration;
import java.time.LocalTime;

public class StringBufferVsStringBuilderPerformanceTest {
	public static void main(String[] args) {
		String message = "Selamunaleykum";
		int i, count = 300000, messageLength = message.length();
		LocalTime startTime;
		StringBuffer buffer = new StringBuffer();
		StringBuilder builder = new StringBuilder();

		startTime = LocalTime.now();
		for (i = 0; i < count; i++) {
			buffer.append(message);
			buffer.delete(0, messageLength - 1);
		}
		System.out.println("Buffer finish time: " + Duration.between(startTime, LocalTime.now()).toMillis());

		startTime = LocalTime.now();
		for (i = 0; i < count; i++) {
			builder.append(message);
			builder.delete(0, messageLength - 1);
		}
		System.out.println("Builder finish time: " + Duration.between(startTime, LocalTime.now()).toMillis());
	}
}
