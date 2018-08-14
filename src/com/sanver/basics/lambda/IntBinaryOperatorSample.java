package com.sanver.basics.lambda;

import java.util.function.IntBinaryOperator;

public class IntBinaryOperatorSample {

	public static void main(String[] args) {
		IntBinaryOperator compare = Integer::compare;
		System.out.println(compare.applyAsInt(16, 11));
	}
}
