package com.sanver.basics.basicoperators;

import static com.sanver.basics.basicoperators.BinaryHelpers.*;

public class BitwiseOperationsSample {
    public static void main(String... args) {
        writeBinaryOfInt(3, "%d     : %s");
        writeBinaryOfInt(-1, "%d    : %s");
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
        writeBinaryOfInt(0,"%-3d: %s",0);
        writeBinaryOfInt(0,"~%-2d: %s",~0);
        writeBinaryOfInt(1,"%-3d: %s",1);
        writeBinaryOfInt(1,"~%-2d: %s",~1);
        writeBinaryOfInt(2,"%-3d: %s",2);
        writeBinaryOfInt(2,"~%-2d: %s",~2);
        writeBinaryOfInt(-1,"%-3d: %s",-1);
        writeBinaryOfInt(-1,"~%-2d: %s",~-1);
        writeBinaryOfInt(number, "%d  : %s", number);
        writeBinaryOfInt(number,"~%d : %s",~number);
    }
}
