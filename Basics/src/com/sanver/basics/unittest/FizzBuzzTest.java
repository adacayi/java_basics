package com.sanver.basics.unittest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

// Right click this class in Package Explorer, select Run As-> JUnit Test
class FizzBuzzTest {

	static FizzBuzz buzzer;

	@BeforeAll
	static void setUp() {
		buzzer = new FizzBuzz();
	}

	@AfterAll
	static void tearDown() {
		buzzer = null;
	}

	@Test
	void testFizzBuzzNonDivisibleByAny() {
		Assertions.assertTrue(buzzer.fizzBuzz(2) == "",
				() -> "Numbers non divisible by 3 and 5 should return empty string.");
	}

	@Test
	void testFizzBuzzDivisibleBy3() {
		Assertions.assertEquals("fizz", buzzer.fizzBuzz(6),
				"Numbers divisible by 3 must return fizz if they are not also divisible by 5");
	}

	@Test
	void testFizzBuzzOtherCases() {
		Assertions.assertAll("Invalid result", () -> Assertions.assertNotEquals(null, buzzer.fizzBuzz(1)),
				() -> Assertions.assertEquals("fizz", buzzer.fizzBuzz(3)),
				() -> Assertions.assertEquals("buzz", buzzer.fizzBuzz(5)),
				() -> Assertions.assertEquals("fizz buzz", buzzer.fizzBuzz(15)));
		Assertions.assertTrue(buzzer.fizzBuzz(2) == "",
				() -> "Numbers non divisible by 3 and 5 should return empty string.");
	}

	@Test
	void testFizzBuzzNonNegativeException() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> buzzer.fizzBuzz(-1),
				"Negative numbers should throw an exception");
	}
}
