package com.sanver.basics.time;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;

public class YearSample {

	public static void main(String[] args) {
		Year year = Year.of(2018);
		LocalDate date = year.atMonthDay(MonthDay.of(11, 22));
		LocalDate date40=year.atDay(40);
		System.out.println(date);
		System.out.println(date40);
	}

}
