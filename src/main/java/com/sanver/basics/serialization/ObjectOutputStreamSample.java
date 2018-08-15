package com.sanver.basics.serialization;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ObjectOutputStreamSample {
	public static void main(String[] args) {
		String file = "src\\com\\sanver\\basics\\serialization\\SerializedEmployee.txt";
		Path path = Paths.get(file);
		Employee employee = new Employee("Ahmet", "Sanver", 9000.4);
		try (OutputStream stream = Files.newOutputStream(path, StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING); // If we do not define OpenOptions, the default behaviour is
														// CREATE and TRUNCATE_EXISTING, which creates a new file if the
														// file does not exist, or truncate the file if it exists.
				BufferedOutputStream buffer = new BufferedOutputStream(stream);
				ObjectOutputStream writer = new ObjectOutputStream(buffer)) {
			writer.writeObject(employee);
			System.out.println("Object serialized to file " + file);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Serialized file content: \n");

		// We cannot get the file content with the various methods in
		// Files in java.nio.file. It throws exception.
		// Hence we used java.io classes.

		try (FileInputStream stream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(stream, "Windows-1254");
				BufferedReader reader = new BufferedReader(inputStreamReader);) {
			reader.lines().forEach(System.out::println);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
