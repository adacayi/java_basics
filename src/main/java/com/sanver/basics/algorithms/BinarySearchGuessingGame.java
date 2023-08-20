package com.sanver.basics.algorithms;

import java.util.Scanner;

public class BinarySearchGuessingGame {
    public static void main(String[] args) {
        System.out.print("Enter lower bound..: ");
        var finder = new BinarySearchGuessingGame();
        try (Scanner scanner = new Scanner(System.in)) {
            var lowerBound = scanner.nextInt();
            System.out.print("Enter upper bound: ");
            var upperBound = scanner.nextInt();
            System.out.printf("Lower bound: %d, Upper bound %d\n", lowerBound, upperBound);
            finder.find(lowerBound, upperBound, scanner);
        }
    }

    public boolean find(int lowerBound, int upperBound, Scanner scanner) {
        if (lowerBound > upperBound) {
            System.out.println("Number not found");
            return false;
        }

        var guess = (lowerBound + upperBound) / 2;
        var result = getResult(scanner, guess);
        switch (result) {
            case 0:
                System.out.println("Number found");
                return true;
            case -1:
                return find(lowerBound, guess - 1, scanner);
            case 1:
                return find(guess + 1, upperBound, scanner);
            default:
                System.out.println("An error occurred");
                return false;
        }

    }

    private byte getResult(Scanner scanner, int guess) {
        do {
            System.out.printf("Is the number %d: ((Y)es, (S)maller, (L)arger)", guess);
            var result = scanner.next();
            switch (result.toUpperCase()) {
                case "Y":
                    return 0;
                case "S":
                    return -1;
                case "L":
                    return 1;
                default:
                    System.out.println("Invalid input. Please type a valid input.");
            }
        } while (true);
    }
}
