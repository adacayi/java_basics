package com.sanver.basics.condition;

import static com.sanver.basics.condition.SwitchStatementSample.Animal.CAT;
import static com.sanver.basics.condition.SwitchStatementSample.Animal.TIGER;

public class SwitchStatementSample {
    public static void main(String[] args) {
        /* The switch statement accepts only the following data types:
            byte and Byte
            short and Short
            int and Integer
            char and Character
            enum
            String
            Use enhanced switch statement instead after Java 12.
         */
        printAnimalType(CAT);
        var tigerType = getAnimalType(TIGER);
        System.out.println(tigerType);
    }

    public static void printAnimalType(Animal animal) {
        String result;
        switch (animal) {
            case DOG:
            case CAT: // These two cases can be combined into one case as shown in the below getAnimalType() method.
                result = "domestic animal";
                break;
            case TIGER:
                result = "wild animal";
                break;
            default: // This does not need to be the last case, unlike the switch pattern matching scenario. Assume it is the first case, then it will be executed if animal does not match any other cases.
                result = "unknown animal";
                break;
        }

        System.out.println(result);
    }

    public static String getAnimalType(Animal animal) {
        switch (animal) {
            case DOG, CAT:
                return "domestic animal";
            case TIGER:
                return "wild animal";
            default:
                return "unknown animal";
        }
    }

    public enum Animal {
        DOG, CAT, TIGER
    }
}
