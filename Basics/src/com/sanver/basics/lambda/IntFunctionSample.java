package com.sanver.basics.lambda;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntFunctionSample {

	public static void main(String[] args) {
		IntFunction<String> mapper = x -> Integer.toString(x) + " converted";
		List<String> result = IntStream.of(3, 2, 4).mapToObj(mapper).collect(Collectors.toList());
		System.out.println(result);
	}

}
