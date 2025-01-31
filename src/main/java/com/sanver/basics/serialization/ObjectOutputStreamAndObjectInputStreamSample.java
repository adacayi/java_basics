package com.sanver.basics.serialization;

import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * Check also {@link com.sanver.basics.io.streams.ObjectOutputStreamSample} and {@link com.sanver.basics.io.streams.ObjectOutputStreamWithAppend}
 * for writing primitive values and appending to an existing serialization.
 */
public class ObjectOutputStreamAndObjectInputStreamSample {
    public static void main(String[] args) {
        var path = Path.of("src", "main", "java", "com", "sanver", "basics", "serialization", "SerializedEmployee.txt");
        var employee = new Employee("Ahmet", "Sanver", 9000.4, new Address[]{new Address("Street 1"), new Address("Street 2")});
        try (OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING); // If no options are present then this method works as if the CREATE, TRUNCATE_EXISTING, and WRITE options are present. In other words, it opens the file for writing, creating the file if it doesn't exist, or initially truncating an existing regular-file to a size of 0 if it exists.
             ObjectOutputStream writer = new ObjectOutputStream(out)) {
            writer.writeObject(employee);
            System.out.println("Object serialized to file " + path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.printf("%nSerialized file content. Since salary is defined as transient in Employee class, its value is not stored. %n");

        try (var in = Files.newInputStream(path);
             var ois = new ObjectInputStream(in)) {
            var read = (Employee) ois.readObject();
            System.out.println(read);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }}
