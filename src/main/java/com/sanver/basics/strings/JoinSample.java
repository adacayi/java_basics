package com.sanver.basics.strings;

import java.util.Arrays;
import java.util.List;

public class JoinSample {

	public static void main(String[] args) {
		String[] names= {"Ahmet", "Mustafa", "Muhammed"};
		List<String> nameList = Arrays.asList(names);
		System.out.println(String.join(", ", nameList));
		System.out.println(String.join("-",names));
	}
}
