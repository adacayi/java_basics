package com.sanver.basics.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * <h3>Key Points</h3>
 * <ol>
 *     <li>
 *     Developers can change how a particular class is serialized by implementing two methods inside the class file. These methods are:
 *     <pre>
 *         {@code
 *          private void writeObject(ObjectOutputStream out) throws IOException;
 *          private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException;
 *         }
 *     </pre>
 * Notice that both methods are (and must be) declared private, proving that neither method is inherited and overridden or overloaded.
 * The trick here is that the virtual machine will automatically check to see if either method is declared during the corresponding method call.
 * The virtual machine can call private methods of your class whenever it wants but no other objects can.
 * Thus, the integrity of the class is maintained and the serialization protocol can continue to work as normal.
 * </li>
 * <li>
 * If an object is being deserialized, that means the class of that object implements Serializable.
 * Therefore, its constructor will never be called. However, constructor for the super class may be invoked if the super class does not implement serializable interface.
 * While deserializing a record, a new record object is created by invoking the record class’s canonical constructor, passing values deserialized from the stream as arguments to the canonical constructor.
 * This is secure because it means the record class can validate the values before assigning them to fields,
 * just like when an ordinary Java program creates a record object via new.
 * </li>
 * <li>
 *     If the object graph contains non-serializable objects, an exception is thrown and nothing is serialized. Object graph means all the objects that are linked/referenced by the first object (directly or indirectly) that is being serialized.
 *     Fields of an Object that are marked as transient are not serialized and so they do not cause an exception. Any field that is not marked transient but points to an object of a class that does not implement Serializable, will cause an exception to be thrown.
 * </li>
 * <li>
 *     If a serializable class extends a non-serializable class, the base classes members are not serialized and when deserializing the default constructor of the non-serializable base class will be called (if that does not exist, it will result in a runtime error).
 *     The base class members will be assigned to their default values.
 *     <pre>
 *         {@code
 *             public static void main(String[] args) {
 *         var path = Path.of("serialize.txt");
 *         try (var fos = Files.newOutputStream(path);
 *              var fis = Files.newInputStream(path);
 *         var oos = new ObjectOutputStream(fos);
 *         var ois = new ObjectInputStream(fis)) {
 *             oos.writeObject(new B("b1",15));
 *             oos.flush();
 *             var b = (B) ois.readObject();
 *             System.out.println(b);
 *         } catch (IOException | ClassNotFoundException e) {
 *             throw new RuntimeException(e);
 *         }
 *     }
 *
 *     static class A {
 *         String name;
 *
 *         public A() { // Since A is not serializable, when deserializing B, default constructor of A is needed. If this does not exist, it will result in a runtime error in deserialization. java.io.InvalidClassException: B; no valid constructor
 *         // Note that, serialization will work without an issue, even this constructor does not exist.
 *             name = "default";
 *         }
 *
 *         public A(String name) {
 *             this.name = name;
 *         }
 *
 *         public String toString() {
 *             return "A[" + name + "]";
 *         }
 *     }
 *
 *     static class B extends A implements Serializable{
 *         @Serial
 *         private static final long serialVersionUID = 1L;
 *
 *         private int age;
 *
 *         public B(String name, int age) {
 *             super(name);
 *         }
 *
 *         public String toString() {
 *             return "B[" + name + ", " + age + "]";
 *         }
 *     }
 *         }
 *     </pre>
 *     Output:
 *     <pre>
 *     B[default, 0]
 *     </pre>
 * </li>
 */
public class CustomSerializationSample {
    public static void main(String[] args) {
        // Create an employee
        Employee emp = new Employee("John Doe", "secret123", 30);

        // Serialize
        var file = new File("employee.ser");
        file.deleteOnExit();
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut);
             FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            out.writeObject(emp);
            out.flush();

            // Deserialize
            var deserializedEmp = (Employee) in.readObject();
            // Verify the data
            System.out.println("Original password: " + emp.getPassword());
            System.out.println("Deserialized password: " + deserializedEmp.getPassword());
            System.out.println("Name: " + deserializedEmp.getName());
            System.out.println("Age: " + deserializedEmp.getAge());

        } catch (ClassNotFoundException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static class Employee implements Serializable { // Note that in this version we don't need to implement Externalizable interface
        // Use serialVersionUID to maintain version compatibility
        private static final long serialVersionUID = 1L;

        private String name;
        private transient String password; // transient fields aren't serialized by default
        private int age;
        private String encryptedPassword; // we'll use this to store password securely

        public Employee(String name, String password, int age) {
            this.name = name;
            this.password = password;
            this.age = age;
        }

        // Custom serialization method
        // Note that we can have a custom serialization with writeObject method and omit a readObject method for standard deserialization and vice versa.
        private void writeObject(ObjectOutputStream out) throws IOException { // If the access privilege is not private, or the method signature is different, this method won't be executed when serializing
            // First, call the default serialization implementation
            out.defaultWriteObject();

            // Custom serialization logic
            if (password != null) {
                // Simple encryption (for demonstration - use proper encryption in production)
                encryptedPassword = encrypt(password);
            }

            // Write any additional fields
            out.writeObject(encryptedPassword);
        }

        // Custom deserialization method
        // Note that we can omit a writeObject method for a standard serialization and have a custom readObject method custom deserialization.
        // Try removing the above writeObject method and use defaultReadObject() and assigning password to a constant value in the below method.
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException { // If the access privilege is not private, or the method signature is different, this method won't be executed when deserializing
            // First, call the default deserialization implementation
            in.defaultReadObject();

            // Custom deserialization logic
            encryptedPassword = (String) in.readObject();
            if (encryptedPassword != null) {
                // Decrypt the password
                password = decrypt(encryptedPassword);
            }
        }

        // Example encryption method (use proper encryption in production)
        private String encrypt(String data) {
            // Simple demonstration encryption - NOT for production use
            StringBuilder encrypted = new StringBuilder();
            for (char c : data.toCharArray()) {
                encrypted.append((char) (c + 1));
            }
            return encrypted.toString();
        }

        // Example decryption method (use proper decryption in production)
        private String decrypt(String data) {
            // Simple demonstration decryption - NOT for production use
            StringBuilder decrypted = new StringBuilder();
            for (char c : data.toCharArray()) {
                decrypted.append((char) (c - 1));
            }
            return decrypted.toString();
        }

        // Example usage methods
        public String getPassword() {
            return password;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
