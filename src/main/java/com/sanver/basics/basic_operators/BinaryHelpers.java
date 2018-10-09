package com.sanver.basics.basic_operators;

public class BinaryHelpers {
    public static void writeBinaryOfInt(int number, Object... args) {
        int newNumber = number;
        String format = "%-5d: %s";

        if (args != null && args.length > 0) {
            format = (String) args[0];
            if (args.length > 1)
                newNumber = (int) args[1];
        }

        System.out.printf(format, number, toBinaryString(newNumber));

        if (newNumber != number)
            System.out.printf(" Value: %d\n", newNumber);
        else
            System.out.println();
    }

    public static String toBinaryString(int number) {
        StringBuilder result = new StringBuilder();

        for (int i = 31; i >= 0; i--) {
            result.append(number << (31 - i) >>> 31);
        }
        return result.toString();
    }
}
