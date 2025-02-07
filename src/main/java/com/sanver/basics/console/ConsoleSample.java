package com.sanver.basics.console;

import java.io.Console;
import java.io.PrintWriter;

public class ConsoleSample {

    private static final String OUTPUT_FORMAT = "%-9s: %s%n";
    private static final String INPUT_FORMAT = "%-9s: ";
    private static final String NAME = "Name";
    private static final String PASSWORD = "Password";

    public static void main(String[] args) {
        Console console = System.console();// System.console() will be null if you run it from IntelliJ or Eclipse.
        // Run it from command line.
        // Go to Basics\target\classes in the terminal and type
        // java com.sanver.basics.console.ConsoleSample
        // Or you can specify the class path with -cp
        // java -cp target\classes com.sanver.basics.console.ConsoleSample (If you are in Basics folder in the terminal)
        // java -cp ..\..\..\..\..\..\..\target\classes com.sanver.basics.console.ConsoleSample (If you are in Basics\src\main\java\com\sanver\basics\console)
        String userName = console.readLine(INPUT_FORMAT, NAME);
        char[] password = console.readPassword(INPUT_FORMAT, PASSWORD);
        System.out.printf(OUTPUT_FORMAT, NAME, userName);
        System.out.printf(OUTPUT_FORMAT, PASSWORD, new String(password));
        consoleWriter(console);
    }

    private static void consoleWriter(Console console) {
        try (PrintWriter writer = console.writer()) {
            writer.printf("%n%-9s: ", NAME);
            writer.flush(); // This is needed, otherwise it might be buffered and not printed out immediately
            var name = console.readLine();
            writer.printf(INPUT_FORMAT, PASSWORD);
            writer.flush();
            var password = console.readPassword();
            writer.printf(OUTPUT_FORMAT, NAME, name);
            writer.printf(OUTPUT_FORMAT, PASSWORD, new String(password));
            writer.flush(); // Flush is needed here as well even though the writer will be closed.
        }
    }
}
