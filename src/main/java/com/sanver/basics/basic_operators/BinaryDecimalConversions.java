package com.sanver.basics.basic_operators;

public class BinaryDecimalConversions {
    public static void main(String... args) {
        int number = 2;
        System.out.printf("To write the negative of %d write ~(%d): %d\n", number, number - 1, ~(number - 1));
        number = -2;
        System.out.printf("To write the negative of %d write ~(%d): %d\n", number, number - 1, ~(number - 1));
        writeBinaryOfInt(0);
        writeBinaryOfInt(-1);// Negative numbers are written as bitwise complements of (its absolute value -1).
        // In the case of -1 it is the bitwise of 0.
        writeBinaryOfInt(1);
        writeBinaryOfInt(-2);
        writeBinaryOfInt(4);
        writeBinaryOfInt(-3);
        writeBinaryOfInt(9);
        writeBinaryOfInt(-9);
        System.out.printf("%-,5d: %s\n", -1, Integer.toBinaryString(-1));
        System.out.printf("%-10s: %d\n", "-1 >> 31", -1 >> 31);
        System.out.printf("%-10s: %d\n", "-2 >> 31", -1 >> 31);
        System.out.printf("%-10s: %d\n", "-1 >>> 31", -1 >>> 31);
        number=48;
        System.out.printf("%-,3d      : %s\n", number, toBinaryString(number));
        System.out.printf("%-,3d >>  3: %s\n", number, toBinaryString(number >> 3));
        System.out.printf("%-,3d >>> 3: %s\n", number, toBinaryString(number >>> 3));
        number = (int) -Math.pow(2, 31) + 320;//320 is 2^8+2^6
        System.out.printf("%-,10d      : %s\n", number, toBinaryString(number));
        System.out.printf("%-,10d >>  4: %s\n", number, toBinaryString(number >> 4));
        System.out.printf("%-,10d >>> 4: %s\n", number, toBinaryString(number >>> 4));
        String binary = "1100";
        System.out.printf("Converting from binary %s to decimal: %,d\n", binary, Integer.parseInt(binary, 2));
        binary = "1111111111111111111111111111111";// 31 1s.
        System.out.printf("Converting from binary %s to decimal: %,d\n", binary, Integer.parseInt(binary, 2));
        binary = "11111111111111111111111111111111";// 32 digit
        System.out.printf("Converting from binary %s to decimal: %,d\n", binary, (int) Long.parseLong(binary, 2));
        // For 32 digits we have to use Long.parseLong and convert it to int
    }

    public static void writeBinaryOfInt(int number) {
        int result;
        System.out.printf("%-5d: ", number);
        for (int i = 31; i >= 0; i--) {
            result = number << (31 - i) >>> 31;
            System.out.print(result);
        }
        System.out.println();
    }
    public static String toBinaryString(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 31; i >= 0; i--) {
            result.append(number << (31 - i) >>> 31);
        }
        return result.toString();
    }
}
