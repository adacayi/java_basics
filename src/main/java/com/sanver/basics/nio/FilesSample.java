package com.sanver.basics.nio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FilesSample {

    public static void main(String[] args) {
        String baseDirectory = "src/main/java/com/sanver/basics/nio/";
        String fileName = "Trial.txt";
        String newFileName = "Trial.txt.bak";
        Path path = Paths.get(baseDirectory, fileName);
        Path newPath = Paths.get(baseDirectory, newFileName);
        List<String> messages = List.of("Selamunaleykum.", "This is Ahmet.");
        String readString;

        // String encoding=System.getProperty("file.encoding"); // This is to get the
        // current file encoding
        Charset charset = Charset.forName("windows-1254");
        // We can also get charset by charSet=StandardCharsets.UTF_8;

        try (BufferedWriter writer = Files.newBufferedWriter(path, charset, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE)) {
            for (var message : messages) {
                writer.write(message);
                writer.newLine();
            }

            System.out.println(path.toAbsolutePath() + " generated.");
        } catch (IOException e) {
            System.out.println("Error occurred while writing file content. Error: " + e.getMessage());
        }

        if (Files.notExists(path)) {
            return;
        }

        try {
            System.out.println("File size is " + Files.size(path) + " bytes.");
            Files.copy(path, newPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File copied to " + newPath.toAbsolutePath());
            System.out.println("\nCopied file content:\n");

            try (BufferedReader reader = Files.newBufferedReader(newPath)) {
                while ((readString = reader.readLine()) != null) {
                    System.out.println(readString);
                }
            }

            System.out.println("\nSimpler file content reading:\n");
            Files.readAllLines(newPath).forEach(System.out::println);
            System.out.println("\nContent reading with stream:\n");

            try (var stream = Files.lines(newPath)) { // It is recommended to use this stream with try-with-resources
                stream.forEach(System.out::println);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
