package com.sanver.basics.time;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

/**
 * The {@code PeriodSample} class demonstrates the usage of the {@link java.time.Period} class
 * from the Java Time API (introduced in Java 8).
 *
 * <p>{@link Period} class implements the {@link TemporalAmount} interface and represents a date-based amount of time in terms of years, months, and days.
 * It is commonly used for calculating the difference between two dates or for manipulating dates.
 *
 * <h2>Common Features and Methods of {@code Period}:</h2>
 * <ul>
 *   <li>{@link Period#of(int, int, int)} - Creates a period of specified years, months, and days.</li>
 *   <li>{@link Period#between(LocalDate, LocalDate)} - Calculates the period between two dates.</li>
 *   <li>{@link Period#getYears()}, {@link Period#getMonths()}, {@link Period#getDays()} - Retrieves individual components of the period.</li>
 *   <li>{@link Period#plus(TemporalAmount)}, {@link Period#minus(TemporalAmount)} - Adds or subtracts periods.</li>
 *   <li>{@link Period#isZero()} and {@link Period#isNegative()} - Checks if the period is zero or negative.</li>
 *   <li>{@link LocalDate#plus(TemporalAmount)}, {@link LocalDate#minus(TemporalAmount)} - Manipulates dates using periods.</li>
 * </ul>
 *
 * <p>This class provides examples for creating periods, calculating differences between dates, and adding/subtracting periods from dates.
 */
public class PeriodSample {

    public static void main(String[] args) {
        // Example 1: Creating Periods
        var period1 = Period.of(2, 3, 15);  // 2 years, 3 months, 15 days
        System.out.println("period1: " + period1);

        // Example 2: Calculating Period Between Two Dates
        var startDate = LocalDate.of(2020, 2, 29);
        var endDate = LocalDate.of(2023, 2, 28);
        var periodBetween = Period.between(startDate, endDate);
        System.out.println("startDate: " + startDate);
        System.out.println("endDate  : " + endDate);
        System.out.println("periodBetween = Period.between(startDate, endDate): " + Period.between(startDate, endDate));
        System.out.println("periodBetween.getYears(): " + periodBetween.getYears());
        System.out.println("periodBetween.getMonths(): " + periodBetween.getMonths());
        System.out.println("ChronoUnit.MONTHS.between(startDate, endDate): " + ChronoUnit.MONTHS.between(startDate, endDate));
        System.out.println("periodBetween.getDays(): " + periodBetween.getDays());
        System.out.println("ChronoUnit.DAYS.between(startDate, endDate): " + ChronoUnit.DAYS.between(startDate, endDate));
        System.out.println("periodBetween.getUnits(): " + periodBetween.getUnits());

        // Example 3: Adding a Period to a Date
        System.out.println("startDate.plus(period1): " +  startDate.plus(period1));

        // Example 4: Subtracting a Period from a Date
        System.out.println("endDate.minus(period1): " + endDate.minus(period1));

        // Example 5: Checking Period properties
        System.out.println("period1.isZero(): " + period1.isZero());
        System.out.println("period1.isNegative(): " + period1.isNegative());

        // Example 6: Adding and Subtracting Periods
        Period period2 = Period.of(1, 2, 10);  // 1 year, 2 months, 10 days
        System.out.println("period1: " + period1);
        System.out.println("period2: " + period2);
        System.out.println("period1.plus(period2): " + period1.plus(period2)); // Note that Period is immutable. period1 is not changed after invoking this method.
        System.out.println("period1.minus(period2): " + period1.minus(period2));
        System.out.println("period1.minusYears(5): " + period1.minusYears(5));
        System.out.println("period1.plusMonths(20): " + period1.plusMonths(20));
        System.out.println("period1.minusDays(16): " + period1.minusDays(16));

        // Example 7: Changing a part of the period
        System.out.println("period1.withYears(19): " + period1.withYears(19));
        System.out.println("period1.withMonths(7): " + period1.withMonths(7));
        System.out.println("period1.withDays(31): " + period1.withDays(31));
    }
}

