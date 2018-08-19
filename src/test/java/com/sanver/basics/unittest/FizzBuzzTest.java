package com.sanver.basics.unittest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

// Right click this class in Package Explorer, select Run As-> JUnit Test
class FizzBuzzTest {

    static FizzBuzz buzzer;

    @BeforeAll
    static void initAll() {
        buzzer = new FizzBuzz();
    }

    @AfterAll
    static void tearDownAll() {
        buzzer = null;
    }

    @Test
    void should_ReturnEmpty_When_NonDivisibleByAny() {
        Assertions.assertTrue(buzzer.fizzBuzz(2) == "",
                "Numbers non divisible by 3 and 5 should return empty string.");
    }

    @Test
    void should_ReturnFizz_When_DivisibleByOnly3() {
        Assertions.assertEquals("fizz", buzzer.fizzBuzz(6),
                "Numbers divisible by 3 must return fizz if they are not also divisible by 5");
    }

    @Test
    void should_ReturnAdequate_When_MultipleEntries() {
        Assertions.assertAll("Invalid result", () -> Assertions.assertNotEquals(null, buzzer.fizzBuzz(1)),
                () -> Assertions.assertEquals("fizz", buzzer.fizzBuzz(3)),
                () -> Assertions.assertEquals("buzz", buzzer.fizzBuzz(5)),
                () -> Assertions.assertEquals("fizz buzz", buzzer.fizzBuzz(15)));
        Assertions.assertTrue(buzzer.fizzBuzz(2) == "",
                "Numbers non divisible by 3 and 5 should return empty string.");
    }

    @Test
    void should_ThrowException_When_Negative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> buzzer.fizzBuzz(-1),
                "Negative numbers should throw an exception");
    }
}
