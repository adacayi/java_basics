package com.sanver.basics.io.streams;

import com.sanver.basics.io.streams.ObjectOutputStreamSample.Person;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.IntStream;

import static com.sanver.basics.io.streams.ObjectOutputStreamSample.generatePerson;

public class ObjectOutputStreamWithAppend {
    public static void main(String[] args) {
        var people1 = IntStream.range(1, 4).mapToObj(i -> generatePerson()).toList();
        var people2 = IntStream.range(4, 6).mapToObj(i -> generatePerson()).toList();
        var file = new File("temp.dat");
        file.deleteOnExit();

        // Append two batches of data into the file
        serializeToFile(file, people1); // First batch (new file or overwrite)
        serializeToFile(file, people2); // Second batch (append)

        // Read all objects from the file
        deserializeFromFile(file);
    }

    // Method to serialize objects into the file
    static void serializeToFile(File file, List<Person> people) {
        try (
                var fos = new FileOutputStream(file, true);
                /* Even though the `FileOutputStream` is opened with append mode, the ObjectOutputStream writes a new stream header every time it is created.
                As a result, any new objects written to the file will corrupt the existing serialized content, making it unreadable.
                To avoid writing a new header, a custom subclass of `ObjectOutputStream` can be used, overriding the writeStreamHeader() method to do nothing when appending to an existing file.
                 */
                var oos = file.exists() && file.length() > 0
                        ? new AppendingObjectOutputStream(fos) // Custom stream if appending
                        : new ObjectOutputStream(fos) // Normal stream if new file or empty
        ) {
            for (Person person : people) {
                oos.writeObject(person);
            }
            System.out.println("Serialized " + people.size() + " people to file."); // It will be flushed after the close method is invoked for oos.
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to read all objects from the file
    public static void deserializeFromFile(File file) {
        try (var fis = new FileInputStream(file);
             var ois = new ObjectInputStream(fis)) {

            System.out.println("Deserialized objects from file:");
            while (fis.available() > 0) {
                Person person = (Person) ois.readObject();
                System.out.println(person);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // Custom ObjectOutputStream to prevent writing a new stream header
    public static class AppendingObjectOutputStream extends ObjectOutputStream {
        public AppendingObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() {
            // Do nothing here so that we don't write a duplicate header
        }
    }
}
