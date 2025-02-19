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
        var period1 = Period.of(2, 3, 15);
        var format = "%-50s : %s%n";
        System.out.printf(format, "Period.ofDays(5)", Period.ofDays(5));
        System.out.printf(format, "Period.ofWeeks(58)", Period.ofWeeks(58)); // This is the same as Period.ofDays(Math.multiplyExact(58, 7)), resulting in a period with days only.
        System.out.printf(format, "Period.ofMonths(3)", Period.ofMonths(3));
        System.out.printf(format, "Period.ofYears(10)", Period.ofYears(10));
        System.out.printf(format, "period1 = Period.of(2, 3, 15)", period1);

        // Example 2: Calculating Period Between Two Dates
        var startDate = LocalDate.of(2020, 2, 29);
        var endDate = LocalDate.of(2023, 2, 28);
        var periodBetween = Period.between(startDate, endDate);
        System.out.printf(format, "startDate = LocalDate.of(2020, 2, 29)", startDate);
        System.out.printf(format, "endDate = LocalDate.of(2023, 2, 28)", endDate);
        System.out.printf(format, "periodBetween = Period.between(startDate, endDate)", Period.between(startDate, endDate)); // Note that this results in P2Y11M30D, but startDate.plusYears(3) and startDate.plus(Period.ofYears(3)) also equals endDate in this case.
        System.out.printf(format, "startDate.plus(Period.between(startDate, endDate))", startDate.plus(Period.between(startDate, endDate)));
        System.out.printf(format, "startDate.plus(Period.ofYears(3))", startDate.plus(Period.ofYears(3)));
        System.out.printf(format, "startDate.plusYears(3)", startDate.plusYears(3));
        System.out.printf(format, "Period.between(startDate, endDate).equals(Period.ofYears(3))", Period.between(startDate, endDate).equals(Period.ofYears(3)));
        System.out.printf(format, "Period.ofMonths(12).equals(Period.ofYears(1))", Period.ofMonths(12).equals(Period.ofYears(1)));
        System.out.printf(format, "Period.ofDays(30).equals(Period.ofMonths(1))", Period.ofDays(30).equals(Period.ofMonths(1)));
        System.out.printf(format, "Period.ofDays(365).equals(Period.ofYears(1))", Period.ofDays(365).equals(Period.ofYears(1)));
        System.out.printf(format, "periodBetween.getYears()", periodBetween.getYears());
        System.out.printf(format, "periodBetween.getMonths()", periodBetween.getMonths());
        System.out.printf(format, "ChronoUnit.MONTHS.between(startDate, endDate)", ChronoUnit.MONTHS.between(startDate, endDate));
        System.out.printf(format, "periodBetween.getDays()", periodBetween.getDays());
        System.out.printf(format, "ChronoUnit.DAYS.between(startDate, endDate)", ChronoUnit.DAYS.between(startDate, endDate));
        System.out.printf(format, "periodBetween.getUnits()", periodBetween.getUnits());

        // Example 3: Adding a Period to a Date
        System.out.printf(format, "startDate", startDate);
        System.out.printf(format, "period1", period1);
        System.out.printf(format, "startDate.plus(period1)", startDate.plus(period1));

        // Example 4: Subtracting a Period from a Date
        System.out.printf(format, "endDate", endDate);
        System.out.printf(format, "period1", period1);
        System.out.printf(format, "endDate.minus(period1)", endDate.minus(period1));

        // Example 5: Checking Period properties
        System.out.printf(format, "period1.isZero()", period1.isZero());
        System.out.printf(format, "period1.isNegative()", period1.isNegative());

        // Example 6: Adding and Subtracting Periods
        Period period2 = Period.of(1, 4, 25);  // 1 year, 2 months, 10 days
        System.out.printf(format, "period1 = Period.of(2, 3, 15)", period1);
        System.out.printf(format, "period2 = Period.of(1, 4, 25)", period2);
        System.out.printf(format, "period1.plus(period2)", period1.plus(period2)); // Note that Period is immutable. period1 is not changed after invoking this method.
        System.out.printf(format, "period1.minus(period2)", period1.minus(period2));
        System.out.printf(format, "period1.minusYears(5)", period1.minusYears(5));
        System.out.printf(format, "period1.plusMonths(20)", period1.plusMonths(20));
        System.out.printf(format, "period1.minusDays(16)", period1.minusDays(16));

        // Example 7: Changing a part of the period
        System.out.printf(format, "period1.withYears(19)", period1.withYears(19));
        System.out.printf(format, "period1.withMonths(-3)", period1.withMonths(-3));
        System.out.printf(format, "period1.withDays(32)", period1.withDays(32));
    }
}

