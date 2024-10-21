package com.sanver.basics.basicoperators;

public class BinaryHelpers {
    private BinaryHelpers() {
    }

    /**
     * Writes the binary representation of the number. If the number is within the integer range, it writes it in 32 bit form by default
     *
     * @param number The number to return the binary representation of.
     * @param args   Optional parameters. First argument is for formatting the output, second parameter is for the number of bits to be printed
     */
    public static void writeBinaryOf(long number, Object... args) {
        var format = "%-,5d: %s";
        long newNumber = number;
        var numberOfBits = number > Integer.MAX_VALUE || number < Integer.MIN_VALUE ? 64 : 32;

        if (args != null && args.length > 0) {
            format = (String) args[0];
            if (args.length > 1) {
                newNumber = Long.parseLong(args[1].toString());
            }
            if (args.length > 2) {
                numberOfBits = (int) args[2];
            }
        }

        System.out.printf(format, number, toBinaryString(newNumber, numberOfBits));

        if (newNumber != number)
            System.out.printf(" Value: %,d%n", newNumber);
        else
            System.out.println();
    }

    public static String toBinaryString(long number, int numberOfBits) {
        if (numberOfBits > 64) {
            throw new IllegalArgumentException("This can only work up to 64 bit representation");
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < numberOfBits; i++) {
            result.append(number << (64 - (numberOfBits - i)) >>> 63); // This is to first clear out the bits before the ith digit by moving it to the far left and then move it to the far right, so only the ith digit remains as the far most digit and all others are 0.
            // Remember, even when doing bitwise operations on types like bytes with 8 bit representation, the result will be calculated in 32 bit, hence when moving the digits to far left we need to move it not by 7-i but 31-(7-i).
        }

        /* We can also use the below approach
         for (int i = numberOfBits - 1; i >= 0; i--) {
            result.append((number & (1L << i)) == 0 ? "0" : "1");
         }
         */
        return result.toString();
    }
}
