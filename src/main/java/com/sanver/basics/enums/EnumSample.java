package com.sanver.basics.enums;

import java.time.LocalDate;

import static com.sanver.basics.utils.Utils.getRandomInt;

enum Days {

    SUNDAY(7), // This calls the constructor with parameter 7 to generate the SUNDAY instance of the Days enum.
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6);

    private final int index; // You can define instance variables like normal classes

    Days(int index) {  // This is a private constructor and its access modifier cannot be changed (e.g. it cannot be made public)
        this.index = index;
    }

    public int getIndex() { // You can define methods like normal classes
        return index;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase(); // name is a final method in enum that returns the name as a String
    }
}

public class EnumSample {
    public static void main(String... args) {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        Days[] days = Days.values();
        Days today = days[dayOfWeek];

        System.out.printf("Today is %s%n%n", today);

        for (Days day : days)
            System.out.printf("%s has index %d%n", day, day.getIndex());

        System.out.printf("%nThe ordinal of Sunday is %d%n", Days.SUNDAY.ordinal());
        System.out.println("A random day is " + getRandomEnum(Days.class));
    }

    public static <T extends Enum<T>> T getRandomEnum(Class<T> type) {
        var values = type.getEnumConstants();
        return values[getRandomInt(0, values.length - 1)];
    }
}
