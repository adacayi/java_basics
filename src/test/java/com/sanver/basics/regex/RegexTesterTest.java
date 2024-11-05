package com.sanver.basics.regex;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link com.sanver.basics.regex.RegexTester}
 */
public class RegexTesterTest {
    @Nested
    class Main {
        private final InputStream originalSystemIn = System.in;
        private final PrintStream originalSystemOut = System.out;

        @AfterEach
        void tearDown() {
            System.setIn(originalSystemIn);   // If not set to original, other tests can be affected
            System.setOut(originalSystemOut); // If not set to original, other tests can be affected.
        }

        @Test
        void shouldPrintTheMatchingSections_whenPatternMatches() {
            // Given
            var output = new ByteArrayOutputStream();
            var input = """
                    Selamunaleykum. This is a pattern which is used to test, test for ^ regex ^ patterns.
                    \\w+
                    \n
                    """.replace("\n", System.lineSeparator());
            var inputStream = new ByteArrayInputStream(input.getBytes());
            System.setOut(new PrintStream(output));
            System.setIn(inputStream);

            // When
            RegexTester.main();

            // Then
            var outputString = output.toString();
            outputString = outputString.replace(System.lineSeparator(), "\n");
            assertThat(outputString).isEqualTo("""
                    Enter text for source for regex
                    Enter regex pattern
                    Selamunaleykum. This is a pattern which is used to test, test for ^ regex ^ patterns.
                    ^            ^  ^  ^ ^^ | ^     ^ ^   ^ ^^ ^  ^ ^^ ^  ^  ^  ^ ^ ^   ^   ^   ^      ^
                    Enter regex pattern
                    """);
        }

        @Test
        void shouldProvideSingleGroup_whenPatternMatchesAndSingleGroupsExist() {
            // Given
            var output = new ByteArrayOutputStream();
            var input = """
                    This is a test
                    (\\w+)
                    \n
                    """.replace("\n", System.lineSeparator());
            var inputStream = new ByteArrayInputStream(input.getBytes());
            System.setOut(new PrintStream(output));
            System.setIn(inputStream);

            // When
            RegexTester.main();

            // Then
            var outputString = output.toString();
            outputString = outputString.replace(System.lineSeparator(), "\n");
            assertThat(outputString).isEqualTo("""
                    Enter text for source for regex
                    Enter regex pattern
                    This is a test
                    ^  ^
                    Group: {This}
                    
                    This is a test
                         ^^
                    Group: {is}
                    
                    This is a test
                            |
                    Group: {a}
                    
                    This is a test
                              ^  ^
                    Group: {test}
                    
                    Enter regex pattern
                    """);
        }

        @Test
        void shouldProvideMultipleGroups_whenPatternMatchesAndMultipleGroupsExist() {
            // Given
            var output = new ByteArrayOutputStream();
            var input = """
                    www.google.com www.apple.co.uk
                    (\\b\\w+)\\.(\\w+)\\.([\\w.]+)
                    \n
                    """.replace("\n", System.lineSeparator());
            var inputStream = new ByteArrayInputStream(input.getBytes());
            System.setOut(new PrintStream(output));
            System.setIn(inputStream);

            // When
            RegexTester.main();

            // Then
            var outputString = output.toString();
            outputString = outputString.replace(System.lineSeparator(), "\n");
            assertThat(outputString).isEqualTo("""
                    Enter text for source for regex
                    Enter regex pattern
                    www.google.com www.apple.co.uk
                    ^            ^
                    Groups: {www} {google} {com}
                    
                    www.google.com www.apple.co.uk
                                   ^             ^
                    Groups: {www} {apple} {co.uk}
                    
                    Enter regex pattern
                    """);
        }

        @Test
        void shouldSkipEmptyGroups_whenPatternMatches() {
            // Given
            var output = new ByteArrayOutputStream();
            var input = """
                    This is a test pattern.
                    \\w*
                    \n
                    """.replace("\n", System.lineSeparator());
            var inputStream = new ByteArrayInputStream(input.getBytes());
            System.setOut(new PrintStream(output));
            System.setIn(inputStream);

            // When
            RegexTester.main();

            // Then
            var outputString = output.toString();
            outputString = outputString.replace(System.lineSeparator(), "\n");
            assertThat(outputString).isEqualTo("""
                    Enter text for source for regex
                    Enter regex pattern
                    This is a test pattern.
                    ^  ^ ^^ | ^  ^ ^     ^
                    Enter regex pattern
                    """);
        }

        @Test
        void shouldPrintNoMatch_whenPatternDoesNotMatch() {
            // Given
            var output = new ByteArrayOutputStream();
            var input = """
                    Test pattern
                    \\d+
                    \n
                    """.replace("\n", System.lineSeparator());
            var inputStream = new ByteArrayInputStream(input.getBytes());
            System.setOut(new PrintStream(output));
            System.setIn(inputStream);

            // When
            RegexTester.main();

            // Then
            var outputString = output.toString();
            outputString = outputString.replace(System.lineSeparator(), "\n");
            assertThat(outputString).isEqualTo("""
                    Enter text for source for regex
                    Enter regex pattern
                    No match found
                    Enter regex pattern
                    """);
        }
    }
}
