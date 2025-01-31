package com.sanver.basics.io.streams;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Demonstrates the differences between {@link java.io.DataOutputStream} /
 * {@link java.io.DataInputStream} and {@link java.io.ObjectOutputStream} /
 * {@link java.io.ObjectInputStream}.
 *
 * <h2>Key Differences</h2>
 * <ul>
 *  <li>
 *      <b>Purpose:</b>
 *      <ul>
 *          <li>{@code DataOutputStream}/ {@code DataInputStream}:
 *          These streams are designed to write and read primitive data types
 *          (e.g., int, float, boolean) and strings in binary format. They provide
 *          low-level control for working with structured binary data.</li>
 *          <li>{@code ObjectOutputStream}/ {@code ObjectInputStream}:
 *          These streams are designed for serialization and deserialization of Java objects.
 *          They allow entire objects to be written and reconstructed with their state.</li>
 *      </ul>
 *  </li>
 *  <li>
 *      <b>Serialization:</b>
 *      <ul>
 *          <li>{@code DataOutputStream}/ {@code DataInputStream}: These do not
 *          support directly writing objects. You must manually write and read individual
 *          fields of an object (as primitive types).</li>
 *          <li>{@code ObjectOutputStream}/ {@code ObjectInputStream}: These support
 *          object serialization, allowing an entire object's state to be saved or restored.
 *          The objects must implement the {@link java.io.Serializable} interface.</li>
 *      </ul>
 *  </li>
 *  <li>
 *      <b>Flexibility:</b>
 *      <ul>
 *          <li>{@code DataOutputStream}/ {@code DataInputStream}: You work directly with
 *          primitive types and can define your own structured file format. Requires you to ensure
 *          the read/write order and structure are consistent.</li>
 *          <li>{@code ObjectOutputStream}/ {@code ObjectInputStream}: Object serialization
 *          is simpler and automatically manages the structure. However, it is limited to
 *          serializable objects and may not support all field types (e.g., transient fields).</li>
 *      </ul>
 *  </li>
 *  <li>
 *      <b>Performance:</b>
 *      <ul>
 *          <li>{@code DataOutputStream}/ {@code DataInputStream}: Generally more efficient
 *          as no metadata (like class type information) is stored. Only raw binary data is written.</li>
 *          <li>{@code ObjectOutputStream}/ {@code ObjectInputStream}: Introduces overhead
 *          as metadata about the object's state, type, and structure are written for deserialization.</li>
 *      </ul>
 *  </li>
 *  <li>
 *      <b>Use Cases:</b>
 *      <ul>
 *          <li>{@code DataOutputStream}/ {@code DataInputStream}: Preferred when working
 *          with specific binary data formats, rigid data structures, or when size/performance
 *          is critical.</li>
 *          <li>{@code ObjectOutputStream}/ {@code ObjectInputStream}: Preferred when dealing with
 *          complete objects or object hierarchies, such as saving application state or transmitting
 *          objects over a network.</li>
 *      </ul>
 *  </li>
 * </ul>
 *
 * <h2>Comparison Example</h2>
 * <p>This example compares how to write and read data using both approaches:</p>
 *
 * <h3>Using DataOutputStream and DataInputStream</h3>
 * <pre>{@code
 * try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.bin"))) {
 *     dos.writeInt(42);
 *     dos.writeFloat(3.14f);
 * } catch (IOException e) {
 *     e.printStackTrace();
 * }
 *
 * try (DataInputStream dis = new DataInputStream(new FileInputStream("data.bin"))) {
 *     int intValue = dis.readInt();
 *     float floatValue = dis.readFloat();
 *     System.out.println("Int: " + intValue + ", Float: " + floatValue);
 * } catch (IOException e) {
 *     e.printStackTrace();
 * }
 * }</pre>
 *
 * <h3>Using ObjectOutputStream and ObjectInputStream</h3>
 * <pre>{@code
 * class Data implements Serializable {
 *     int intValue = 42;
 *     float floatValue = 3.14f;
 * }
 *
 * try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.bin"))) {
 *     oos.writeObject(new Data());
 * } catch (IOException e) {
 *     e.printStackTrace();
 * }
 *
 * try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.bin"))) {
 *     Data data = (Data) ois.readObject();
 *     System.out.println("Int: " + data.intValue + ", Float: " + data.floatValue);
 * } catch (IOException | ClassNotFoundException e) {
 *     e.printStackTrace();
 * }
 * }</pre>
 *
 * <h2>Conclusion</h2>
 * <p>
 * Use {@code DataOutputStream} and {@code DataInputStream} when working with primitive
 * data and binary formats. Opt for {@code ObjectOutputStream} and {@code ObjectInputStream}
 * when working with serializable objects, where simplicity in object persistence is desired.
 * </p>
 */
public class ObjectOutputStreamSample {
    private static final Random random = new Random();

    public static void main(String[] args) {

        var path = Path.of("src", "main", "java", "com", "sanver", "basics", "io", "stream");
        var file = path.resolve("data.dat").toFile();
        file.deleteOnExit();
        try (var out = new FileOutputStream(file);
             var in = new FileInputStream(file);
             var oos = new ObjectOutputStream(out);
             var ois = new ObjectInputStream(in)
        ) {
            oos.writeByte(5);
            oos.writeShort(15_234);
            oos.writeInt(1_000_000);
            oos.writeLong(1_000_000_000_000L);
            oos.writeChar(65);
            oos.writeFloat(1.23456789f);
            oos.writeDouble(1.23456789);
            oos.writeBoolean(true);
            oos.writeObject(generatePerson());
            oos.flush();
            System.out.println(ois.readByte());
            System.out.println(ois.readShort());
            System.out.println(ois.readInt());
            System.out.println(ois.readLong());
            System.out.println(ois.readChar());
            System.out.println(ois.readFloat());
            System.out.println(ois.readDouble());
            System.out.println(ois.readBoolean());
            Person person = (Person) ois.readObject();
            System.out.println(person);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    static Person generatePerson() {
        var id = random.nextInt(1, 1000);
        var age = random.nextInt(1, 91);
        var addresses = IntStream.range(1, random.nextInt(2, 4)).mapToObj(i -> new Address("Street " + random.nextInt(1, 101))).toArray(Address[]::new);
        return new Person("Person " + id, age, addresses);
    }

    record Person(String name, int age, Address[] addresses) implements Serializable {
        // Both Person and all objects referenced within the object graph of Person,
        // including nested or referenced objects, must implement Serializable for the
        // serialization process to work without issues. If any non-serializable object
        // exists in the graph, a NotSerializableException will be thrown.

        //We override the equals and hashcode methods because Sonar suggests "Override equals and hashCode to consider array's content in the method" for Address array and wanted us to use Arrays.equals() instead.
        // Because of that hashCode also needs to be overwritten so that object returning true for equals() should have the same hashCode.
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && name.equals(person.name) && Arrays.equals(addresses, person.addresses);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, Arrays.hashCode(addresses));
        }

        @Override
        public String toString() {
            return "Person[name=" + name + ", age=" + age + ", addresses=" + Arrays.toString(addresses) + "]";
        }
    }

    record Address(String street) implements Serializable {
    }
}
