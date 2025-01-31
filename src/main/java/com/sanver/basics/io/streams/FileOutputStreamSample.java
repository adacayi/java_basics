package com.sanver.basics.io.streams;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamSample {

    public static void main(String[] args) {
        var fileName = "src/main/java/com/sanver/basics/io/streams/TrialOut.txt";
        var message = """
                Writer line 1
                Writer line 2
                
                Writer line 4
                
                """;

        try (FileOutputStream stream = new FileOutputStream(fileName)) { // If the file does not exist, it will be created. It will always overwrite the content if the file exists. If new FileOutputStream(fileName, true) is used instead, the file will be created if it does not exist and if it does then bytes will be written to the end of the file.
            stream.write(message.getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
