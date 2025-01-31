package com.sanver.basics.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ExternalSerializationAndDeserializationSample {

    public static void main(String[] args) {
        var path = Path.of("src", "main", "java", "com", "sanver", "basics", "serialization", "SerializedEmployeeExternal.txt");
        var employee = new EmployeeExternalizable("Ahmet", "Sanver", 9000.4);

        try (OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE); // If the file does not exist, it will be created. If the file already exists, it will not be truncated and will simply open the file for writing.

             // Using Files.newOutputStream instead of FileOutputStream offers better flexibility and advantages:
             // 1. Enhanced configuration options: Files.newOutputStream allows specifying multiple options
             //    via StandardOpenOption (e.g., CREATE, CREATE_NEW, APPEND, TRUNCATE_EXISTING).
             // 2. Better integration with NIO APIs: It works seamlessly with Path and other features of
             //    java.nio.file (e.g., FileVisitor, WatchService).
             // 3. Granular error handling: Files API produces more specific exceptions such as NoSuchFileException
             //    or AccessDeniedException, providing better clarity about issues compared to generic exceptions
             //    in FileOutputStream.
             // 4. Suitable for modern file systems: It supports non-standard file systems (e.g., zip files, cloud storage)
             //    via custom FileSystemProviders.
             // 5. More scalable and future-proof: Files API is built for advanced use cases like asynchronous I/O
             //    or symbolic links.
             ObjectOutputStream oos = new ObjectOutputStream(out)) {
            employee.writeExternal(oos);
            System.out.println("Object serialized to file " + path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            byte[] fileBytes = Files.readAllBytes(path);
            System.out.printf("%nSerialized file content (hex)%n%n");
            for (byte b : fileBytes) {
                System.out.printf("%02X ", b);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        EmployeeExternalizable deserializedEmployee = new EmployeeExternalizable(null, null, 0);
        // We generate an empty object and then call its readExternal method to fill the values.

        try (InputStream in = Files.newInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(in)) {
            deserializedEmployee.readExternal(ois);
            System.out.printf("%nObject deserialized.%nValue: %n");
            System.out.println(deserializedEmployee);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
