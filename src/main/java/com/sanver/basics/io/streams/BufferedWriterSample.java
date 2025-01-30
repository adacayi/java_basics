package com.sanver.basics.io.streams;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BufferedWriterSample {

    public static void main(String[] args) {
        String fileName = "src/main/java/com/sanver/basics/io/streams/TrialOut.txt";
        String message = """
                Writer line 1
                Writer line 2
                
                Writer line 4
                
                """;

        try (var out = new FileOutputStream(fileName); // If the file does not exist, it will be created. It will always overwrite the content if the file exists. If new FileOutputStream(fileName, true) is used instead, the file will be created if it does not exist and then bytes will be written to the end of the file.
             var outputStreamWriter = new OutputStreamWriter(out, "Windows-1254");
             var writer = new BufferedWriter(outputStreamWriter);
             var in = new FileInputStream(fileName);
             var inputStreamReader = new InputStreamReader(in, "Windows-1254");
             var reader = new BufferedReader(inputStreamReader)) {
            writer.write(message);
            writer.flush(); // Flushes the stream, so the content is written to the file.
            reader.lines().forEach(System.out::println);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
