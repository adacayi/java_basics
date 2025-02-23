package com.sanver.basics.basicoperators;

import java.util.function.BinaryOperator;

/**
 * <a href = "https://introcs.cs.princeton.edu/java/11precedence/">Operator precedence</a>
 */
public class OperatorPrecedenceSample {
    public static void main(String[] args) {
        Object a = new A();
        ((A) a).run(); // (A)a.run would give a compile error since member access(.) has higher priority than casting.

        // lambda expression -> priority
        BinaryOperator<String> concatenate = (x, y) -> x += " " + y;
        var fullName = "George";
        fullName = concatenate.apply(fullName, "Russel");
        System.out.println(fullName);

        // nested ternary operators
        var result = true ? true : false ? false : true; // This results in true and is equivalent to true ? true : (false ? false : true); which results in true, because associativity is from right to left. It is not equivalent to (true ? true : false) ? false : true; which results false.
        System.out.println(result);

        int i = 5;
        i += (i = 10); // Even though  i = 10 is calculated first, and 10 is added, it is added to i's initial value of 5, not the current value of 10 and the result is not 20, but 15.
        System.out.println("int i = 5;");
        System.out.println("i += (i = 10): " + i);
        i = i + (i = 3); // This is not 6 as well, but 15 + (i = 3) = 18. The last value of i is not used, but the initial one is used.
        System.out.println("i = i + (i = 3): " + i);
        i = (i = 36) / i; // This time though, it uses i's current value  36 / 36 = 1
        System.out.println("i = i + (i = 3): " + i);
    }

    static class A {
        public void run() {
            System.out.println("Running");
        }
    }
}
