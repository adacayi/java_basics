package algorithms;

import com.sanver.basics.algorithms.BinarySearch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BinarySearchTest {
    private BinarySearch binarySearch = new BinarySearch();

    @Test
    public void should_ReturnMinus1_When_Empty() {
        int[] array = {};
        int expected = -1;
        int actual = binarySearch.search(array, 4);
        assertEquals(expected, actual);
    }

    @Test
    public void should_ReturnMinus1_When_NotExistsAndFirst() {
        int[] array = {0, 3, 5};
        int expected = -1;
        int actual = binarySearch.search(array, -4);
        assertEquals(expected, actual);
    }

    @Test
    public void should_ReturnMinus4_When_NotExistsAndLast() {
        int[] array = {0, 3, 5};
        int expected = -4;
        int actual = binarySearch.search(array, 7);
        assertEquals(expected, actual);
    }

    @Test
    public void should_ReturnMinus2_When_NotExistsAndSecond() {
        int[] array = {0, 3, 5};
        int expected = -2;
        int actual = binarySearch.search(array, 1);
        assertEquals(expected, actual);
    }

    @Test
    public void should_Return1_When_ExistsAndSecond() {
        int[] array = {0, 3, 5};
        int expected = 1;
        int actual = binarySearch.search(array, 3);
        assertEquals(expected, actual);
    }

    @Test
    public void should_Return1_When_SearchInSubArrayAndNotExistsAndSecond() {
        int[] array = {0, 3, 4, 5};
        int expected = -3;
        int actual = binarySearch.search(array, 2, 4, 1);
        assertEquals(expected, actual);
    }
}
