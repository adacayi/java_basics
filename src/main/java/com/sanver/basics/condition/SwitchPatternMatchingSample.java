package com.sanver.basics.condition;

import java.io.File;
import java.time.Month;
import java.util.List;

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
 *   <li>Deconstruction of records in `case` labels</li>
 * </ul>
 *
 * <p>Example Scenarios:
 * <ul>
 *   <li>Processing objects of different types differently.</li>
 *   <li>Extracting fields from records during the match.</li>
 * </ul>
 *
 * <p>This example demonstrates pattern matching with a `switch` statement
 * on a variety of object types, including String, Integer, and a custom record type.
 */
public class SwitchPatternMatchingSample {

    public static void main(String[] args) {
        var objects = List.of((byte) 2, (byte) 123, (short) 128, 20_000, 'f', Month.SEPTEMBER, "Abdullah", new Person("Abdullah", 40), new File("Non existent"));
        objects.forEach(x -> describe(x));
        System.out.println();
        objects.forEach(x -> System.out.println(getDescription(x)));
    }

    /**
     * <p>
     * Demonstrates pattern matching in enhanced switch statement.
     * </p>
     * Note that, for pattern matching, even for switch statements, all cases needs to be covered.
     * @param obj Object to be described
     */
    public static void describe(Object obj) {
        switch (obj) {

            case Byte b when b < 10 ->
                    System.out.println("Byte smaller than 10 " + b); // Note we cannot use byte as of Java 21. Primitive types in patterns, instanceof and switch are not supported at language level '21'
            case Byte b ->
                    System.out.println("Byte " + b); // Note that this won't be executed if obj is a Byte < 10, satisfying the above case. Also, if we move this case above the first case we will get an error: Label is dominated by a preceding case label 'Byte b'
            case Short s -> System.out.println("Short " + s);
            case Integer i -> System.out.println("Integer " + i);
            case Character c -> System.out.println("Character " + c);
            case Enum<?> e -> System.out.println("Enum " + e); // Note that, "case Enum e ->" compiles as well.
            case String s -> System.out.println("String " + s);
            case Person(var name, var age) -> System.out.printf("Person %s at age %d%n", name, age); // We can also use Person(String name, int age). Deconstruction pattern can only be applied to a record.
            case Object o ->
                    System.out.println("Object " + o);  // If we don't make this the last case we will also get errors for each case below it suggesting Label is dominated by a preceding case label 'Object o'
//            default -> System.out.println("Object " + obj); // If we don't make this the last case we will also get errors for each case below it suggesting Label is dominated by a preceding case label 'default'.
//            Also, we can either have the default case or the case Object, not both.
        }
    }

    /**
     * Demonstrates pattern matching in switch expression
     *
     * @param obj Object to be described
     * @return Description for the object
     */
    private static String getDescription(Object obj) {
        return switch (obj) {

            case Byte b when b < 10 ->
                    "Byte smaller than 10 " + b; // Note we cannot use byte as of Java 21. Primitive types in patterns, instanceof and switch are not supported at language level '21'
            case Byte b ->
                    "Byte " + b; // Note that this won't be executed if obj is a Byte < 10, satisfying the above case. Also, if we move this case above the first case we will get an error: Label is dominated by a preceding case label 'Byte b'
            case Short s -> "Short " + s;
            case Integer i -> "Integer " + i;
            case Character c -> "Character " + c;
            case Enum<?> e -> "Enum " + e; // Note that, "case Enum e ->" compiles as well.
            case String s -> "String " + s;
            case Person(var name, var age) -> "Person %s at age %d%n".formatted(name, age); // We can also use Person(String name, int age)
            case Object o ->
                    "Object " + o;  // If we don't make this the last case we will also get errors for each case below it suggesting Label is dominated by a preceding case label 'Object o'
//            default -> "Object " + obj; // If we don't make this the last case we will also get errors for each case below it suggesting Label is dominated by a preceding case label 'default'.
//            Also, we can either have the default case or the case Object, not both.
        };
    }


    record Person(String name, int age) {
    }
}

