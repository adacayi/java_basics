package com.sanver.basics.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class provides ways to parse a LocalDateTime and convert LocalDateTime to string with a defined format.
 *
 * <table border="1">
 *     <caption>DateTimeFormatter Symbols</caption>
 *     <tr>
 *         <th>Symbol</th>
 *         <th>Meaning</th>
 *         <th>Presentation</th>
 *         <th>Examples</th>
 *     </tr>
 *     <tr><td>G</td><td>Era</td><td>Text</td><td>AD; Anno Domini; A</td></tr>
 *     <tr><td>u</td><td>Year</td><td>Year</td><td>2004; 04</td></tr>
 *     <tr><td>y</td><td>Year-of-era</td><td>Year</td><td>2004; 04</td></tr>
 *     <tr><td>D</td><td>Day-of-year</td><td>Number</td><td>189</td></tr>
 *     <tr><td>M/L</td><td>Month-of-year</td><td>Number/Text</td><td>7; 07; Jul; July; J</td></tr>
 *     <tr><td>d</td><td>Day-of-month</td><td>Number</td><td>10</td></tr>
 *     <tr><td>Q/q</td><td>Quarter-of-year</td><td>Number/Text</td><td>3; 03; Q3; 3rd quarter</td></tr>
 *     <tr><td>Y</td><td>Week-based-year</td><td>Year</td><td>1996; 96</td></tr>
 *     <tr><td>w</td><td>Week-of-week-based-year</td><td>Number</td><td>27</td></tr>
 *     <tr><td>W</td><td>Week-of-month</td><td>Number</td><td>4</td></tr>
 *     <tr><td>E</td><td>Day-of-week</td><td>Text</td><td>Tue; Tuesday; T</td></tr>
 *     <tr><td>e/c</td><td>Localized day-of-week</td><td>Number/Text</td><td>2; 02; Tue; Tuesday; T</td></tr>
 *     <tr><td>F</td><td>Week-of-month</td><td>Number</td><td>3</td></tr>
 *     <tr><td>a</td><td>AM-PM of day</td><td>Text</td><td>PM</td></tr>
 *     <tr><td>h</td><td>Clock-hour-of-AM-PM (1-12)</td><td>Number</td><td>12</td></tr>
 *     <tr><td>K</td><td>Hour-of-AM-PM (0-11)</td><td>Number</td><td>0</td></tr>
 *     <tr><td>k</td><td>Clock-hour-of-AM-PM (1-24)</td><td>Number</td><td>0</td></tr>
 *     <tr><td>H</td><td>Hour-of-day (0-23)</td><td>Number</td><td>0</td></tr>
 *     <tr><td>m</td><td>Minute-of-hour</td><td>Number</td><td>30</td></tr>
 *     <tr><td>s</td><td>Second-of-minute</td><td>Number</td><td>55</td></tr>
 *     <tr><td>S</td><td>Fraction-of-second</td><td>Fraction</td><td>978</td></tr>
 *     <tr><td>A</td><td>Milli-of-day</td><td>Number</td><td>1234</td></tr>
 *     <tr><td>n</td><td>Nano-of-second</td><td>Number</td><td>987654321</td></tr>
 *     <tr><td>N</td><td>Nano-of-day</td><td>Number</td><td>1234000000</td></tr>
 *     <tr><td>V</td><td>Time-zone ID</td><td>Zone-ID</td><td>America/Los_Angeles; Z; -08:30</td></tr>
 *     <tr><td>z</td><td>Time-zone name</td><td>Zone-name</td><td>Pacific Standard Time; PST</td></tr>
 *     <tr><td>O</td><td>Localized zone-offset</td><td>Offset-O</td><td>GMT+8; GMT+08:00; UTC-08:00</td></tr>
 *     <tr><td>X</td><td>Zone-offset 'Z' for zero</td><td>Offset-X</td><td>Z; -08; -0830; -08:30; -083015; -08:30:15</td></tr>
 *     <tr><td>x</td><td>Zone-offset</td><td>Offset-x</td><td>+0000; -08; -0830; -08:30; -083015; -08:30:15</td></tr>
 *     <tr><td>Z</td><td>Zone-offset</td><td>Offset-Z</td><td>+0000; -0800; -08:00</td></tr>
 *     <tr><td>p</td><td>Pad next</td><td>Pad modifier</td><td>1</td></tr>
 *     <tr><td>'</td><td>Escape for text</td><td>Delimiter</td><td></td></tr>
 *     <tr><td>''</td><td>Single quote</td><td>Literal</td><td>'</td></tr>
 *     <tr><td>[ ]</td><td>Optional section</td><td>Delimiter</td><td></td></tr>
 *     <tr><td># { }</td><td>Reserved for future use</td><td></td><td></td></tr>
 * </table>
 *
 * For more details, visit:
 * <a href="https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html">
 * Java DateTimeFormatter Documentation
 * </a>
 */
public class ParsingToLocalDateTime {
    public static void main(String... args) {
        var format = "%-57s : %s%n";
        var dateTimeString = "14-07-2018 16:24:03.032";
        System.out.printf(format, "dateTimeString", dateTimeString);
        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS"); // YYYY does not work as intended, because Y is for week-based-year and may differ from actual year in the first and last weeks of a year.
        System.out.printf(format, "formatter", "DateTimeFormatter.ofPattern(\"dd-MM-yyyy HH:mm:ss.SSS\")");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        System.out.printf(format, "dateTime = LocalDateTime.parse(dateTimeString, formatter)", dateTime);
        System.out.printf(format, "dateTime.format(formatter)", dateTime.format(formatter));
        System.out.printf(format, "dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)", dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.printf(format, "DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime)", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(dateTime));
    }
}
