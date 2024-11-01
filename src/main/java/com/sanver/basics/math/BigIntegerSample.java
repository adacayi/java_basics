package com.sanver.basics.math;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BigIntegerSample {
    public static void main(String... args) {
        BigInteger first;
        BigInteger second;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter first  integer: ");
            first = new BigInteger(scanner.nextLine());
            System.out.print("Enter second integer: ");
            second = new BigInteger(scanner.nextLine());

            print(first, second, BigInteger::add, "+");
            print(first, second, BigInteger::multiply, "x");
        }
    }

    private static void print(BigInteger first, BigInteger second, BinaryOperator<BigInteger> operator, String operatorSign) {
        var result = operator.apply(first, second);
        var max = result.toString().length();
        var format = "%" + max + "s%n";
        System.out.println();
        System.out.printf(format, first);
        System.out.printf(format, second);
        System.out.println(operatorSign);
        System.out.printf("%s%n", IntStream.range(0, max).mapToObj(x -> "-").collect(Collectors.joining("")));
        System.out.println(result);
    }
}
