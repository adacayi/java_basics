package com.sanver.basics.enums;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EnumSample {
    interface Capitalize {
        String capitalize();
    }

    /**
     * {@link Enum} implements {@link Comparable} which compares based on the ordinal value.
     * {@link Enum#compareTo}
     */
    enum Days implements Capitalize { // This is automatically static(i.e. no need to write `static enum Days implements Capitalize{..}`) and cannot access any instance members of its outer class (e.g. EnumSample.this.someMember won't compile)
        // Note: all enum types implicitly extend the java.lang.Enum. enums cannot extend another class. e.g. enum Days extends Object is not allowed. Implements is allowed though.
        // The first thing in an enum must be its list of constants
        // We can have inner classes, enums, records and interfaces inside an enum.
        SUNDAY(7) { // This calls the private constructor of the enum.

            void print() { // This overrides the print method
                System.out.println("The day is Sunday");
            }
        }, // This calls the constructor with parameter 7 to generate the SUNDAY instance of the Days enum.
        MONDAY(1) {
            void print() {
                System.out.println("The day is Monday");
            }
        },
        TUESDAY(2) {
            void print() {
                System.out.println("The day is Tuesday");
            }
        },
        WEDNESDAY(3) {
            void print() {
                System.out.println("The day is Wednesday");
            }
        },
        THURSDAY(4) {
            void print() {
                System.out.println("The day is Thursday");
            }
        },
        FRIDAY(5) {
            void print() {
                System.out.println("The day is Friday");
            }
        },
        SATURDAY(6) {
            void print() {
                System.out.println("The day is Saturday");
            }
        };

        static { // Unlike interfaces, static blocks are allowed in enums.
            System.out.println("Days static block executed");
        }

        { // Unlike interfaces and records, non-static blocks are allowed in enums.
            System.out.println("Days non-static block executed");
        }

        private final int index; // You can define instance variables like normal classes
        public String capitalizedName;
        public static String prefix = "some prefix";

        Days(int index) {  // This is a private constructor and its access modifier cannot be changed (e.g. it cannot be made public)
            this.index = index;
            capitalizedName = capitalize(); // capitalize() + prefix would result in a compile error, since Enum constants are implicitly static and final and are created first, calling this constructor, and at that point, the prefix static variable is not initialized yet.
            // However, if we made prefix final, this would work, since the prefix variable is declared as static final, making it a compile-time constant.
            // In Java, static final constants are inlined at compile time, meaning "some prefix " is directly substituted in the constructor before any instance is created.
            // Since prefix is now a constant expression, there is no illegal forward reference issue.

        } // This is a private constructor. Enums can only have private constructors.

        abstract void print(); // This is to show that enums can have abstract methods, which can be implemented in the enum constants as seen above.

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

        private class SomeClass{ } // We can define inner classes, static classes abstract classes and interfaces in an enum

        private static class SomeStaticClass{}

        public abstract class SomeAbstractClass{}

        protected interface SomeInterface{}
    }

    private static final Random RANDOM = new Random();

    public static void main(String... args) {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        Days[] days = Days.values();
        Days today = Arrays.stream(days).filter(x -> x.getIndex() == dayOfWeek.ordinal() + 1).findFirst().orElse(Days.MONDAY);

        System.out.printf("Today is %s%n%n", today);
        today.print();
        System.out.println("Days.valueOf(\"TUESDAY\"): " + Days.valueOf("TUESDAY"));
        System.out.println("Enum.valueOf(Days.class, \"TUESDAY\"): " + Enum.valueOf(Days.class, "TUESDAY"));

        for (Days day : days) {
            System.out.printf("%-9s ordinal: %d, index %d%n", day, day.ordinal(), day.getIndex());
        }

        System.out.printf("%n10 random days%n");
        var randomDays = IntStream.range(0, 10).mapToObj(x -> getRandomEnum(Days.class)).toList();
        System.out.println(randomDays);

        System.out.printf("%nDistinct days sorted by TreeSet%n");
        var distinctDays = new TreeSet<>(randomDays); // Since Enum implements Comparable and compares based on the ordinal value, it will sort the set accordingly.
        System.out.println(distinctDays);
        System.out.println(Days.TUESDAY.capitalizedName);
    }

    public static <T extends Enum<T>> T getRandomEnum(Class<T> type) {
        var values = type.getEnumConstants();
        return values[RANDOM.nextInt(0, values.length)];
    }
}
