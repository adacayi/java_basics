package com.sanver.basics.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateInterfaceSample {

	public static void main(String[] args) {
		List<String> names = new ArrayList<>(Arrays.asList("Ahmet", "Mustafa", "Mehmet"));
		Predicate<String> small = s -> s.length() < 7;
		System.out.println(names.stream().filter(small.negate().or(s -> s.contains("af")).negate())
				.collect(Collectors.joining(", ")));
	}
}
