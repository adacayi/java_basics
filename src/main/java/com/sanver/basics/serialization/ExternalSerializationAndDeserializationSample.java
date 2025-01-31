package com.sanver.basics.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static com.sanver.basics.io.streams.ObjectOutputStreamWithAppend.AppendingObjectOutputStream;

public class ExternalSerializationAndDeserializationSample {

    public static void main(String[] args) {
        var path = Path.of("src", "main", "java", "com", "sanver", "basics", "serialization", "SerializedEmployeeExternal.txt");

        try (OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND); // StandardOpenOption.CREATE: If the file does not exist, it will be created. If the file already exists, it will not be truncated and will simply open the file for writing and write position defaults to the beginning of the file. StandardOpenOption.APPEND will open the file and move the cursor to the end.

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
             ObjectOutputStream oos = path.toFile().exists() && path.toFile().length() > 0 ? new AppendingObjectOutputStream(out) : new ObjectOutputStream(out)) { // A custom ObjectOutputStream is needed for append to prevent writing stream header. Check ObjectOutputStreamWithAppend for more explanation.
            int count = getEmployeeCount(path);
            count++;
            var employee = new EmployeeExternalizable("Person " + count, "Surname " + count, 1000.25 * count);
            employee.writeExternal(oos);
            System.out.println("Object serialized to file " + path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        readByteContent(path);

        EmployeeExternalizable deserializedEmployee = new EmployeeExternalizable(); // No args constructor is not necessary in the first place. We added no args constructor only for getting an instance of this class without needing to specify any value, since deserialization will do that for us.
        // We generate an empty object and then call its readExternal method to fill the values.

        try (InputStream in = Files.newInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(in)) {
            System.out.printf("%n%nObject deserialized%n%nValues: %n");
            for (int i = 1; in.available() > 0; i++) {
                deserializedEmployee.readExternal(ois);
                System.out.printf("%2d-  %s%n", i, deserializedEmployee);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void readByteContent(Path path) {
        try {
            byte[] fileBytes = Files.readAllBytes(path);
            System.out.printf("%nSerialized file content (hex)%n%n");
            for (byte b : fileBytes) {
                System.out.printf("%02X ", b);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static int getEmployeeCount(Path path) {
        var employee = new EmployeeExternalizable("","",0.0);
        try (InputStream in = Files.newInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(in)) {
            int i = 0;
            for (;in.available() > 0; i++) {
                employee.readExternal(ois);
            }
            return i;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
