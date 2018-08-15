package com.sanver.basics.console;

import java.io.Console;

public class ConsoleSample {

	public static void main(String[] args) {
		Console console = System.console();// console will be null if you run it from Eclipse. Run it from command line
		String userName = console.readLine("User Name: ");
		char[] password = console.readPassword("Password: ");
		System.out.println("Selamunaleykum " + userName);
		System.out.println("Your password is " + new String(password));
	}
}
