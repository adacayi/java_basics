package com.sanver.basics.strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinSample {

	public static void main(String[] args) {
		List<String> names = new ArrayList<>(Arrays.asList("Ahmet", "Mustafa", "Muhammed"));
		String joined = String.join(", ", names);
		System.out.println(joined);
	}

}
