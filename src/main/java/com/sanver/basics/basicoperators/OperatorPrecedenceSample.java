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
        i += (i = 10); // This is equal to i = i + (i = 10) and when processed the initial value of i (5) is put to the stack first, then i = 10 is calculated and 10 is put to the stack, and i became 10, but finally the two values in the stack 5 and 10 are added and assigned to i, making i 15.
        System.out.println("int i = 5;");
        System.out.println("i += (i = 10)     : " + i);
        i = i + (i = 3); // This is not 6 as well, 15 + (i = 3) = 18.
        System.out.println("i  = i + (i = 3)  : " + i);
        i = (i = 36) / i; // When processed i = 36 is calculated first and its value put to the stack and i became 36. Next, i's current value (36) is put to the stack and the two values in the stack 36 and 36 are divided, resulting in 1.
        System.out.println("i  = (i = 36) / i : " + i);
    }

    static class A {
        public void run() {
            System.out.println("Running");
        }
    }
}
