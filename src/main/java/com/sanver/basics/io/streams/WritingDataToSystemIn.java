package com.sanver.basics.io.streams;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class WritingDataToSystemIn {

    public static void main(String[] args) {
        var message = """
                Writer line 1
                Writer line 2
                
                Writer line 4
                
                """;
        InputStream stream = new ByteArrayInputStream((message).getBytes());
        final InputStream originalSystemIn = System.in;
        try {
            System.setIn(stream);
            try (var scanner = new Scanner(System.in)) {// It is necessary to set the scanner after changing System.in. Else it will still read from the old stream.
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
            }
        } finally {
            System.out.println("Finished");
            System.setIn(originalSystemIn);
        }
    }
}
