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

        int rowCount = stm.executeUpdate(String.format("INSERT INTO dbo.logTable (Value) VALUES(%d)", number));

        if (rowCount > 1) // This control is unnecessary and is only added to check mocked statement
            // object return value.
            throw new RuntimeException("Insert operation failed");

        return fizzBuzz(number);
    }

    public void fizzBuzzConsole(int number) {
        if (number < 0)
            throw new IllegalArgumentException("Number must be non-negative");

        if (number % 3 == 0)
            System.out.print("fizz");

        if (number % 5 == 0)
            System.out.print("buzz");

        System.out.println();
    }
}
