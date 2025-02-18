package com.sanver.basics.enums;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum Days implements Capitalize { // Note: all enum types implicitly extend the java.lang.Enum. enums cannot extend another class. e.g. enum Days extends Object is not allowed. Implements is allowed though.
    // The first thing in an enum must be its list of constants
    SUNDAY(7){ // This calls the private constructor of the enum.
        void print() { // This overrides the print method
            System.out.println("The day is Sunday");
        }
    }, // This calls the constructor with parameter 7 to generate the SUNDAY instance of the Days enum.
    MONDAY(1){
        void print() {
            System.out.println("The day is Monday");
        }
    },
    TUESDAY(2){
        void print() {
            System.out.println("The day is Tuesday");
        }
    },
    WEDNESDAY(3){
        void print() {
            System.out.println("The day is Wednesday");
        }
    },
    THURSDAY(4){
        void print() {
            System.out.println("The day is Thursday");
        }
    },
    FRIDAY(5){
        void print() {
            System.out.println("The day is Friday");
        }
    },
    SATURDAY(6){
        void print() {
            System.out.println("The day is Saturday");
        }
    };

    abstract void print(); // This is to show that enums can have abstract methods, which can be implemented in the enum constants as seen above.

    private final int index; // You can define instance variables like normal classes

    Days(int index) {  // This is a private constructor and its access modifier cannot be changed (e.g. it cannot be made public)
        this.index = index;
    } // This is a private constructor. Enums can only have private constructors.

    public int getIndex() { // You can define methods like normal classes
        return index;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase(); // name is a final method in enum that returns the name as a String
    }

    @Override
    public String capitalize() {
        return IntStream.range(0, name().length()).mapToObj(i -> i == 0 ? String.valueOf(name().charAt(0)).toUpperCase() : String.valueOf(name().charAt(i)).toLowerCase()).collect(Collectors.joining(""));
    }
}

interface Capitalize {
    String capitalize();
}

public class EnumSample {
    private static final Random RANDOM = new Random();

    public static void main(String... args) {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        Days[] days = Days.values();
        Days today = Arrays.stream(days).filter(x -> x.getIndex() == dayOfWeek.ordinal() + 1).findFirst().orElse(Days.MONDAY);

        System.out.printf("Today is %s%n%n", today);
        today.print();

        for (Days day : days) {
            System.out.printf("%-9s ordinal: %d, index %d%n", day, day.ordinal(), day.getIndex());
        }

        System.out.println("10 random days");
        IntStream.range(0, 10).forEach(x -> System.out.println(getRandomEnum(Days.class)));
    }

    public static <T extends Enum<T>> T getRandomEnum(Class<T> type) {
        var values = type.getEnumConstants();
        return values[RANDOM.nextInt(0, values.length)];
    }
}
