package com.sanver.basics.io.streams;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterFileReaderSample {

    public static void main(String[] args) {
        String fileName = "src/main/java/com/sanver/basics/io/streams/FileWriterReaderDemo.txt";
        var file = new File(fileName);
        file.deleteOnExit();
        // Writing to the file
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("This is a demonstration of FileWriter.\n");
            writer.append("FileWriter writes character streams easily.\n")
                    .append("This is the last line.\n");
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error while writing to the file: " + e.getMessage());
        }

        // Reading from the file
        try (FileReader reader = new FileReader(fileName)) {
            int character;
            while ((character = reader.read()) != -1) {
                System.out.print((char) character);
            }
        } catch (IOException e) {
            System.out.println("Error while reading from the file: " + e.getMessage());
        }

        System.out.println();

        // Reading the file in chunks of 10 bytes
        try (FileReader reader = new FileReader(fileName)) {
            char[] buffer = new char[10];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                System.out.print(new String(buffer, 0, bytesRead));
            }
        } catch (IOException e) {
            System.out.println("Error while reading file in chunks: " + e.getMessage());
        }
    }
}
