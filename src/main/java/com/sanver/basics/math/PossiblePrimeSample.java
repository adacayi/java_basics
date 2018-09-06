package com.sanver.basics.math;

import java.math.BigInteger;
import java.util.Scanner;

public class PossiblePrimeSample {
    public static void main(String... args) {
        System.out.print("Enter a number to check if it is possibly prime: ");
        try (Scanner scanner = new Scanner(System.in)) {
            BigInteger number = new BigInteger(scanner.next());
            System.out.printf("%s\n", number.isProbablePrime(1) ? "prime" : "not prime");
        }
    }
}
