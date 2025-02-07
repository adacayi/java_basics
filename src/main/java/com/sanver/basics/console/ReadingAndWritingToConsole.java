package com.sanver.basics.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Use {@link java.util.Scanner} to read data from console for more flexibility instead of {@link java.io.InputStreamReader}.
 *
 * @see com.sanver.basics.console.ScannerSample
 */
public class ReadingAndWritingToConsole {

    public static void main(String[] args) {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Name: ");

            String name = reader.readLine();
            System.out.println("Name: " + name);
            System.out.print("Age : ");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            System.out.println("Age : " + age);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
