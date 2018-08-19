package com.sanver.basics.unittest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

// This class is coded to only show TestSuite. The naming convention should be ClassNameToBeTestedTest. In our case it
// should have been FizzBuzzTest. For test class and test methods naming conventions check
// https://dzone.com/articles/7-popular-unit-test-naming.
// My preferred choice to name test methods is should_ExpectedBehavior_When_StateUnderTest
// i.e. should_ReturnBuzz_When_10
class DivisibleBy5Test {

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
    void should_ReturnBuzz_When_10() {
        Assertions.assertEquals("buzz", buzzer.fizzBuzz(10),
                "Numbers divisible by 5 must return buzz if they are not also divisible by 5");
    }
}
