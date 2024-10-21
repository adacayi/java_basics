package com.sanver.basics.basicoperators;

import java.text.NumberFormat;

public class ShiftOverflow {
    public static void main(String[] args) {
        int a = 1 << 31;
        var format = NumberFormat.getInstance();
        System.out.println("1 << 31 =           " + format.format(a) );
        System.out.println("Integer.MIN_VALUE = " + format.format(Integer.MIN_VALUE));
        //        In Java, the << operator only considers the lower 5 bits of the right-hand operand (because the left operand is a 32-bit integer). So, 1 << 32 is equivalent to 1 << (32 % 32) or 1 << 0, which is just 1.
        System.out.println("1 << 32 = " + (1 << 32)); // This will be calculated as 1L << (32 % 32)
        System.out.println("1 << 33 = " + (1 << 33)); // This will be calculated as 1L << (33 % 32)
//        1 << 31 shifts the bit 1 to the 31st position. This is effectively 0x80000000, which is the minimum value for a 32-bit signed integer (i.e., -2,147,483,648 in decimal).
//        Shifting 0x80000000 left by 1 position (<< 1) results in 0x00000000 (all bits become zero), because shifting beyond the sign bit in a signed 32-bit integer causes overflow. This gives a result of 0.
        System.out.println("(1 << 31) << 1 = " + ((1 << 31) << 1));

        System.out.println();
        long b = 1L << 63;

        System.out.println("1L << 63 =           " + format.format(b) );
        System.out.println("Long.MIN_VALUE =     " + format.format(Long.MIN_VALUE));
        System.out.println("1L << 64 = " + (1L << 64)); // This will be calculated as 1L << (64 % 64)
        System.out.println("1L << 65 = " + (1L << 65)); // This will be calculated as 1L << (65 % 64)
        System.out.println("(1L << 63) << 1 = " + ((1L << 63) << 1));
    }
}
