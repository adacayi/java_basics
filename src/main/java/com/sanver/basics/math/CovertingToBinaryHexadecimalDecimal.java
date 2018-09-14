package com.sanver.basics.math;

public class CovertingToBinaryHexadecimalDecimal {
    public static void main(String... args) {
        int number = 127;
        String binary = Integer.toString(number, 2);
        String hexadecimal = Integer.toString(number, 16);
        System.out.printf("%d: %-8s Binary Representation\n", number, binary);
        System.out.printf("%d: %-8s Hexadecimal Representation\n", number, hexadecimal);
        System.out.printf("%d: %-8x Hexadecimal Representation By %%x\n", number, number);
        System.out.printf("%d: %-8d Converting to decimal\n", number, Integer.parseInt(hexadecimal, 16));
    }
}
