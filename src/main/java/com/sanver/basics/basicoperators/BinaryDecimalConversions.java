package com.sanver.basics.basicoperators;

import static com.sanver.basics.basicoperators.BinaryHelpers.writeBinaryOf;

public class BinaryDecimalConversions {
    public static void main(String... args) {
        // Documentation for how negative numbers are represented in binary form: https://www.geeksforgeeks.org/representation-of-negative-binary-numbers/
        // In short, we don't just use the first bit to denote sign, because for 0 we have 2 representations and we for example for 8 bits, we can only represent down to -127, and not -128 because of that.
        // For having a single representation of 0 and be able to represent -128 with 8 bits, for any negative number -x, we represent its binary form as follows:
        // We take the complement (change 0s to 1s and 1s to 0s) of the positive x and then add 1 (This operation is called 2's complement method).
        // With this method as well all non-negative numbers start with 0 and all negative numbers start with 1 (since to get to the negative number we need to represent the positive number in binary format as usual (and don't use the first bit so that we can represent negative numbers as well) and then take the complement).
        // So for -1, we have 1 in 8 bits as 00000001, we take its complement 11111110 and then add one and get 11111111 as the binary representation of -1.
        // For -128 in 8 bits though let's represent -127 first and then subtract 1 since we cannot represent 128 with 8 bits (if we want negatives as well).
        // 127 is 01111111. The complement is 10000000. If we add 1 then we get 10000001 as -127. Now -128 is -1 of that. So it is 10000000 in 8 bits.
        int number = 2;
        System.out.printf("To write the negative of %d write ~%d + 1 = %d%n", number, number, ~number + 1);
        number = -2;
        System.out.printf("To write the negative of %d write ~(%d) + 1 = %d%n", number, number, ~number + 1);
        System.out.println();
        var format64Bit = "%-,5d: %s (64 bit representation)";
        var format32Bit = "%-,5d: %s (32 bit representation)";
        var format8Bit = "%-,5d: %s (8 bit representation)";
        writeBinaryOf("%,26d: %s (64 bit representation)", Long.MAX_VALUE);
        writeBinaryOf("%,26d: %s (64 bit representation)", Long.MIN_VALUE);
        writeBinaryOf(format64Bit, 0, 64);
        writeBinaryOf(format32Bit, 0);
        writeBinaryOf(format8Bit, 0, 8);
        writeBinaryOf(format8Bit, 1, 8);
        writeBinaryOf(format8Bit, -1, 8);
        writeBinaryOf(format32Bit, -1);
        writeBinaryOf(format64Bit, -1, 64);
        writeBinaryOf(format8Bit, 127, 8);
        writeBinaryOf(format8Bit, -127, 8);
        writeBinaryOf(format32Bit, -127);
        writeBinaryOf(format32Bit, 128);
        writeBinaryOf(format32Bit, -128);
        writeBinaryOf(format8Bit, -128, 8);
        writeBinaryOf(1);
        writeBinaryOf(-2);
        writeBinaryOf(4);
        writeBinaryOf(-3);
        writeBinaryOf(9);
        writeBinaryOf(-9);
        System.out.println();
        System.out.printf("%-,5d: %s%n", -12, Integer.toBinaryString(-12));// This is to show Integer.toBinaryString
        // method
        System.out.println();
        writeBinaryOf("%-,16d: %s", -1);
        writeBinaryOf("-1  >> 31  : %2d : %s", -1 >> 31);
        writeBinaryOf("-1  >>> 31 : %2d : %s", -1 >>> 31);
        writeBinaryOf("%-,16d: %s", -2);
        writeBinaryOf("-2 >> 31   : %2d : %s", -2 >> 31);
        System.out.println();
        number = 48;
        writeBinaryOf("%-,12d: %s", number);
        writeBinaryOf(String.format("%-,3d", number) + " >>  3: %d: %s", number >> 3);
        writeBinaryOf(String.format("%-,3d", number) + " >>> 3: %d: %s", number >>> 3);
        System.out.println();
        number = (int) -Math.pow(2, 31) + 320;//320 is 2^8+2^6
        writeBinaryOf("%-,34d : %s, number of trailing zeros " + Integer.numberOfTrailingZeros(number), number);
        writeBinaryOf(String.format("%-,10d", number) + " >>  4: %,12d : %s", number >> 4);
        writeBinaryOf(String.format("%-,10d", number) + " >>> 4: %,12d : %s", number >>> 4);
        writeBinaryOf(String.format("%-,10d", number) + " >>> 4: %,12d : %s, number of Leading zeros is " + Integer.numberOfLeadingZeros(number >>> 4), number >>> 4);
        System.out.println();
        String binary = "1100";
        var convertFormat = "Converting from binary %s to decimal: %,d%n";
        System.out.printf(convertFormat, binary, Integer.parseInt(binary, 2));
        binary = "1111111111111111111111111111111";// 31 1s.
        System.out.printf(convertFormat, binary, Integer.parseInt(binary, 2));
        binary = "11111111111111111111111111111111";// 32 digit
        System.out.printf(convertFormat, binary, (int) Long.parseLong(binary, 2));
        // For 32 digits we have to use Long.parseLong and convert it to int
    }

}
