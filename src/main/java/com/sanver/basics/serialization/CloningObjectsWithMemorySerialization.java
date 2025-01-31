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
		var employee = new Employee("Person 1", "Surname 1", 10_000, new Address[]{new Address("Street 1")});
		try (var out = new ByteArrayOutputStream();
			 var bufOut = new BufferedOutputStream(out); // This is just to show how to implement serialization with buffering. While BufferedOutputStream can provide performance benefits when dealing with larger objects or when performing multiple write operations, since we're only serializing a single object and working entirely in memory (using ByteArrayOutputStream) not dealing with disk I/O or network operations, the buffering overhead might actually be more than the potential benefit in this specific scenario.
			 var oos = new ObjectOutputStream(bufOut)) {
			oos.writeObject(employee);
			oos.flush();
			try(var in = new ByteArrayInputStream(out.toByteArray());
			var bufIn = new BufferedInputStream(in); // This is just to show how to implement deserialization with buffering. While BufferedInputStream can provide performance benefits when dealing with larger objects or when performing multiple write operations, since we're only serializing a single object and working entirely in memory (using ByteArrayOutputStream) not dealing with disk I/O or network operations, the buffering overhead might actually be more than the potential benefit in this specific scenario.
			var ois = new ObjectInputStream(bufIn)){
				var employee2 = (Employee) ois.readObject();
				System.out.println(employee);
				System.out.println(employee2);
				employee2.getAddresses()[0].setStreet("Street 2");
				System.out.println(employee2);
			}
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
    }
}
