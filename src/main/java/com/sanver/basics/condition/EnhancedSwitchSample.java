package com.sanver.basics.condition;

import static com.sanver.basics.condition.EnhancedSwitchSample.Animal.*;

/**
 * <a href = "https://www.baeldung.com/java-switch">Source</a>
 */
public class EnhancedSwitchSample {
    private static final String DOMESTIC_ANIMAL = "domestic animal";
    private static final String WILD_ANIMAL = "wild animal";
    private static final String UNKNOWN_ANIMAL = "unknown animal";

    public static void main(String[] args) {
        printAnimalTypeWithSwitchStatement(DOG);
        printAnimalType(TIGER);
        printAnimalType2(MOUSE);
        var tigerType = getAnimalType(TIGER);
        System.out.println(tigerType);
    }

    /**
     * Demonstrates switch statement
     * <h3>Exhaustiveness</h3>
     * When using switch statements, it doesn’t really matter if all cases are covered.
     */
    private static void printAnimalTypeWithSwitchStatement(Animal animal) {
        switch(animal) {
            case DOG -> System.out.println(DOMESTIC_ANIMAL);
            case TIGER -> System.out.println(WILD_ANIMAL);
        }
    }

    /**
     * Demonstrates switch expression
     */
    private static void printAnimalType(Animal animal) {
        var result = switch (animal) {
            case DOG, CAT -> DOMESTIC_ANIMAL;
            case TIGER -> WILD_ANIMAL;
            default -> UNKNOWN_ANIMAL;
        };

        System.out.println(result);
    }

    /**
     * Demonstrates yield usage in switch expression
     */
    private static void printAnimalType2(Animal animal) {
        // This is to show multiple statement case in switch where to return the switch result,
        // "yield" keyword needs to be used (return would try to return a value for the method (printAnimalType2)
        // not the switch and would result in a compile time error since the method return type is void).
        var result = switch (animal) {
            case DOG, CAT -> DOMESTIC_ANIMAL;
            case TIGER -> WILD_ANIMAL;
            default -> {
                var length = animal.name().length();
                yield "unknown animal with name length " + length;
            }
        };

        System.out.println(result);
    }

    /**
     * Demonstrates return usage in switch expression
     */
    private static String getAnimalType(Animal animal) {
        switch (animal) {
            case DOG, CAT -> { // To use return in the enhanced switch statement, you must use curly braces.
                return DOMESTIC_ANIMAL;
            }
            case TIGER -> {
                return WILD_ANIMAL;
            }
            default -> {
                return UNKNOWN_ANIMAL;
            }
        }
    }

    enum Animal {
        DOG, CAT, TIGER, MOUSE
    }
}
