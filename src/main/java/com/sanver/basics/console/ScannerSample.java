package com.sanver.basics.console;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class ScannerSample {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String[] tags = {"Name", "Age", "Weight"};
            int width = 10;
            System.out.printf("%-" + width + "s: ", tags[0]);
            String name = scanner.nextLine();
            System.out.printf("%-" + width + "s: ", tags[1]);
            int age = scanner.nextInt();
            System.out.printf("%-" + width + "s: ", tags[2]);
            double weight = scanner.nextDouble();
            System.out.printf("\n\n%-" + width + "s: %s\n%-" + width + "s: %d\n%-" + width + "s: %.2f\n", tags[0], name, tags[1], age, tags[2], weight);
        }
        String inputString = "3 5\n7 9\nThis is a text\n3.14159265 2.71828";
        ByteArrayInputStream input = new ByteArrayInputStream(inputString.getBytes());
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
    }
}
