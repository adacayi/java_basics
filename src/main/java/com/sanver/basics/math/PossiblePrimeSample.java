package com.sanver.basics.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Scanner;

public class PossiblePrimeSample {
    public static void main(String... args) {
        System.out.print("Enter a number to check if it is possibly prime: ");
        try (Scanner scanner = new Scanner(System.in)) {
            BigInteger number = new BigInteger(scanner.next());
            var certainty = 4;
            var possibility = BigDecimal.valueOf(1).subtract(new BigDecimal("0.5").pow(certainty)).multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            var format = NumberFormat.getInstance();
            System.out.printf("%s: %s%n", format.format(number), number.isProbablePrime(4) ? "More than %" + possibility + " probable that it is prime" : "definitely not prime"); // Certainty parameter: A measure of the uncertainty that the caller is willing to tolerate: if the call returns true the probability that this BigInteger is prime exceeds (1 - (1/2)^certainty). The execution time of this method is proportional to the value of this parameter.
        }
    }
}
