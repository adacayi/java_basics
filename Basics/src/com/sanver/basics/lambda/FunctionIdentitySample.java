package com.sanver.basics.lambda;

import java.util.function.Function;

public class FunctionIdentitySample {

	public static void main(String[] args) {
		Function<String, String> identity = Function.identity();
		System.out.println(identity.apply("Selamunaleykum"));
	}
}
