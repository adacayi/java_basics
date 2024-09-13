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
    }

    static class A {
        public void run() {
            System.out.println("Running");
        }
    }
}
