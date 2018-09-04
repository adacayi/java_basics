package com.sanver.basics.math;

import java.math.BigInteger;
import java.util.Scanner;

public class BigIntegerSample {
    public static void main(String... args) {
        BigInteger a, b;
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter first integer: ");
            a = new BigInteger(scanner.nextLine());
            System.out.print("Enter second integer: ");
            b = new BigInteger(scanner.nextLine());
            System.out.printf("First: %s\nSecond: %s\nAddition: %s\nMultiplication: %s\n", a, b, a.add(b), a.multiply(b));
        }
    }
}
