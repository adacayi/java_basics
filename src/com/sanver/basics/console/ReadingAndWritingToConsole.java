package com.sanver.basics.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadingAndWritingToConsole {

	public static void main(String[] args) {
		String name;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter your name: ");

		try {
			name = reader.readLine();
			System.out.println("Selamunaleykum " + name);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
