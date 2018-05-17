package com.sanver.basics.serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CloningObjectsWithMemorySerialization {

	public static void main(String[] args) {
		Employee employee = new Employee("Ahmet", "Sanver", 11000);
		Employee clone;

		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ObjectOutputStream writer = new ObjectOutputStream(new BufferedOutputStream(outputStream))) {
			writer.writeObject(employee);
			writer.flush();
			try (ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
					ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(inputStream))) {
				clone = (Employee) reader.readObject();
				System.out.println("Clone successful.\nValue:\n" + clone);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
