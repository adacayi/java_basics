package com.sanver.basics.io.streams;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterFileReaderSample {

    public static void main(String[] args) {
        String fileName = "src/main/java/com/sanver/basics/io/streams/FileWriterReaderDemo.txt";
        var file = new File(fileName); // Note that file is not created here, but when new FileWriter(fileName) is called.
        file.deleteOnExit();
        // Writing to the file
        try (FileWriter writer = new FileWriter(fileName)) { // If file does not exist, it will be created.
            // We can also use new FileWriter(file).
            // new FileWriter(file, true) can be used for appending to an existing file (A new file will be created if the file does not exist).
            // It will throw an IOException if the named file exists but is a directory rather than a regular file,
            // does not exist but cannot be created, or cannot be opened for any other reason.
            writer.write("This is a demonstration of FileWriter.\n");
            writer.append("FileWriter writes character streams easily.\n").append("This is the last line.\n"); // append behaves in exactly the same way as the invocation write(), but also returns back the writer.
            // To actually append to the end of a file, use the constructor new FileWriter(file, true)
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error while writing to the file: " + e.getMessage());
        }

        // Reading from the file
        try (FileReader reader = new FileReader(fileName)) { // We can also use new FileReader(file).
            // It will throw an FileNotFoundException if the named file does not exist,
            // is a directory rather than a regular file, or for some other reason cannot be opened for reading.
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
