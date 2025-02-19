package com.sanver.basics.objects;

/**
 * Demonstrates Pattern Matching for {@code instanceof}, a feature introduced in Java 16.
 * Pattern matching simplifies the use of the {@code instanceof} operator by allowing
 * conditional extraction of a variable in a single expression. It avoids the need for
 * redundant casting after type checking.
 *
 * <p>Features demonstrated:
 * <ul>
 *   <li><b>Simplified Type Casting</b>: Allows direct use of a variable of the checked type within the {@code instanceof} block.</li>
 *   <li><b>Pattern Matching with Multiple Conditions</b>: Combines pattern matching with additional conditions.</li>
 *   <li><b>Nesting with Pattern Matching</b>: Shows pattern matching used in nested contexts, such as in method calls and expressions.</li>
 * </ul>
 */
public class InstanceofPatternMatching {

    /**
     * Checks if the given object is a {@code String} and, if so, prints its length.
     * Uses pattern matching with {@code instanceof} to directly access the {@code String} variable.
     *
     * @param obj an object to check and process if it's a string
     */
    public static void printStringLength(Object obj) {
        if (obj instanceof String str) {  // Pattern matching: no need to cast obj to String
            System.out.println("The length of the string is: " + str.length());
        } else {
            System.out.println("The object is not a String.");
        }
    }

    /**
     * Checks if the given object is an {@code Integer} that is positive.
     * Uses pattern matching with {@code instanceof} and adds an additional condition.
     *
     * @param obj an object to check and process if it's a positive integer
     */
    public static void checkPositiveInteger(Object obj) {
        if (obj instanceof Integer i && i > 0) {  // Pattern matching with an additional condition
            System.out.println("The integer is positive: " + i);
        } else {
            System.out.println("The object is not a positive Integer.");
        }
    }

    /**
     * Demonstrates nested pattern matching. Checks if the object is a {@code String} and
     * if so, converts it to uppercase and returns it. If not a {@code String}, it returns {@code null}.
     *
     * @param obj an object to check and convert if it's a string
     * @return the uppercase string if {@code obj} is a {@code String}, otherwise {@code null}
     */
    public static String convertToUpperIfString(Object obj) {
        // Nested pattern matching within a conditional expression
        return (obj instanceof String str) ? str.toUpperCase() : null;
    }

    public static void printPerson(Object object) {
        if (object instanceof Person(var name, var age) && age > 18) {
            System.out.printf("An adult %s at age %d%n", name, age );
        } else{
            System.out.println("Not an adult person");
        }

    }
    /**
     * Main method to demonstrate pattern matching with {@code instanceof}.
     */
    public static void main(String[] args) {
        Object stringObject = "Hello, World!";
        Object integerObject = 42;
        Object negativeIntegerObject = -10;
        Object nonStringObject = 3.14;
        Object person = new Person("Ahmet", 21);

        // Example 1: Checking and printing length of a String
        printStringLength(stringObject);
        printStringLength(integerObject); // Not a String, print "The object is not a String."

        // Example 2: Checking for positive Integer
        checkPositiveInteger(integerObject);       // Positive Integer
        checkPositiveInteger(negativeIntegerObject); // Negative Integer

        // Example 3: Converting to uppercase if the object is a String
        System.out.println("Converted to uppercase: " + convertToUpperIfString(stringObject));
        System.out.println("Converted to uppercase: " + convertToUpperIfString(nonStringObject)); // Not a String, should return null

        // Example 4: Record pattern matching
        printPerson(person);
    }

    record Person(String name, int age) {

    }
}

