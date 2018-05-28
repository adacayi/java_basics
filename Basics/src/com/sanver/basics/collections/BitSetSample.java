package com.sanver.basics.collections;

import java.util.BitSet;

public class BitSetSample {

	public static void main(String[] args) {
		int i, length = 10;
		BitSet divisibleBy2 = new BitSet(length);
		BitSet divisibleBy3 = new BitSet(length);
		BitSet divisibleBy2And3 = new BitSet();
		BitSet divisibleBy2Or3;

		for (i = 0; i < length; i++) {
			if (i % 2 == 0)
				divisibleBy2.set(i, true);// This is the same as divisibleBy2.set(i)
			if (i % 3 == 0)
				divisibleBy3.set(i);
		}

		divisibleBy2Or3 = (BitSet) divisibleBy2.clone();
		divisibleBy2And3 = (BitSet) divisibleBy2.clone();
		divisibleBy2Or3.or(divisibleBy3);
		divisibleBy2And3.and(divisibleBy3);
		System.out.println(divisibleBy2);
		System.out.println(divisibleBy3);
		System.out.println(divisibleBy2Or3);
		System.out.println(divisibleBy2And3);

		for (i = 0; i < divisibleBy2And3.length(); i++)
			System.out.println(divisibleBy2And3.get(i));

		int size = divisibleBy2And3.size(); // Size is larger than the last bit with number 1
		System.out.println("Size of divisible by 2 and 3 BitSet is " + size);

		for (i = 0; i < size; i++) {
			if (divisibleBy2And3.get(i))
				System.out.println(i);
		}
	}
}
