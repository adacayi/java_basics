package com.sanver.basics.unittest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is written for TDD of SetImplementation class in src/main/java/unittest
 */
public class SetImplementationTest {
    SetImplementation set;

    @BeforeEach
    public void init() {
        set = new SetImplementation();
    }

    @Test
    public void should_Return0_When_Empty() {
        assertEquals(0, set.getSize());
    }

    @Test
    public void should_AddElement() {
        set.add("Element");
        set.add("Second");
        assertEquals(2, set.getSize());
    }

    @Test
    public void should_NotAddDuplicate() {
        set.add("Element");
        set.add("Element");
        assertEquals(1, set.getSize());
    }

    @Test
    public void should_Expand_When_SizeExceeded() {
        for (int i = 0; i < 30; i++) {
            set.add(i + "");
        }
        assertEquals(30, set.getSize());
    }

    @Test
    public void should_Remove_When_Exists() {
        for (int i = 0; i < 30; i++) {
            set.add(i + "");
        }

        assertTrue(set.remove("29"));
        assertEquals(29, set.getSize());
    }

    @Test
    public void should_Remove_When_NotExists() {
        for (int i = 0; i < 30; i++) {
            set.add(i + "");
        }

        assertFalse(set.remove("30"));
        assertEquals(30, set.getSize());
    }

    @Test
    public void should_GetCapacity() {
        SetImplementation set = new SetImplementation(20);
        assertEquals(20, set.getCapacity());
    }

    @Test
    public void should_Contract_When_SizeDecreases() {
        set = new SetImplementation(10);

        for (int i = 0; i < 30; i++) {
            set.add(i + "");
        }

        for (int i = 0; i < 30; i++) {
            set.remove(i + "");
        }

        assertTrue(set.getCapacity() <= 10);
    }
}
