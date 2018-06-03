package com.sanver.basics.unittest;

import java.sql.SQLException;
import java.sql.Statement;

public class FizzBuzz {

	public static void main(String[] args) {
		FizzBuzz buzzer = new FizzBuzz();
		for (int i = 0; i < 20; i++)
			System.out.printf("%d -> %s\n", i, buzzer.fizzBuzz(i));
	}

	public String fizzBuzz(int number) {

		if (number < 0)
			throw new IllegalArgumentException("Number must be non-negative");

		if (number % 3 == 0 && number % 5 == 0)
			return "fizz buzz";
		if (number % 3 == 0)
			return "fizz";

		if (number % 5 == 0)
			return "buzz";

		return "";
	}

	public String fizzBuzzWithLogger(int number, Statement stm) throws SQLException {

		stm.executeUpdate("INSERT INTO dbo.logTable (Value) VALUES(" + number + ")");

		if (number < 0)
			throw new IllegalArgumentException("Number must be non-negative");

		if (number % 3 == 0 && number % 5 == 0)
			return "fizz buzz";
		if (number % 3 == 0)
			return "fizz";

		if (number % 5 == 0)
			return "buzz";

		return "";
	}
}
