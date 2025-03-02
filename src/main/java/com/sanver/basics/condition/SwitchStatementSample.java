package com.sanver.basics.condition;

import static com.sanver.basics.condition.SwitchStatementSample.Animal.CAT;
import static com.sanver.basics.condition.SwitchStatementSample.Animal.DOG;
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
        System.out.println(getAnimalType2(DOG));
        System.out.println(getAnimalType4(DOG));
    }

    public static void printAnimalType(Animal animal) {
        String result;
        switch (animal) {
            case DOG:
                // tempResult = "Dog" // This will result in a compile error, since tempResult is not defined yet.
            case BIRD:
            case CAT:
                // These three cases can be combined into one case as shown in the below getAnimalType() method.
                // Other combining options are shown in getAnimalType2() and getAnimalType3() methods.
                String tempResult = "cat"; // tempResult is defined within the scope of the switch statement, thus can be accessed within the case CAT: block and any subsequent case blocks after it.
                // Note that this wouldn't compile if we had the below combinations.
                // It will compile if  String tempResult = "cat"; is not the first statement after CAT:,
                // but second or later.
                // CASE DOG:
                // case BIRD: // or just BIRD:
                // CAT:
                // String tempResult = "cat"; // This will result in a compile error.
                result = "domestic animal";
                break;
            case TIGER:
//                System.out.println(tempResult); // This results in a compile error since, tempResult is not initialized. Even if we didn't have the break on the above case and there is a fall through, this won't compile.
                tempResult = "wild animal"; // This does not cause a compile error since tempResult is in the scope for the rest of the switch statement
                result = tempResult;
                break;
            default: // This does not need to be the last case, unlike the switch pattern matching scenario. Assume it is the first case, then it will be executed if animal does not match any other cases.
                tempResult = "";  // This does not cause a compile error since tempResult is in the scope for the rest of the switch statement
                result = "unknown animal";
                break;
        }

        System.out.println(result);
    }

    public static String getAnimalType(Animal animal) {
        switch (animal) {
            case DOG, CAT, BIRD:
                return "domestic animal";
            case TIGER:
                return "wild animal";
            default:
                return "unknown animal";
        }
    }

    public static String getAnimalType2(Animal animal) {
        switch (animal) {
            case DOG:
                CAT:
                BIRD:
                return "domestic animal";
            case TIGER:
                return "wild animal";
            default:
                return "unknown animal";
        }
    }

    public static String getAnimalType3(Animal animal) {
        switch (animal) {
            case DOG:
            case CAT:
                BIRD:
                // Note that this way of combining cases does not allow defining a variable within the scope of the switch statement as the first line.
                // String tempResult = "cat"; // This would result in an error, as it is in the first line.
                //  This would work:
                // System.out.println();
                // String tempResult = "cat";
                return "domestic animal";
            case TIGER:
                return "wild animal";
            default:
                return "unknown animal";
        }
    }

    public static String getAnimalType4(Animal animal) {
        switch (animal) {
            case DOG:
//            CAT:
//            case BIRD: // Note that this way of combining cases does not work if we don't have any statement for the CAT: case.
                CAT:
                System.out.println("Dog or cat"); // If we have code here, this works and DOG and CAT will fall through to the next case.
            case BIRD:
                return "domestic animal";
            case TIGER:
                return "wild animal";
            default:
                return "unknown animal";
        }
    }

    public enum Animal {
        DOG, CAT, BIRD, TIGER
    }
}
