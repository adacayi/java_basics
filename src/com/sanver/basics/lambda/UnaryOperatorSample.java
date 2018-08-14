package com.sanver.basics.lambda;

import java.util.function.UnaryOperator;

public class UnaryOperatorSample {

	public static void main(String[] args) {
		UnaryOperator<Integer> add = x -> x + 2;
		UnaryOperator<Integer> multiply = x -> x * 5;
		// Function<Integer,Integer> addFunction=add; // This will work, since
		// UnaryOperator is derived from Function.
		// public interface UnaryOperator<T> extends Function<T, T>
		System.out.println(add.compose(multiply).apply(3));
		// UnaryOperator<Integer> function=add.compose(multiply);// This assignment
		// would give an error because compose returns Function.
	}

}
