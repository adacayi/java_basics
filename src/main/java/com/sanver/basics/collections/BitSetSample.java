package com.sanver.basics.collections;

import java.util.BitSet;

public class BitSetSample {

    public static void main(String[] args) {
        int i, length = 10;
        BitSet divisibleBy2 = new BitSet(length);
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
        divisibleBy2Or3.or(divisibleBy3);
        divisibleBy2And3.and(divisibleBy3);
        System.out.printf("Divisible by 2: %s\n", divisibleBy2);
        System.out.printf("Divisible by 3: %s\n", divisibleBy3);
        System.out.printf("Divisible by 2 or 3: %s\n", divisibleBy2Or3);
        System.out.printf("Divisible by 2 and 3: %s\n", divisibleBy2And3);

        System.out.print("Divisible by 2 and 3 content: ");
        for (i = 0; i < divisibleBy2And3.length(); i++) // length returns the index of the last true bit plus 1.
            // In this case it is 7.
            System.out.printf("%s, ",divisibleBy2And3.get(i));

        System.out.println();
        int size = divisibleBy2And3.size(); // Size is larger than the last bit with number 1
        System.out.println("Size of divisible by 2 and 3 BitSet is " + size);

        for (i = 0; i < size; i++) {
            if (divisibleBy2And3.get(i))
                System.out.printf("%d, ",i);
        }
    }
}
