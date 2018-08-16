package com.sanver.basics.console;

import java.util.Scanner;

public class ScannerSample {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String[] tags = {"Name", "Age", "Weight"};
            int width=10;
            System.out.printf("%-"+width+"s: ", tags[0]);
            String name = scanner.nextLine();
            System.out.printf("%-"+width+"s: ", tags[1]);
            int age = scanner.nextInt();
            System.out.printf("%-"+width+"s: ", tags[2]);
            double weight = scanner.nextDouble();
            System.out.printf("\n\n%-"+width+"s: %s\n%-"+width+"s: %d\n%-"+width+"s: %.2f\n", tags[0], name, tags[1], age, tags[2], weight);
        }
    }
}
