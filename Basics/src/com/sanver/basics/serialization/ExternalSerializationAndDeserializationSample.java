package com.sanver.basics.serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ExternalSerializationAndDeserializationSample {

	public static void main(String[] args) {
		String file = "src\\com\\sanver\\basics\\serialization\\SerializedEmployeeExternal.txt";
		Path path = Paths.get(file);
		EmployeeExternalizable employee = new EmployeeExternalizable("Ahmet", "Sanver", 9000.4);

		try (OutputStream stream = Files.newOutputStream(path, StandardOpenOption.CREATE);
				BufferedOutputStream buffer = new BufferedOutputStream(stream);
				ObjectOutputStream writer = new ObjectOutputStream(buffer)) {
			employee.writeExternal(writer);
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

		EmployeeExternalizable deserializedEmployee = new EmployeeExternalizable(null, null, 0); // We generate an empty
																									// object and then
																									// call its
																									// readExternal
																									// method to fill
																									// the values.

		try (InputStream stream = Files.newInputStream(path);
				BufferedInputStream buffer = new BufferedInputStream(stream);
				ObjectInputStream reader = new ObjectInputStream(buffer)) {
			deserializedEmployee.readExternal(reader);
			System.out.println("Object deserialized.\nValue: \n");
			System.out.println(deserializedEmployee);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
