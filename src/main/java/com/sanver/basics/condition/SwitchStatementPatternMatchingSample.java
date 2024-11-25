package com.sanver.basics.condition;

/**
 * Demonstrates pattern matching in switch statements in Java 21.
 *
 * <p>Java 21 enhances switch statements with pattern matching capabilities.
 * This allows you to match against specific types within a switch and
 * deconstruct objects directly, improving readability and reducing boilerplate code.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Type checks with patterns (e.g., `case Integer i`).</li>
 *   <li>Conditional patterns using `when` clauses.</li>
 *   <li>Deconstruction of objects in `case` labels for structured types.</li>
 * </ul>
 *
 * <p>Example Scenarios:
 * <ul>
 *   <li>Processing objects of different types differently.</li>
 *   <li>Extracting fields from structured objects during the match.</li>
 * </ul>
 *
 * <p>This example demonstrates pattern matching with a `switch` statement
 * on a variety of object types, including String, Integer, and a custom record type.
 */
public class SwitchStatementPatternMatchingSample {

    public static void main(String[] args) {
        Object[] inputs = {"Hello, world!", 42, -5, new Point(3, 5), null, 3.14};

        for (Object input : inputs) {
            describe(input);
        }
    }

    /**
     * Uses a switch statement with pattern matching to describe the given object.
     *
     * @param obj the object to describe
     */
    public static void describe(Object obj) {
        switch (obj) {
            case String s -> System.out.printf("A string \"%s\" of length %d%n", s, s.length());
            case Integer i when i > 0 -> System.out.println("A positive integer: " + i);
            case Integer i -> System.out.println("A non-positive integer: " + i);
            case Point(int x, int y) -> System.out.println("A point at (" + x + ", " + y + ")"); // Since both x and y fields of the record are accessed this usage is preferable to case Point p -> System.out.println("A point at (" + p.x() + ", " + p.y() + ")")
            case null -> System.out.println("It's null!");
            default -> System.out.printf("An unknown type. Object: %s Type: %s%n", obj, obj.getClass());
        }
    }

    /**
     * A record to demonstrate deconstruction in switch cases.
     */
    record Point(int x, int y) {
    }
}

