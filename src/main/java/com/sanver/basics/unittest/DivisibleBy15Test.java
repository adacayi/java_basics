package com.sanver.basics.unittest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DivisibleBy15Test {

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
	void testFizzBuzzDivisibleBy15() {
		Assertions.assertEquals("fizz buzz", buzzer.fizzBuzz(15),
				"Numbers divisible by 15 must return fizz buzz");
	}
}
