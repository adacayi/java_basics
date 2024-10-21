package com.sanver.basics.basicoperators;

import static com.sanver.basics.basicoperators.BinaryHelpers.*;

public class BitwiseOperationsSample {
    public static void main(String... args) {
        writeBinaryOf(3, "%d     : %s");
        writeBinaryOf(-1, "%d    : %s");
        writeBinaryOf(8, "%d     : %s");
        writeBinaryOf(3, "%d | 8 : %s", 3 | 8);
        writeBinaryOf(3, "%d & 8 : %s", 3 & 8);
        writeBinaryOf(3, "%d ^ 8 : %s", 3 ^ 8);
        writeBinaryOf(12, "%d       : %s", 12 );
        writeBinaryOf(12, "%d <<  2 : %s", 12 << 2);
        writeBinaryOf(12, "%d >>  2 : %s", 12 >> 2);
        writeBinaryOf(12, "%d >>> 2 : %s", 12 >>> 2);
        int number = (int) -Math.pow(2, 31) + 320;
        writeBinaryOf(number, "%d       : %s", number);
        writeBinaryOf(number, "%d >>  2 : %s", number >> 2);
        writeBinaryOf(number, "%d >>> 2 : %s", number >>> 2);
        var threeDigitFormat = "%-3d: %s";
        var twoDigitFormat = "~%-2d: %s";
        writeBinaryOf(0, threeDigitFormat,0);
        writeBinaryOf(0, twoDigitFormat,~0);
        writeBinaryOf(1, threeDigitFormat,1);
        writeBinaryOf(1, twoDigitFormat,~1);
        writeBinaryOf(2, threeDigitFormat,2);
        writeBinaryOf(2, twoDigitFormat,~2);
        writeBinaryOf(-1, threeDigitFormat,-1);
        writeBinaryOf(-1, twoDigitFormat,~-1);
        writeBinaryOf(number, "%d  : %s", number);
        writeBinaryOf(number,"~%d : %s",~number);
    }
}
