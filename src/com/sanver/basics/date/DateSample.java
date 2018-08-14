package com.sanver.basics.date;

import java.util.Date;

public class DateSample {
	public static void main(String[] args) {
		Date now = new Date();
		Date beginDate = new Date(0);
		Long millisecondsToNow = now.getTime();
		System.out.println("Now is " + now);
		System.out.println("Java Date class initial date is " + beginDate);
		System.out.printf("From initial date to now in milliseconds is %,d" , millisecondsToNow);
	}
}
