package com.sanver.basics.unittest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

// This class is coded to only show TestSuite. The naming convention should be ClassNameToBeTestedTest. In our case it
// should have been FizzBuzzTest.
class DivisibleBy15Test {

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
    void should_Returnfizzbuzz_When_15() {
        Assertions.assertEquals("fizz buzz", buzzer.fizzBuzz(15),
                "Numbers divisible by 15 must return fizz buzz");
    }
}
