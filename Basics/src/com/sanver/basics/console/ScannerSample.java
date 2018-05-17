package com.sanver.basics.console;

import java.util.Scanner;

public class ScannerSample {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter your name: ");
			String name = scanner.nextLine();
			System.out.println("Your name is " + name);
			int age;
			System.out.print("Enter your age: ");
			age = scanner.nextInt();
			System.out.println("Your age is " + age);
		}
	}
}
