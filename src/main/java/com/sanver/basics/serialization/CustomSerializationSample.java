package com.sanver.basics.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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
