package com.sanver.basics.io.streams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BufferedReaderSample {

    public static void main(String[] args) {
        var fileName = "src/main/java/com/sanver/basics/io/streams/Trial.txt";

        try (FileInputStream stream = new FileInputStream(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(stream, "Windows-1254");
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            var file = new File(fileName); // This is just to get the file length.
            reader.mark((int) file.length() + 1); // Marks the present position in the stream. Subsequent calls to reset() will attempt to reposition the stream to this point. readAheadLimit – Limit on the number of characters that may be read while still preserving the mark. An attempt to reset the stream after reading characters up to this limit or beyond may fail.
            reader.lines().forEach(System.out::println);
            System.out.printf("Finished reading%n%n");
            System.out.println("Resetting BufferedReader to initial position");
            reader.reset(); // Resets the stream to the most recent mark.
            char[] chunk = new char[10];
            int len;
            var chunkLength = chunk.length;
            while ((len = reader.read(chunk, 0, chunkLength)) != -1) {
                var content = len == chunkLength ? chunk : Arrays.copyOf(chunk, len);
                System.out.print(new String(content));
            }
            System.out.printf("Finished reading%n%n"); // Note that this is printed just after the last line content, which does not have a new line at the end.
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
