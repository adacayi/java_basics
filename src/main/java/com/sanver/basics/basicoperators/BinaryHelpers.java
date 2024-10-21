package com.sanver.basics.basicoperators;

public class BinaryHelpers {

    public static final String DEFAULT_FORMAT = "%-,5d: %s";

    private BinaryHelpers() {
    }

    /**
     * Writes the binary representation of the number. If the number is within the integer range, it writes it in 32 bit form by default
     *
     * @param number The number to print the binary representation of.
     */
    public static void writeBinaryOf(long number) {
        writeBinaryOf(DEFAULT_FORMAT, number, defaultNumberOfBits(number));
    }

    /**
     * Writes the binary representation of the number. If the number is within the integer range, it writes it in 32 bit form by default
     *
     * @param format Output format to print the binary representation of the first and second argument
     * @param number The number to print the binary representation of.
     */
    public static void writeBinaryOf(String format, long number) {
        writeBinaryOf(format, number, defaultNumberOfBits(number));
    }

    private static int defaultNumberOfBits(long number) {
        return number > Integer.MAX_VALUE || number < Integer.MIN_VALUE ? 64 : 32;
    }

    /**
     * Writes the binary representation of the number.
     *
     * @param format Output format to print the binary representation of the first and second argument
     * @param number The number to print the binary representation of.
     * @param numberOfBits The number of bits to be printed
     */
    public static void writeBinaryOf(String format, long number, int numberOfBits) {
        System.out.printf(format, number, toBinaryString(number, numberOfBits));
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
