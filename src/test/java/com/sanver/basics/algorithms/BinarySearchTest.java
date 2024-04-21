package com.sanver.basics.algorithms;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BinarySearchTest {
    private final BinarySearch binarySearch = new BinarySearch();

    @Nested
    class Search {

        @Test
        void shouldReturnMinusOne_whenEmpty() {
            // Given
            int[] array = {};
            int key = 4;
            int expected = -1;

            // When
            int actual = binarySearch.search(array, key);

            // Then
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnMinusOne_whenNotExistsAndSmallerThanAllTheElements() {
            // Given
            int[] array = {0, 3, 5};
            int key = -4;
            int expected = -1;

            // When
            int actual = binarySearch.search(array, key);

            // Then
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnMinusLengthMinusOne_whenNotExistsAndGreaterThanAllTheElements() {
            // Given
            int[] array = {0, 3, 5};
            int key = 7;
            int expected = -4;

            // When
            int actual = binarySearch.search(array, key);

            // Then
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnMinusTwo_whenNotExistsAndShouldBePlacedSecond() {
            // Given
            int[] array = {0, 3, 5};
            int key = 1;
            int expected = -2;

            // When
            int actual = binarySearch.search(array, key);

            // Then
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnOne_whenExistsAndPlacedSecond() {
            // Given
            int[] array = {0, 3, 5};
            int key = 3;
            int expected = 1;

            // When
            int actual = binarySearch.search(array, key);

            // Then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class SearchInSubArray {
        @Test
        void shouldReturnMinusThree_whenNotExistsInTheSubArrayStartingFromTheThirdElementAndIsSmallerThanAllTheElementsInTheSubArray() {
            // Given
            int[] array = {0, 3, 4, 5};
            int key = 3;
            int expected = -3;

            // When
            int actual = binarySearch.search(array, 2, 4, key);

            // Then
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnMinusFour_whenNotExistsInTheSubArrayEndingAtTheThirdElementAndIsLargerThanAllTheElementsInTheSubArray() {
            // Given
            int[] array = {0, 3, 4, 5};
            int key = 5;
            int expected = -4;

            // When
            int actual = binarySearch.search(array, 1, 3, key);

            // Then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class SearchFirst {
        @Test
        void shouldReturnTwo_whenMultipleInstancesExistAndTheFirstInstanceIsInIndexTwo() {
            // Given
            int[] array = {2, 3, 4, 4, 4, 6};
            int key = 4;
            int expected = 2;

            // When
            int actual = binarySearch.searchFirst(array, key);

            // Then
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnMinusLength_whenNotExistsAndIsGreaterThanAllButTheLastElement() {
            // Given
            int[] array = {2, 3, 4, 4, 4, 6};
            int key = 5;
            int expected = -6;

            // When
            int actual = binarySearch.searchFirst(array, key);

            // Then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class SearchFirstInSubArray {

        @Test
        void shouldReturnMinusThree_whenNotExistsInTheSubArrayStartingFromTheThirdElementAndIsSmallerThanAllTheElementsInTheSubArray() {
            // Given
            int[] array = {2, 3, 4, 4, 4, 5};
            int key = 1;
            int expected = -3;

            // When
            int actual = binarySearch.searchFirst(array, 2, 6, key);

            // Then
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnMinusFour_whenNotExistsInTheSubArrayEndingAtTheThirdElementAndIsLargerThanAllTheElementsInTheSubArray() {
            // Given
            int[] array = {0, 3, 3, 4, 4, 5};
            int key = 5;
            int expected = -4;

            // When
            int actual = binarySearch.searchFirst(array, 1, 3, key);

            // Then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class SearchLast {
        @Test
        void shouldReturnFour_whenMultipleInstancesExistAndTheLastInstanceIsInIndexFour() {
            // Given
            int[] array = {2, 3, 4, 4, 4, 6};
            int key = 4;
            int expected = 4;

            // When
            int actual = binarySearch.searchLast(array, key);

            // Then
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnMinusLength_whenNotExistsAndIsGreaterThanAllButTheLastElement() {
            // Given
            int[] array = {2, 3, 4, 4, 4, 6};
            int key = 5;
            int expected = -6;

            // When
            int actual = binarySearch.searchLast(array, key);

            // Then
            assertEquals(expected, actual);
        }
    }

    @Nested
    class SearchLastInSubArray {

        @Test
        void shouldReturnMinusThree_whenNotExistsInTheSubArrayStartingFromTheThirdElementAndIsSmallerThanAllTheElementsInTheSubArray() {
            // Given
            int[] array = {2, 3, 4, 4, 4, 5};
            int key = 1;
            int expected = -3;

            // When
            int actual = binarySearch.searchLast(array, 2, 6, key);

            // Then
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnMinusFour_whenNotExistsInTheSubArrayEndingAtTheThirdElementAndIsLargerThanAllTheElementsInTheSubArray() {
            // Given
            int[] array = {0, 3, 3, 4, 4, 5};
            int key = 5;
            int expected = -4;

            // When
            int actual = binarySearch.searchLast(array, 1, 3, key);

            // Then
            assertEquals(expected, actual);
        }
    }
}
