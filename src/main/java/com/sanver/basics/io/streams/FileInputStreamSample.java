package com.sanver.basics.io.streams;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInputStreamSample {

    public static void main(String[] args) {
        String fileName = "src/main/java/com/sanver/basics/io/streams/trial.txt";

        try (FileInputStream stream = new FileInputStream(fileName)) {
            int read;

            while ((read = stream.read()) != -1) {
                System.out.print((char) read);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
