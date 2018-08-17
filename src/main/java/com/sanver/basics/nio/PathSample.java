package com.sanver.basics.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class PathSample {

    public static void main(String[] args) {
        String baseDirectory = "src/main/java/com/sanver/basics/nio/";
        // These also work
        // "./src/main/java/com/sanver/basics/nio/"
        // "../basics/src/main/java/com/sanver/basics/nio/"
        // This don't work
        // "/src/main/java/com/sanver/basics/nio/"
        String fileName = "Trial.txt";
        Path filePath = Paths.get(baseDirectory + fileName);
        Path directoryPath = Paths.get(baseDirectory);
        File file = new File(fileName);
        String absolutePathString = file.getAbsolutePath();// To get absolute path as path we must use
        // Paths.get(file.getAbsolutePath())
        Path absolutePath = Paths.get(file.getAbsolutePath());
        System.out.println("file.getAbsolutePath(): " + absolutePathString);
        System.out.println("Paths.get(file.getAbsolutePath()): " + absolutePath);
        System.out.println("path: " + filePath);
        System.out.println("isAbsolute: " + filePath.isAbsolute());
        System.out.println("getFileName: " + filePath.getFileName());
        System.out.println("getFileSystem: " + filePath.getFileSystem());
        System.out.println("getName(0): " + filePath.getName(0));
        System.out.println("getParent: " + filePath.getParent());
        System.out.println("directoryPath.getParent: " + directoryPath.getParent());
        System.out.println("toAbsolutePath: " + filePath.toAbsolutePath());
        System.out.println("toAbsolutePath().getRoot(): " + filePath.toAbsolutePath().getRoot());
        System.out.println("toAbsolutePath().subpath(1, 4): " + filePath.toAbsolutePath().subpath(1, 4));
        System.out.println("toAbsolutePath().getName(0): " + filePath.toAbsolutePath().getName(0));
        System.out.println("getRoot: " + filePath.getRoot());
        System.out.println("subpath(0, 3): " + filePath.subpath(0, 3));
        System.out.println("File name for base directory: " + directoryPath.getFileName());
        System.out.println("filePath.resolve(directoryPath): " + filePath.resolve(directoryPath));
        System.out.println("directoryPath.resolve(filePath): " + directoryPath.resolve(filePath));
        // If we send absolute path to resolve, the absolute path is returned no matter
        // the calling path
        System.out.println("directoryPath.resolve(filePath.toAbsolutePath()): "
                + directoryPath.resolve(filePath.toAbsolutePath()));
        System.out.println("filePath.resolve(directoryPath.toAbsolutePath()): "
                + filePath.resolve(directoryPath.toAbsolutePath()));
        System.out.println("directoryPath.toAbsolutePath().resolve(filePath): "
                + directoryPath.toAbsolutePath().resolve(filePath));
        System.out.println("directoryPath.relativize(directoryPath.getParent()): "
                + directoryPath.relativize(directoryPath.getParent()));
        System.out.println("directoryPath.relativize(directoryPath.getParent().getParent()): "
                + directoryPath.relativize(directoryPath.getParent().getParent()));
        System.out.println("filePath.relativize(directoryPath.getParent().getParent()): "
                + filePath.relativize(directoryPath.getParent().getParent()));
        System.out.println("directoryPath.getParent().getParent().relativize(filePath): "
                + directoryPath.getParent().getParent().relativize(filePath));
        System.out.println("directoryPath.getParent().getParent(): " + directoryPath.getParent().getParent());
        System.out.println(
                "directoryPath.getParent().getParent().resolve(directoryPath.getParent().getParent().relativize(filePath)): "
                        + directoryPath.getParent().getParent()
                        .resolve(directoryPath.getParent().getParent().relativize(filePath)));
        try {
            System.out.println("Files.isSameFile(filePath, filePath.toAbsolutePath()): "
                    + Files.isSameFile(filePath, filePath.toAbsolutePath()));
            System.out.println(Files.list(Paths.get(baseDirectory)).map(p -> p.getFileName().toString())
                    .collect(Collectors.joining(", ", "Files in the base directory: ", "")));// This will also print
            // folders
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
