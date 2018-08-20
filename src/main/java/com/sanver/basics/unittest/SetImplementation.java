package com.sanver.basics.unittest;

import java.util.Arrays;

/**
 * This class is coded with TDD principles.
 * The unit tests are in src/test/java/unittest/SetImplementationTest.java
 */
public class SetImplementation<E> {
    private int initialCapacity = 10;
    private final int SIZE_CHANGE = 10;
    private int lastIndex = 0;
    private Object[] set;

    public SetImplementation() {
        set = new Object[initialCapacity];
    }

    public SetImplementation(int capacity) {
        initialCapacity = capacity;
        set = new Object[initialCapacity];
    }

    public int getSize() {
        return lastIndex;
    }

    public void add(E element) {
        if (lastIndex == set.length) {
            set = Arrays.copyOf(set, lastIndex + SIZE_CHANGE);
        }

        for (int i = 0; i < lastIndex; i++) {
            if (set[i].equals(element))
                return;
        }

        set[lastIndex] = element;
        lastIndex++;
    }

    public boolean remove(E element) {
        for (int i = 0; i < lastIndex; i++) {
            if (set[i].equals(element)) {
                set[i] = set[lastIndex - 1];
                lastIndex--;

                if (lastIndex < getCapacity() - SIZE_CHANGE)
                    set = Arrays.copyOf(set, getCapacity() - SIZE_CHANGE);

                return true;
            }
        }
        return false;
    }

    public int getCapacity() {
        return set.length;
    }

    public boolean contains(E value) {
        for (Object item : set) {
            if (item == value)
                return true;
        }
        return false;
    }
}
