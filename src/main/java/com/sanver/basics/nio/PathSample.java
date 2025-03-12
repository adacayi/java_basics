package com.sanver.basics.nio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class PathSample {

    public static void main(String[] args) {
        //combining strings to a path
        var format = "%-70s : %s%n";
        var basePath = Path.of("src", "main", "java", "com", "sanver", "basics", "nio"); // We can also use Paths.get() introduced in Java 7, but Path.of introduced in Java 11 is more intuitive because it’s a simpler, more direct factory method, aligning with other factory methods introduced in recent Java versions, like List.of(), Set.of(), etc.
        var baseDirectory = "src/main/java/com/sanver/basics/nio/";
        // These also point to the same base path:

        // Path.of("./src/main/java/com/sanver/basics/nio/") // The single dot at the beginning has no effect in here since in file paths, "./" represents the current directory, but Java's Path.of() method normalizes this automatically, treating "./src/..." the same as "src/..."
        // Path.of("../basics/src/main/java/com/sanver/basics/nio/")

        // Below does not point to [current working directory]\src\main\.., it points to D:\src\main\... instead. Call toAbsolutePath() to check.

//        var path2 = Path.of("/src/main/java/com/sanver/basics/nio/");
//        var path3 = Path.of("\\src/main/java/com/sanver/basics/nio/");
        // This is because paths beginning with "/" and "\" are treated as absolute paths from the root directory, not relative to the current working directory.
        // However, isAbsolute() will still return false for Windows based systems, since Windows has a different format for absolute paths that includes a drive letter (like "C:").
        // This behavior is a notable platform-specific detail that developers should be aware of when working with paths in Java, especially in cross-platform applications.

        var fileName = PathSample.class.getSimpleName() + ".java";
        Path filePath = Path.of(baseDirectory, fileName);
        Path directoryPath = Path.of(baseDirectory);
        File file = new File(fileName); // Note that this points to the classPath/fileName, which is D:\...\Basics\PathSample.java, not D:\..\Basics\src\main\...\nio\PathSample.java
        var absolutePathString = file.getAbsolutePath();// To get absolute path as path we can use file.toPath().toAbsolutePath(), Path.of(file.getAbsolutePath()) or Paths.get(file.getAbsolutePath())
        Path absolutePath = Path.of(file.getAbsolutePath());
        System.out.printf(format, "baseDirectory", baseDirectory);
        System.out.printf(format, "fileName", fileName);
        System.out.printf(format, "new File(fileName) ", file);
        System.out.printf(format, "file.getAbsolutePath()", absolutePathString);
        System.out.printf(format, "absolutePath = Path.of(file.getAbsolutePath())", absolutePath);
        System.out.printf(format, "file.toPath().toAbsolutePath()", file.toPath().toAbsolutePath());
        System.out.printf(format, "absolutePath.isAbsolute()", absolutePath.isAbsolute());
        System.out.printf(format, "filePath = Path.of(baseDirectory, fileName)", filePath);
        System.out.printf(format, "filePath.isAbsolute()", filePath.isAbsolute());
        System.out.printf(format, "filePath.getFileName()", filePath.getFileName());
        System.out.printf(format, "filePath.getFileSystem()", filePath.getFileSystem());
        System.out.printf(format, "filePath.getName(0)", filePath.getName(0));
        System.out.printf(format, "filePath.getParent()", filePath.getParent());
        System.out.printf(format, "directoryPath = Path.of(baseDirectory)", directoryPath);
        System.out.printf(format, "directoryPath.getParent()", directoryPath.getParent());
        System.out.printf(format, "filePath.toAbsolutePath()", filePath.toAbsolutePath());
        System.out.printf(format, "filePath.toAbsolutePath().getRoot()", filePath.toAbsolutePath().getRoot());
        System.out.printf(format, "filePath.toAbsolutePath().subpath(1, 4)", filePath.toAbsolutePath().subpath(1, 4)); // This is also a Path object
        System.out.printf(format, "filePath.toAbsolutePath().subpath(1, 4).isAbsolute()", filePath.toAbsolutePath().subpath(1, 4).isAbsolute());
        System.out.printf(format, "filePath.toAbsolutePath().getName(0)", filePath.toAbsolutePath().getName(0)); // This is also a Path object
        System.out.printf(format, "filePath.getRoot()", filePath.getRoot());
        System.out.printf(format, "filePath.subpath(0, 3)", filePath.subpath(0, 3));
        System.out.printf(format, "directoryPath.getFileName()", directoryPath.getFileName());
        System.out.printf(format, "directoryPath.resolve(fileName)", directoryPath.resolve(fileName));
        System.out.printf(format, "directoryPath.getParent().resolve(\"jdbc\")", directoryPath.getParent().resolve("jdbc"));
        System.out.printf(format, "directoryPath.resolveSibling(\"jdbc\")", directoryPath.resolveSibling("jdbc"));   // This is equal to directoryPath.getParent().resolve("jdbc")
        System.out.printf(format, "filePath.resolve(directoryPath)", filePath.resolve(directoryPath));
        System.out.printf(format, "directoryPath.resolve(filePath)", directoryPath.resolve(filePath));
        // If we send absolute path to resolve, the absolute path is returned no matter
        // the calling path
        System.out.printf(format,"directoryPath.resolve(filePath.toAbsolutePath())"
               , directoryPath.resolve(filePath.toAbsolutePath()));
        System.out.printf(format,"filePath.resolve(directoryPath.toAbsolutePath())"
               , filePath.resolve(directoryPath.toAbsolutePath()));
        System.out.printf(format,"directoryPath.toAbsolutePath().resolve(filePath)"
               , directoryPath.toAbsolutePath().resolve(filePath));
        System.out.printf(format,"directoryPath.relativize(directoryPath.getParent())"
               , directoryPath.relativize(directoryPath.getParent()));
        System.out.printf(format,"directoryPath.relativize(directoryPath.getParent().getParent())"
               , directoryPath.relativize(directoryPath.getParent().getParent()));
        System.out.printf(format,"filePath.relativize(directoryPath.getParent().getParent())"
               , filePath.relativize(directoryPath.getParent().getParent()));
        System.out.printf(format,"directoryPath.getParent().getParent().relativize(filePath)"
               , directoryPath.getParent().getParent().relativize(filePath));
        System.out.printf(format,"directoryPath.getParent().getParent()", directoryPath.getParent().getParent());
        System.out.printf(format,
                "directoryPath.getParent().getParent().resolve(directoryPath.getParent().getParent().relativize(filePath))"
                       , directoryPath.getParent().getParent()
                        .resolve(directoryPath.getParent().getParent().relativize(filePath)));
        try {
            System.out.printf(format,"Files.isSameFile(filePath, filePath.toAbsolutePath())",
                    Files.isSameFile(filePath, filePath.toAbsolutePath())); // Tests if two paths locate the same file.
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try (var stream = Files.list(basePath.getParent())) { // It is suggested that we use try-with-resources with this stream
            System.out.println(stream.map(p -> p.getFileName().toString())
                    .collect(Collectors.joining(", ", "Files and directories in %s:%n".formatted(basePath.getParent()), "")));// This will print files and folders, but not content of folders and its subfolders. For that we need Files.walk
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
