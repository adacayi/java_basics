package com.sanver.basics.unittest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DivisibleBy5Test {

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
	void testFizzBuzzDivisibleBy5() {
		Assertions.assertEquals("buzz", buzzer.fizzBuzz(10),
				"Numbers divisible by 5 must return buzz if they are not also divisible by 5");
	}
}
