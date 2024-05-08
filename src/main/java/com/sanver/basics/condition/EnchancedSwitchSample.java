package com.sanver.basics.condition;

import static com.sanver.basics.condition.EnchancedSwitchSample.Animal.DOG;
import static com.sanver.basics.condition.EnchancedSwitchSample.Animal.MOUSE;
import static com.sanver.basics.condition.EnchancedSwitchSample.Animal.TIGER;

public class EnchancedSwitchSample {
    public static void main(String[] args) {
        printAnimalType(DOG);
        printAnimalType2(MOUSE);
        var tigerType = getAnimalType(TIGER);
        System.out.println(tigerType);
    }

    public static void printAnimalType(Animal animal) {
        var result = switch (animal) {
            case DOG, CAT -> "domestic animal";
            case TIGER -> "wild animal";
            default -> "unknown animal";
        };

        System.out.println(result);
    }

    public static void printAnimalType2(Animal animal) {
        // This is to show multiple statement case in switch where to return the switch result,
        // "yield" keyword needs to be used (return would try to return a value for the method (printAnimalType2)
        // not the switch and would result in a compile time error since the method return type is void).
        var result = switch (animal) {
            case DOG, CAT -> "domestic animal";
            case TIGER -> "wild animal";
            default -> {
                var length = animal.name().length();
                yield "unknown animal with name length " + length;
            }
        };

        System.out.println(result);
    }

    public static String getAnimalType(Animal animal) {
        switch (animal) {
            case DOG, CAT -> { // To use return in the enhanced switch statement, you must use curly braces.
                return "domestic animal";
            }
            case TIGER -> {
                return "wild animal";
            }
            default -> {
                return "unknown animal";
            }
        }
    }

    enum Animal {
        DOG, CAT, TIGER, MOUSE
    }
}
