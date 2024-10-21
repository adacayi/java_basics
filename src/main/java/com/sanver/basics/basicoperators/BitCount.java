package com.sanver.basics.basicoperators;

public class BitCount {
    public static void main(String[] args) {
        System.out.println("Bit count for 1 << 5: " + Integer.bitCount(1 << 5));
        System.out.println("Bit count for 31    : " + Integer.bitCount(31));
        System.out.println("Bit count for -1    : " + Integer.bitCount(-1));
        System.out.println("Bit count for -1L   : " + Long.bitCount(-1L));
        System.out.println("Bit count for (1L << 33) + 7   : " + Long.toString((1L << 33) + 7, 2).chars().filter(x -> x == '1').count()); // Another way to count bits. This only works for non-negative numbers, since Long.toString returns the mathematical representation of the number in radix and puts a minus sign if it is negative.
        System.out.println("(1L << 33) + 7 : " + Long.toString((1L << 33) + 7, 2));
    }
}
