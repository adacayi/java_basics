package com.sanver.basics.serialization;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ObjectInputStreamSample {

	public static void main(String[] args) {
		String file = "src\\com\\sanver\\basics\\serialization\\SerializedEmployee.txt";
		Path path = Paths.get(file);
		Employee employee;

		try (InputStream stream = Files.newInputStream(path);
				BufferedInputStream buffer = new BufferedInputStream(stream);
				ObjectInputStream reader = new ObjectInputStream(buffer)) {
			employee = (Employee) reader.readObject();
			System.out.println(
					"Object deserialized.\nSince salary is defined as transient in Employee class, its value is not stored.\nValue: \n");
			System.out.println(employee);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
