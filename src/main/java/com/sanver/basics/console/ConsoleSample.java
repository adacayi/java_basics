package com.sanver.basics.console;

import java.io.Console;

public class ConsoleSample {

	public static void main(String[] args) {
		Console console = System.console();// System.console() will be null if you run it from IntelliJ or Eclipse. Run it from command line. Go to Basics\target\classes in the terminal and type java com.sanver.basics.console.ConsoleSample
		String userName = console.readLine("User Name: ");
		char[] password = console.readPassword("Password: ");
		System.out.println("Selamunaleykum " + userName);
		System.out.println("Your password is " + new String(password));
	}
}
