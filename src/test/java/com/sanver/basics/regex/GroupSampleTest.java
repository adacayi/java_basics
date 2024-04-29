package com.sanver.basics.regex;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupSampleTest {
    private final GroupSample delete = new GroupSample();

    @Nested
    class FindPath {

        @ParameterizedTest
        @CsvSource({
                "www.sancode.co.uk/employees, employees",
                "www.gmail.com/mails/inbox, mails/inbox",
                "www.gmail.com/mails/inbox.au/some.data, mails/inbox.au/some.data",
        })
        void shouldReturnPath_whenValidUrlWithPath(String url, String path) {
            // Given
            // When
            var result = delete.findPath(url);

            // Then
            assertThat(result).isEqualTo(path);
        }

        @Test
        void shouldReturnNull_whenValidUrlWithNoPath() {
            // Given
            var url = "www.gmail.com";

            // When
            var result = delete.findPath(url);

            // Then
            assertThat(result).isNull();
        }

        @ParameterizedTest
        @ValueSource(strings = {
                "www.sancode.co.uk/employees..",
                "www.sancode.co.uk/employees/../something",

        })
        void shouldReturnNull_whenInvalidUrl(String url) {
            // Given
            // When
            var result = delete.findPath(url);

            // Then
            assertThat(result).isNull();
        }
    }
}

