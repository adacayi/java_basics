package com.sanver.basics.condition;

public class SwitchCaseMatching {
    public static void main(String[] args) {
        char c = 65;
        switch (c) { // Case constants (e.g., 2, 64) must be compatible with the type of the switch variable (char in this case).
            // The compiler attempts implicit conversion of each case constant to the type of the switch variable.
            // If a case constant's type is incompatible, or its value is out of the range that can be safely represented by the switch variable's type,
            // a compile error (e.g., "incompatible types: possible lossy conversion") occurs.
            default ->
                    System.out.println("Some character"); // Notice, although not advisable we can have default in switch statements as not the last case. This cannot be done with switch pattern matching.
            case 2 -> {
            } // We can have empty blocks in a switch statement. Notice there is no semicolon at the end.
//            case 3 ->; // This results in an error.  unexpected statement in case, expected is an expression, a block or a throw statement
            case 64 -> System.out.println(64);
            case 'A' -> System.out.println("A");
//            case 65 -> System.out.println("65"); // This will result in a compile error, since there is already a case for 65, 'A'. java: duplicate case label
//            case -1 -> System.out.println("-1"); // This will result in a compile error. incompatible types: possible lossy conversion from int to char
//            case Integer.MAX_VALUE -> System.out.println("Integer.MAX"); // This will result in a compile error. incompatible types: possible lossy conversion from int to char
        }

        System.out.println();

        byte b = 65; // Try with 66 to see that it executes default, case 64 and case 'A'.
        switch (b) {
            default:
                System.out.println("Default");
            case 64:
                System.out.println("64");
            case 'A':
                System.out.println("A");
//            case 128 -> System.out.println("128");  // This will result in a compile error. incompatible types: possible lossy conversion from int to byte
        }
    }
}
