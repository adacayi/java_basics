package com.sanver.basics.collections;

import java.util.BitSet;

public class BitSetSample {

    public static void main(String[] args) {
        int i;
        int length = 10;
        BitSet divisibleBy2 = new BitSet(length); // BitSets are packed into arrays of "words." Currently, a word is a long, which consists of 64 bits. BitSet(length) will initialize a long array of size (length - 1) >> 6 + 1 since every long can hold 64 bits.
        System.out.println("divisibleBy2 size (number of bits it can hold): " + divisibleBy2.size()); // Size of the long array times 64 since every long can hold 64 bits.
        System.out.println("divisibleBy2 length (index of the last true bit plus 1): " + divisibleBy2.length()); // length returns the index of the last true bit plus 1.
        System.out.println("divisibleBy2 cardinality (number of bits set to true): " + divisibleBy2.cardinality()); // length returns the index of the last true bit plus 1.
        BitSet divisibleBy3 = new BitSet(length);
        BitSet divisibleBy2And3;
        BitSet divisibleBy2Or3;

        for (i = 0; i < length; i++) {
            if (i % 2 == 0)
                divisibleBy2.set(i, true);// This is the same as divisibleBy2.set(i)
            if (i % 3 == 0)
                divisibleBy3.set(i);
        }

        divisibleBy2Or3 = (BitSet) divisibleBy2.clone();
        divisibleBy2And3 = (BitSet) divisibleBy2.clone();
        divisibleBy2Or3.or(divisibleBy3); // The size of the BitSet will increase to the BitSet in the "or" method's argument if the argument's size is larger, otherwise it won't change.
        divisibleBy2And3.and(divisibleBy3); // The size of the BitSet will decrease to the BitSet in the "and" method's argument if the argument's size is smaller, otherwise it won't change.
        System.out.printf("Divisible by 2: %s%n", divisibleBy2);
        System.out.printf("Divisible by 3: %s%n", divisibleBy3);
        System.out.printf("Divisible by 2 or 3: %s%n", divisibleBy2Or3);
        System.out.printf("Divisible by 2 and 3: %s%n", divisibleBy2And3);

        System.out.print("Divisible by 2 and 3 content: ");
        for (i = 0; i < divisibleBy2And3.length(); i++) {// length returns the index of the last true bit plus 1. In this case it is 7.
            System.out.printf("%s, ", divisibleBy2And3.get(i));
        }

        System.out.println();
        int size = divisibleBy2And3.size();
        System.out.println("Size of divisible by 2 and 3 BitSet is " + size);
        System.out.println("length() for divisible by 2 and 3 BitSet is (length returns the index of the last true bit plus 1) " + divisibleBy2And3.length());
        System.out.println("cardinality() for divisible by 2 and 3 BitSet is (cardinality returns the number of bits set to true) " + divisibleBy2And3.cardinality());

        for (i = 0; i < size; i++) {
            if (divisibleBy2And3.get(i))
                System.out.printf("%d, ", i);
        }
    }
}
