package com.sanver.basics.date;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class GregorianCalanderSample {

	public static void main(String[] args) {
		GregorianCalendar now = new GregorianCalendar();
		System.out.println("Now is " + getDate(now));
		now.add(Calendar.DATE, 1);
		// now.clear(Calendar.HOUR_OF_DAY | Calendar.MINUTE | Calendar.SECOND |
		// Calendar.MILLISECOND); This usage does not work
		now.clear(Calendar.HOUR_OF_DAY);// This does not work
		now.clear(Calendar.MINUTE);
		now.clear(Calendar.SECOND);
		now.clear(Calendar.MILLISECOND);
		now.set(Calendar.HOUR_OF_DAY, 0);
		System.out.println("Tomorrow is " + getDate(now));
		now.clear();
		System.out.println("Begin date is " + getDate(now));
		System.out.println("My birth date is " + getDate(new GregorianCalendar(1983, 8, 10)));// We have to subtract 1
																								// from months, since it
																								// starts from 0
	}

	public static String getDate(Calendar calendar) {
		return "" + calendar.get(Calendar.DATE) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
				+ calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND) + "."
				+ calendar.get(Calendar.MILLISECOND);// Months begin with 0, so we added 1.
	}
}
