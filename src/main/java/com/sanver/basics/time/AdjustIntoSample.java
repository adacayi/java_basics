package com.sanver.basics.time;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;

public class AdjustIntoSample {

    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2012, 2, 29);
        Year year = Year.of(2014);
        print(date, year);

        LocalDate dateAtThisYear = (LocalDate) year.adjustInto(date);  // Adjusts the specified temporal object to have this year. This returns a temporal object of the same observable type as the input with the year changed to be the same as this.
        System.out.printf("year.adjustInto(date): %s. %s%n", dateAtThisYear, "Adjusts the date to have this year. Since February has 28 days in 2014, it will result into 28th of February rather than 29th.");
        print(date, year); // This is to show that LocalDate and Year are immutable classes.

        System.out.println("LocalDate.now(): " + LocalDate.now());
        System.out.println("Year.now(): " + Year.now());

        LocalDate yearAtDay3 = year.atDay(3);
        System.out.println("year.atDay(3) (LocalDate): " + yearAtDay3);
        YearMonth yearAtMonth4 = year.atMonth(4);
        System.out.println("year.atMonth(4): " + yearAtMonth4);

        LocalDate yearAtMonthDay = year.atMonthDay(MonthDay.of(12,31));
        System.out.println("year.atMonthDay(MonthDay.of(12,31)): " + yearAtMonthDay);
    }

    private static void print(LocalDate date, Year year) {
        System.out.println("date (LocalDate): " + date);
        System.out.println("year (Year): " + year);
    }

}
