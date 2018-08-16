package com.sanver.basics.io.streams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedReaderSample {

    public static void main(String[] args) {
        String fileName = "src/main/java/com/sanver/basics/io/streams/Trial.txt";

        try (FileInputStream stream = new FileInputStream(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(stream, "Windows-1254");
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            StringBuffer buffer = new StringBuffer();
            int length;
            reader.lines().forEach(l -> buffer.append(l + "\n"));

            length = buffer.length();

            if (length != 0)
                buffer.deleteCharAt(length - 1);

            System.out.println(buffer);
            System.out.println("Finished reading.");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
