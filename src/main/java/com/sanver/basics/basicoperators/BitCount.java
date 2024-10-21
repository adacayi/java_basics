package com.sanver.basics.basicoperators;

public class BitCount {
    public static void main(String[] args) {
        System.out.println("Bit count for 1 << 5: " + Integer.bitCount(1 << 5));
        System.out.println("Bit count for 31    : " + Integer.bitCount(31));
        System.out.println("Bit count for -1    : " + Integer.bitCount(-1));
        System.out.println("Bit count for -1L   : " + Long.bitCount(-1L));
    }
}
