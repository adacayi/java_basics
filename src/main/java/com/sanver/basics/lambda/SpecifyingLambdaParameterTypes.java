package com.sanver.basics.lambda;

import java.util.function.IntBinaryOperator;

public class SpecifyingLambdaParameterTypes {
    public static void main(String[] args) {
        // If you want to annotate lambda parameters (e.g., for documentation or validation purposes), you need to specify the type explicitly.
        // You can also use var as well for that case from Java 11 forwards.

        IntBinaryOperator first = (int x, int y) -> x + y + 5;
        IntBinaryOperator second = (var x, var y) -> x + y + 5;
        System.out.println(first.applyAsInt(3, 7));
        System.out.println(second.applyAsInt(1, 10));
    }
}
