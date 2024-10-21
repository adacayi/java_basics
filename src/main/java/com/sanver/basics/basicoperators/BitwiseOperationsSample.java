package com.sanver.basics.basicoperators;

import static com.sanver.basics.basicoperators.BinaryHelpers.writeBinaryOf;

public class BitwiseOperationsSample {
    public static void main(String... args) {
        writeBinaryOf("%d          : %s", 3);
        writeBinaryOf("%d         : %s", -1);
        writeBinaryOf("%d          : %s", 8);
        writeBinaryOf("3 | 8 : %d : %s", 3 | 8);
        writeBinaryOf("3 & 8 : %2d : %s", 3 & 8);
        writeBinaryOf("3 ^ 8 : %d : %s", 3 ^ 8);
        writeBinaryOf("%-13d : %s", 12);
        writeBinaryOf("12 <<  2 : %d : %s", 12 << 2);
        writeBinaryOf("12 >>  2 : %2d : %s", 12 >> 2);
        writeBinaryOf("12 >>> 2 : %2d : %s", 12 >>> 2);
        int number = (int) -Math.pow(2, 31) + 320;
        writeBinaryOf("%,-32d : %s", number);
        writeBinaryOf(number + " >>  2 : %,12d : %s", number >> 2);
        writeBinaryOf(number + " >>> 2 : %,12d : %s", number >>> 2);

        var formatWith8Digit = "%-,8d: %s";
        writeBinaryOf(formatWith8Digit, 0);
        writeBinaryOf("~0 : %d : %s", ~0);
        writeBinaryOf(formatWith8Digit, 1);
        writeBinaryOf("~1 : %d : %s", ~1);
        writeBinaryOf(formatWith8Digit, 2);
        writeBinaryOf("~2 : %d : %s", ~2);
        writeBinaryOf(formatWith8Digit, -1);
        writeBinaryOf("~-1 : %d : %s", ~-1);
        writeBinaryOf("%,-30d  : %s", number);
        writeBinaryOf(String.format("~%,d", number) + " : %,d : %s", ~number);
    }
}
