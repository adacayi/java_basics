package com.sanver.basics.basic_operators;

import static com.sanver.basics.basic_operators.BinaryHelpers.*;

public class BitwiseOperationsSample {
    public static void main(String... args) {
        writeBinaryOfInt(3, "%d     : %s");
        writeBinaryOfInt(8, "%d     : %s");
        writeBinaryOfInt(3, "%d | 8 : %s", 3 | 8);
        writeBinaryOfInt(3, "%d & 8 : %s", 3 & 8);
        writeBinaryOfInt(3, "%d ^ 8 : %s", 3 ^ 8);
        writeBinaryOfInt(12, "%d       : %s", 12 );
        writeBinaryOfInt(12, "%d <<  2 : %s", 12 << 2);
        writeBinaryOfInt(12, "%d >>  2 : %s", 12 >> 2);
        writeBinaryOfInt(12, "%d >>> 2 : %s", 12 >>> 2);
        int number = (int) -Math.pow(2, 31) + 320;
        writeBinaryOfInt(number, "%d       : %s", number);
        writeBinaryOfInt(number, "%d >>  2 : %s", number >> 2);
        writeBinaryOfInt(number, "%d >>> 2 : %s", number >>> 2);
    }
}
