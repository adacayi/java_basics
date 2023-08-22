package com.sanver.basics.basicoperators;

public class BinaryHelpers {
    public static void writeBinaryOfInt(int number, Object... args) {
        var format = "%-5d: %s";
        var newNumber = number;
        var numberOfBits = 32;

        if (args != null && args.length > 0) {
            format = (String) args[0];
            if (args.length > 1) {
                newNumber = (int) args[1];
            }
            if (args.length > 2) {
                numberOfBits = (int) args[2];
            }
        }

        System.out.printf(format, number, toBinaryString(newNumber, numberOfBits));

        if (newNumber != number)
            System.out.printf(" Value: %d\n", newNumber);
        else
            System.out.println();
    }

    public static String toBinaryString(int number, int numberOfBits) {
        if (numberOfBits > 32) {
            throw new IllegalArgumentException("This can only work up to 32 bit representation");
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < numberOfBits; i++) {
            result.append(number << (31 - ((numberOfBits - 1) - i)) >>> 31); // This is to first clear out the bits before the ith digit by moving it to the far left and then move it to the far right, so only the ith digit remains as the far most digit and all others are 0.
            // Remember, even when doing bitwise operations on types like bytes with 8 bit representation, the result will be calculated in 32 bit, hence when moving the digits to far left we need to move it not by 7-i but 31-(7-i).
        }

        return result.toString();
    }
}
