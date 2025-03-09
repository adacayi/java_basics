package com.sanver.basics.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * This class demonstrates the usage of the {@link PrintWriter} class in Java.
 * <p>
 * {@link PrintWriter} is a character-stream class used for writing formatted
 * output to a text-output stream. Unlike the {@link java.io.OutputStream}
 * classes that work with byte streams, {@link PrintWriter} works with
 * character streams, making it suitable for writing text data.
 * </p>
 * <p>
 * Key features of {@link PrintWriter}:
 * <ul>
 *   <li><b>Formatting:</b> It provides methods like {@code print()},
 *   {@code println()}, and {@code printf()} for writing formatted text,
 *   similar to how you would use {@code System.out.print()} and
 *   {@code System.out.println()}.</li>
 *   <li><b>Auto-flushing:</b> It can be set to automatically flush the
 *   stream after each line break, ensuring data is immediately written to
 *   the underlying stream.</li>
 *   <li><b>Convenience:</b> It simplifies writing text data compared to
 *   working directly with raw character streams.</li>
 *   <li><b>Error Handling:</b> It can handle errors and exceptions
 *   internally, providing a cleaner way to manage potential issues.</li>
 * </ul>
 * </p>
 * <p>
 * Common use cases include:
 * <ul>
 *   <li>Writing log files</li>
 *   <li>Generating text-based reports</li>
 *   <li>Writing configuration files</li>
 *   <li>Outputting data to the console or network streams</li>
 * </ul>
 * </p>
 */
public class PrintWriterSample {

    public static void main(String[] args) {
        // Demonstrate PrintWriter with different constructors and methods.
        try {
            // 1. PrintWriter to a file with auto-flushing.
            File outputFile = new File("output.txt");
            outputFile.deleteOnExit();
            PrintWriter writerToFile = new PrintWriter(outputFile, StandardCharsets.UTF_8); // a new PrintWriter, without automatic line flushing, with the specified file and charset is created. The file is created, if it doesn't exist. Note that all overloads with file is without auto-flushing.
//            PrintWriter writerToFile = new PrintWriter(new FileOutputStream(outputFile), true, StandardCharsets.UTF_8); // a new PrintWriter with automatic line flushing and the specified charset is created.

            // Write some text to the file.
            writerToFile.println("This is the first line written to the file.");
            writerToFile.print("This is the second line without a line break.");
            writerToFile.println(" And this is the rest of the second line.");
            writerToFile.printf("The value of pi is approximately: %.2f%n", Math.PI);
            writerToFile.flush(); //Ensure that everything in buffer is written to file.
            writerToFile.close();

            // 2. PrintWriter to the console (System.out) with no auto-flushing.
            PrintWriter writerToConsole = new PrintWriter(System.out, false);// PrintWriter(OutputStream out, boolean autoFlush): Creates a new PrintWriter.
            // Write some text to the console.
            writerToConsole.println("This is a line written to the console.");
            writerToConsole.print("This is another line without a line break.");
            writerToConsole.println(" And the rest of the line.");
            writerToConsole.flush();
            writerToConsole.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Output file not found: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error: Unsupported Encoding: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: IOException: " + e.getMessage());
        }
    }
}