package com.sanver.basics.serialization;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class Employee implements Serializable {

    /**
     * This code declares a special identifier called serialVersionUID that helps Java keep track of different versions of a serializable class (in this case, the Employee class). Let me break down what it does:
     * The purpose of this code is to provide a version number for the Employee class when it gets converted to bytes (serialized) and back to an object (deserialized). Think of it like a fingerprint that identifies a specific version of your class.
     * This code doesn't take any inputs or produce any direct outputs. Instead, it's a static final field (meaning it's constant and shared across all instances of the Employee class) with a specific long number value: -6536466959199096679L.
     * The way it works is straightforward - when you serialize an Employee object (save it to a file or send it over a network), Java includes this version number with the data. Later, when you try to deserialize it, Java checks if the version numbers match. If they don't match, it means the class structure has changed, and Java will refuse to load the data to prevent errors.
     * The logic is simple but important: if you make changes to the Employee class (like adding new fields or changing existing ones), you should update this number. If you don't explicitly declare this number, Java will automatically generate one based on the class structure, which can cause problems when trying to load previously saved data.
     * This is particularly useful when you're building applications where you need to save Employee objects to files or send them across a network, ensuring that the data structure remains compatible across different versions of your application.
     */
    @Serial
    private static final long serialVersionUID = -6536466959199096679L;

    private String firstName;
    private String lastName;
    private transient double salary;
    private Address[] addresses;

    Employee(final String firstName, final String lastName, final double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }
}

@Data
@AllArgsConstructor
class Address implements Serializable {
    @Serial
    private static final long serialVersionUID = 8342617942282891201L; // This should be unique for each class.
    private String street;
}
