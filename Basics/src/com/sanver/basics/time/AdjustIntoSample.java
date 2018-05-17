package com.sanver.basics.time;

import java.time.LocalDate;
import java.time.Year;

public class AdjustIntoSample {

	public static void main(String[] args) {
		LocalDate date = LocalDate.of(2012, 2, 29);
		Year year = Year.of(2014);
		System.out.println(date);
		System.out.println(year);
		System.out.println(year.atDay(3));
		System.out.println(year.adjustInto(date));
		System.out.println(date);
		System.out.println(Year.now());
	}

}
