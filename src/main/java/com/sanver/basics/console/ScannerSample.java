package com.sanver.basics.console;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ScannerSample {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String[] tags = {"Name", "Age", "Weight"};
            int width = 10;
            var format = "%-" + width + "s: ";
            System.out.printf(format, tags[0]);
            String name = scanner.nextLine();
            System.out.printf(format, tags[1]);
            int age = scanner.nextInt();
            System.out.printf(format, tags[2]);
            double weight = scanner.nextDouble();
            var format2 = "%n%-" + width + "s: %s%n%-" + width + "s: %d%n%-" + width + "s: %f%n";
            System.out.printf(format2, tags[0], name, tags[1], age, tags[2], weight);
        }
        String inputString = """
        3 5
        7 9
        This is a text
        3.14159265 2.71828
        """;
        var input = new ByteArrayInputStream(inputString.getBytes());
        try (Scanner scanner = new Scanner(input)) {
            System.out.println(scanner.nextInt());
            System.out.println(scanner.nextInt());
            System.out.println(scanner.nextInt());
            System.out.println(scanner.nextInt());
            System.out.println("Be careful here. Since nextInt does not get the newline character, the first nextLine " +
                    "method will not give the string. The second one will.");
            System.out.println(scanner.nextLine());
            System.out.println(scanner.nextLine());
            System.out.println(scanner.nextDouble());
            System.out.println(scanner.nextDouble());
        }

        //region to show hasNext usage
        try (var scanner = new Scanner(new ByteArrayInputStream("2 3 4 5 7".getBytes()))) {
            System.out.printf("%nShowing scanner.hasNext() usage%n");
            while (scanner.hasNextInt()) {
                System.out.printf("%d ", scanner.nextInt());
            }
            System.out.println();
        }
    }
}
