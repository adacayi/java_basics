package com.sanver.basics.strings;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormattedSample {
    public static void main(String[] args) {
        // String.formatted() method is introduced as part of Java 15, to aid with variable situations in text blocks """.
        var message = "Date is%6s: %s%nLocal time is: %s";
        String formatted = message.formatted("", LocalDate.now(), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SS")));
        System.out.println(formatted);
    }
}
