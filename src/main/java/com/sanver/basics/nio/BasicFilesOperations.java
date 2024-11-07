package com.sanver.basics.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;

public class BasicFilesOperations {
    public static void main(String[] args) throws IOException {
        var rootPathString = "src/main/java/com/sanver/basics/nio/test";

        var rootPath = Path.of(rootPathString);
        var subDirectories = Path.of(rootPathString, "sub1", "sub2");

        if (Files.notExists(rootPath)) {
            Files.createDirectory(rootPath);// For generating a single directory. It will throw an exception if the directory already exists
        }

        Files.createDirectories(subDirectories); // For generating nested directories. This does not throw an exception if the directories exist.
        createFileWithContent(rootPathString + "/sub1/sub1-file1.txt", "sub1-file1.txt content");
        createFileWithContent(rootPathString + "/sub1/sub1-file2.txt", "sub1-file2.txt content");
        var sub2File1Path = Path.of(rootPathString + "/sub1/sub2/sub2-file1.txt");
        Files.copy(Path.of(rootPathString + "/sub1/sub1-file1.txt"), sub2File1Path, StandardCopyOption.REPLACE_EXISTING); // There is no explicit option to not replace if exists. We need to check file existence ourselves, before copying. If we did not have the StandardCopyOption.REPLACE_EXISTING, it would throw a FileAlreadyExistsException if the file existed in the target path.
        Files.writeString(sub2File1Path, System.lineSeparator() + "sub 2 file content appended", StandardOpenOption.APPEND); // We can have StandardOpenOption.CREATE, StandardOpenOption.APPEND together, meaning if the file does not exist, it will create a new file, and if it exists it will append to it.
        createFileWithContent(rootPathString + "/sub1/sub2-file2.txt", "sub2-file2.txt content");
        Files.move(Path.of(rootPathString + "/sub1/sub2-file2.txt"), Path.of(rootPathString + "/sub1/sub2/sub2-file2.txt"), StandardCopyOption.REPLACE_EXISTING);

        int maxPathLength;

        try (var stream = Files.walk(rootPath)) {
            maxPathLength = stream.mapToInt(path -> path.toString().length()).max().orElse(0);
        }

        var format = "%-" + maxPathLength + "s - %s%n";
        try (var pathStream = Files.walk(rootPath)) {
            pathStream.forEach(path -> {
                if (Files.isDirectory(path)) {
                    System.out.printf(format, path, "Directory");
                } else if (Files.isRegularFile(path)) {
                    System.out.printf(format, path, "File. Content: ");
                    try (var stream = Files.lines(path)) { // It is suggested that we use try-with-resources with this stream
                        stream.forEach(System.out::println); // We can also read all the file content with Files.readString(file), or all the lines with Files.readAllLines. However, reading lines lazily with a stream is better for large files. We can use Files.newBufferedReader(file) as well.
                    } catch (IOException e) {
                        System.out.println("File content cannot be read. Error: " + e);
                    }
                }
            });
        }

        System.out.println("\nDeleting " + rootPathString);
        deleteDirectory(rootPath);
    }

    private static void createFileWithContent(String pathString, String content) throws IOException {
        var file1Path = Path.of(pathString);

        if (Files.notExists(file1Path)) {
            Files.createFile(file1Path);
        }

        Files.writeString(file1Path, content, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private static void deleteDirectory(Path path) {
        try (var stream = Files.walk(path)) {
            stream.sorted(Comparator.reverseOrder()).forEach(x -> {
                System.out.println("Deleting: " + x);
                try {
                    Files.deleteIfExists(x);
                } catch (IOException e) {
                    System.out.println("Could not delete. Error: " + e);
                }
            });
        } catch (IOException e) {
            System.out.println("Could not walk the path. Error: " + e);
        }
    }
}
