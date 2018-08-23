package com.sanver.basics.basic_operators;

public class BinaryHelpers {
    public static void writeBinaryOfInt(int number, Object... args) {
        int result;
        int newNumber = number;
        String format = "%-5d: %s";

        if (args != null && args.length > 0) {
            format = (String) args[0];
            if (args.length > 1)
                newNumber = (int) args[1];
        }

        System.out.printf(format + "\n", number, toBinaryString(newNumber));
    }

    public static String toBinaryString(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 31; i >= 0; i--) {
            result.append(number << (31 - i) >>> 31);
        }
        return result.toString();
    }
}
