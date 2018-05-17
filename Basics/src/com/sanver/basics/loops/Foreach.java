package com.sanver.basics.loops;

import java.util.*;

public class Foreach {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>(2);
		list.add("Ahmet");
		list.add("Mustafa");

		for (String item : list)
			System.out.println(item);

		list.forEach(a -> {
			System.out.print(a);
			System.out.println(", ");
		});
	}
}
