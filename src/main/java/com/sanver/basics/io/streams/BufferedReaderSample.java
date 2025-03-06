package com.sanver.basics.io.streams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedReaderSample {

    public static void main(String[] args) {
        var fileName = "src/main/java/com/sanver/basics/io/streams/Trial.txt";

        try (FileInputStream fis = new FileInputStream(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(fis, "Windows-1254");
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            // Note that both InputStreamReader and BufferedReader extend abstract Reader class, but only BufferedReader supports mark.
            System.out.println("inputStreamReader.markSupported() : " + inputStreamReader.markSupported());
            System.out.println("bufferedReader.markSupported()    : " + bufferedReader.markSupported());
            var file = new File(fileName); // This is just to get the file length.
            bufferedReader.mark((int) file.length() + 1); // Marks the present position in the stream.
            // Subsequent calls to reset() will attempt to reposition the stream to this point.
            // readAheadLimit – Limit on the number of characters that may be read while still preserving the mark.
            // An attempt to reset the stream after reading characters up to this limit or beyond may fail.
            bufferedReader.lines().forEach(System.out::println);
            printFinishedAndReset(bufferedReader);

            char[] chunk = new char[10];
            int len;
            while ((len = bufferedReader.read(chunk)) != -1) {
                System.out.print(new String(chunk, 0, len));
            }

            printFinishedAndReset(bufferedReader);

            while ((len = bufferedReader.read(chunk, 5, 3)) != -1) { // Read the characters, and writing them to the array chunk starting from offset 5, with length 3 and returning the length of the characters read.
                System.out.print(new String(chunk, 5, len));
            }

            printFinishedAndReset(bufferedReader);

            int i;
            while ((i = bufferedReader.read()) != -1) {
                System.out.print((char) i);
            }

            printFinishedAndReset(bufferedReader);

            while (bufferedReader.ready()) { // Tells whether this stream is ready to be read.
                // A buffered character stream is ready if the buffer is not empty, or if the underlying character stream is ready.
                System.out.print((char) bufferedReader.read());
            }

            printFinishedAndReset(bufferedReader);

            // inputStreamReader.ready() will return false after the initial read and bufferedReader.reset() won't change it.
//            inputStreamReader.reset(); // This won't work as well, since InputStreamReader does not support mark and reset, and it will throw an IOException.
            while (inputStreamReader.ready()) { // Tells whether this stream is ready to be read.
                // An InputStreamReader is ready if its input buffer is not empty, or if bytes are available to be read from the underlying byte stream.
                System.out.print((char) bufferedReader.read());
            }

            printFinishedAndReset(bufferedReader);

            while (fis.available() > 0) { // This won't work. fis.available() will become zero after the initial read and bufferedReader.reset() won't change it.
                System.out.print((char) bufferedReader.read());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printFinished() {
        System.out.printf("Finished reading%n%n");
    }

    private static void printFinishedAndReset(BufferedReader reader) throws IOException {
        printFinished();
        reader.reset(); // Resets the stream to the most recent mark.
        System.out.printf("Resetting BufferedReader to initial position%n%n");
    }
}
