package com.sanver.basics.basicoperators;

import static com.sanver.basics.basicoperators.BinaryHelpers.writeBinaryOfInt;

public class BinaryDecimalConversions {
    public static void main(String... args) {
        // Documentation for how negative numbers are represented in binary form: https://www.geeksforgeeks.org/representation-of-negative-binary-numbers/
        // In short, we don't just use the first bit to denote sign, because for 0 we have 2 representations and we for example for 8 bits, we can only represent down to -127, and not -128 because of that.
        // For having a single representation of 0 and be able to represent -128 with 8 bits, for any negative number -x, we represent its binary form as follows:
        // We take the complement (change 0s to 1s and 1s to 0s) of the positive x and then add 1 (This operation is called 2's complement method).
        // So for -1, we have 1 in 8 bits as 00000001, we take its complement 11111110 and then add one and get 11111111 as the binary representation of -1.
        // For -128 in 8 bits though let's represent -127 first and then subtract 1 since we cannot represent 128 with 8 bits (if we want negatives as well).
        // 127 is 01111111. The complement is 10000000. If we add 1 then we get 10000001 as -127. Now -128 is -1 of that. So it is 10000000 in 8 bits.
        int number = 2;
        System.out.printf("To write the negative of %d write ~%d + 1 = %d\n", number, number, ~number + 1);
        number = -2;
        System.out.printf("To write the negative of %d write ~(%d) + 1 = %d\n", number, number, ~number + 1);
        System.out.println();
        writeBinaryOfInt(0, "%-5d: %s (32 bit representation)", 0);
        writeBinaryOfInt(0, "%-5d: %s (8 bit representation)", 0, 8);
        writeBinaryOfInt(-1, "%-5d: %s (32 bit representation)", -1);
        writeBinaryOfInt(-1, "%-5d: %s (8 bit representation)", -1, 8);
        writeBinaryOfInt(128, "%-5d: %s (32 bit representation)", 128);
        writeBinaryOfInt(-128, "%-5d: %s (32 bit representation)", -128);
        writeBinaryOfInt(-128, "%-5d: %s (8 bit representation)", -128, 8);
        writeBinaryOfInt(1);
        writeBinaryOfInt(-2);
        writeBinaryOfInt(4);
        writeBinaryOfInt(-3);
        writeBinaryOfInt(9);
        writeBinaryOfInt(-9);
        System.out.println();
        System.out.printf("%-,5d: %s\n", -12, Integer.toBinaryString(-12));// This is to show Integer.toBinaryString
        // method
        System.out.println();
        writeBinaryOfInt(-1, "%-,3d         : %s");
        writeBinaryOfInt(-1, "%-,3d  >> 31  : %s", -1 >> 31);
        writeBinaryOfInt(-1, "%-,3d  >>> 31 : %s", -1 >>> 31);
        writeBinaryOfInt(-2, "%-,3d         : %s");
        writeBinaryOfInt(-2, "%-,3d  >> 31  : %s", -2 >> 31);
        System.out.println();
        number = 48;
        writeBinaryOfInt(number, "%-,3d      : %s");
        writeBinaryOfInt(number, "%-,3d >>  3: %s", number >> 3);
        writeBinaryOfInt(number, "%-,3d >>> 3: %s", number >>> 3);
        System.out.println();
        number = (int) -Math.pow(2, 31) + 320;//320 is 2^8+2^6
        writeBinaryOfInt(number, "%-,10d      : %s");
        writeBinaryOfInt(number, "%-,10d >>  4: %s", number >> 4);
        writeBinaryOfInt(number, "%-,10d >>> 4: %s", number >>> 4);
        System.out.println();
        String binary = "1100";
        System.out.printf("Converting from binary %s to decimal: %,d\n", binary, Integer.parseInt(binary, 2));
        binary = "1111111111111111111111111111111";// 31 1s.
        System.out.printf("Converting from binary %s to decimal: %,d\n", binary, Integer.parseInt(binary, 2));
        binary = "11111111111111111111111111111111";// 32 digit
        System.out.printf("Converting from binary %s to decimal: %,d\n", binary, (int) Long.parseLong(binary, 2));
        // For 32 digits we have to use Long.parseLong and convert it to int
    }

}
