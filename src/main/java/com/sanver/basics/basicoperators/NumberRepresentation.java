package com.sanver.basics.basicoperators;

public class NumberRepresentation {
    public static void main(String[] args) {
        int i = 5;
        var format = "%-20s : %d%n";
        System.out.printf(format, "i = 5", i);
        i = 0x121; // This is a hexadecimal number
        System.out.printf(format, "i = 0x121", i);
        i = 0x000121; // This is a hexadecimal number as well. The leading 0s are ignored.
        System.out.printf(format, "i = 0x000121", i);
        i = 0123; // This is an octal number.
        System.out.printf(format, "i = 0123", i);
        i = 000123; // This is an octal number as well. The leading 0s are ignored.
        System.out.printf(format, "i = 000123", i);

        // Binary Representation
        i = 0b1010; // This is a binary number (decimal 10)
        System.out.printf(format, "i = 0b1010", i);
        i = 0B1010; // You can use 0B as well as 0b and they are identical.
        System.out.printf(format, "i = 0B1010", i);
        i = 0b00010101; //leading 0s are ignored.
        System.out.printf(format, "i = 0b00010101", i);

        // Underscores in binary literals:
        i = 0b0_00_101___0_1; // underscores are allowed for readability.
        // Note that underscore is not allowed just after b, or x. (e.g. 0b_1 or 0x_1 are not allowed).
        // Underscore cannot be the last character as well. (e.g. 0b1_ is not allowed)
        System.out.printf(format, "i = 0b0_00_101___0_1", i);

        i = (int)1e5;// 1e5 is a double, so needs explicit casting to int.
        System.out.printf(format, "i = (int)1e5", i);
        //float f = (double)1e5; // This needs explicit casting as well.
    }
}
