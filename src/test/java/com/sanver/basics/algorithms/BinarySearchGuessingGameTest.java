package com.sanver.basics.algorithms;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class BinarySearchGuessingGameTest {
    private final BinarySearchGuessingGame sut = new BinarySearchGuessingGame();

    @Nested
    class Find {
        @ParameterizedTest
        @CsvSource({"2,1", "-3,-4", "100,99"})
        void givenLowerBoundIsGreaterThanUpperBound_find_shouldReturnFalse(int lowerBound, int upperBound) {
            // Given
            try (Scanner scanner = new Scanner(System.in)) {
                // When
                var result = sut.find(lowerBound, upperBound, scanner);

                // Then
                assertThat(result).isFalse();
            }
        }

        @ParameterizedTest
        @ValueSource(strings = {"s\nl\ny\n","S\nl\ny\n","S\nL\nY\n"})
        void givenValidLowerBoundAndUpperBoundAndValueIsInBetween_find_shouldReturnTrue(String input) {
            // Given
            var lowerBound = 5;
            var upperBound = 10;
            var resultOutputStream = new ByteArrayOutputStream();
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            System.setOut(new PrintStream(resultOutputStream));
            try (Scanner scanner = new Scanner(System.in)) {// this part is important. We initialize a Scanner instance after calling System.setIn. If we initialized a scanner before setting System.in, it would not be able to read from the set input.

                // When
                var result = sut.find(lowerBound, upperBound, scanner);

                // Then
                assertThat(result).isTrue();
                var resultOutput = resultOutputStream.toString();
                assertThat(resultOutput).isEqualTo("Is the number 7: ((Y)es, (S)maller, (L)arger)Is the number 5: ((Y)es, (S)maller, (L)arger)Is the number 6: ((Y)es, (S)maller, (L)arger)Number found\r\n");
            }
        }

        @Test
        void givenValidLowerBoundAndUpperBoundAndValueIsNotValid_find_shouldReturnFalse() {
            // Given
            var lowerBound = 5;
            var upperBound = 10;
            var resultOutputStream = new ByteArrayOutputStream();
            System.setIn(new ByteArrayInputStream("s\ns\n,".getBytes()));
            System.setOut(new PrintStream(resultOutputStream));
            try (Scanner scanner = new Scanner(System.in)) {

                // When
                var result = sut.find(lowerBound, upperBound, scanner);

                // Then
                assertThat(result).isFalse();
                var resultOutput = resultOutputStream.toString();
                assertThat(resultOutput).isEqualTo("Is the number 7: ((Y)es, (S)maller, (L)arger)Is the number 5: ((Y)es, (S)maller, (L)arger)Number not found\r\n");
            }
        }

        @Test
        void givenValidLowerBoundAndUpperBoundAndValueIsInBetweenAndSomeInvalidInputs_find_shouldReturnTrue() {
            // Given
            var lowerBound = 5;
            var upperBound = 10;
            var resultOutputStream = new ByteArrayOutputStream();
            System.setIn(new ByteArrayInputStream("s\nl\nasd\nxyz\ny\n,".getBytes()));
            System.setOut(new PrintStream(resultOutputStream));
            try (Scanner scanner = new Scanner(System.in)) {

                // When
                var result = sut.find(lowerBound, upperBound, scanner);

                // Then
                assertThat(result).isTrue();
                var resultOutput = resultOutputStream.toString();
                assertThat(resultOutput).isEqualTo("Is the number 7: ((Y)es, (S)maller, (L)arger)Is the number 5: ((Y)es, (S)maller, (L)arger)Is the number 6: ((Y)es, (S)maller, (L)arger)Invalid input. Please type a valid input.\r\nIs the number 6: ((Y)es, (S)maller, (L)arger)Invalid input. Please type a valid input.\r\nIs the number 6: ((Y)es, (S)maller, (L)arger)Number found\r\n");
            }
        }
    }
}
