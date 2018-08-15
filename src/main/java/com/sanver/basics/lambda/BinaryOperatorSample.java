package com.sanver.basics.lambda;

import java.util.Comparator;
import java.util.function.BinaryOperator;

public class BinaryOperatorSample {

	public static void main(String[] args) {
		// BinaryOperator extends BiFunction<T,T,T>
		BinaryOperator<Integer> operator = (x, y) -> x + y + 5;// These also result in the same output, because the
																// operation's result is returned: (x, y) -> x += y + 5;
		// (x, y) -> y += x + 5
		System.out.println(operator.andThen(x -> x * 10).apply(2, 3));
		BinaryOperator<Integer> min = BinaryOperator.minBy(Comparator.comparing((Integer x) -> x));
		System.out.println(min.apply(3, 2));
		BinaryOperator<Integer> max = BinaryOperator.maxBy(Comparator.comparing((Integer x) -> x));
		System.out.println(max.apply(3, 2));
	}
}
