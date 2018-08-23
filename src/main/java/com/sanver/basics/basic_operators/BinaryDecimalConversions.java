package com.sanver.basics.basic_operators;

import static com.sanver.basics.basic_operators.BinaryHelpers.*;

public class BinaryDecimalConversions {
    public static void main(String... args) {
        int number = 2;
        System.out.printf("To write the negative of %d write ~(%d): %d\n", number, number - 1, ~(number - 1));
        number = -2;
        System.out.printf("To write the negative of %d write ~(%d): %d\n", number, number - 1, ~(number - 1));
        System.out.println();
        writeBinaryOfInt(0);
        writeBinaryOfInt(-1);// Negative numbers are written as bitwise complements of (its absolute value -1).
        // In the case of -1 it is the bitwise of 0.
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
