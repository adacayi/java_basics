package com.sanver.basics.strings;

import java.time.LocalDate;

public class TextBlocks {
    private static int count = 0;

    public static void main(String[] args) {
        var explanation = """
                Date: %s%n
                    As of Java 15 we have text blocks, which was a preview feature introduced in Java 13.
                Inside the text blocks, we can freely use newlines and quotes without the need for escaping line breaks.
                It allows us to include literal fragments of HTML, JSON, SQL, or whatever we need, in a more elegant and readable way.
                In the resulting String, the (base) indentation and the first newline are not included.
                
                    Luckily, when using text blocks, we can still indent our code properly.
                To achieve that, part of the indentation is treated as the source code while another part of the indentation is seen as a part of the text block.
                To make this work, the compiler checks for the minimum indentation in all non-empty lines.
                Next, the compiler shifts the complete text block to the left.
                
                    Inside text blocks, double-quotes "" don’t have to be escaped.
                We could even use three double-quotes \""" again in our text block by escaping one of them.
                
                    To aid with variable substitution, a new method formatted was added that allows calling the String.format method directly on a String literal:
                
                return \"""
                            Some parameter: %%s
                            \""".formatted(parameter);
                    Note that to escape %% we use %%%%
                Source: https://www.baeldung.com/java-text-blocks
                """.formatted(LocalDate.now()); // %s is for just preserving %s
        System.out.println(explanation);
        System.out.println("\nLines: ");
        explanation.lines().forEach(line -> System.out.printf("%2d- %s%n", ++count, line));
    }
}
