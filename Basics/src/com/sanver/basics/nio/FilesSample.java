package com.sanver.basics.nio;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class FilesSample {

	public static void main(String[] args) {
		String baseDirectory = "src\\com\\sanver\\basics\\nio\\";
		String fileName = "Trial.txt";
		String newFileName = "Trial.txt.bak";
		Path path = Paths.get(baseDirectory + fileName);
		Path newPath = Paths.get(baseDirectory + newFileName);
		String message = "Selamunaleykum.\n\nThis is Ahmet.";
		String readString;

		// String encoding=System.getProperty("file.encoding"); // This is to get the
		// current file encoding
		Charset charset = Charset.forName("windows-1254");
		// We can also get charset by charSet=StandardCharsets.UTF_8;
		if (!Files.exists(path)) {
			try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
				writer.write(message);
				System.out.println(path.toAbsolutePath() + " generated.");
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		} else
			System.out.println(path.toAbsolutePath() + " exists.");

		if (!Files.exists(path))
			return;

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
			Files.lines(newPath).forEach(System.out::println);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
