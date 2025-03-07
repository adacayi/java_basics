package com.sanver.basics.serialization;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationDeserializationWithNonSerializableBaseClass {
    public static void main(String[] args) {
        var file = new File("serialize.ser");
        serialize(file); // Run first with serialize and then, comment this line and uncomment the below line.
//        deserialize(file);
        // Note that only constructors, static, non-static field instantiations and static, non-static code block executions are done for the non-serializable base classes.
        // No constructor, static, non-static field instantiations are done for the serializable classes.
    }

    private static void serialize(File file) {
        try (
                var fos = new FileOutputStream(file);
                var oos = new ObjectOutputStream(fos)) {
            var object = new C(7);
            System.out.println();
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deserialize(File file) {
        try (
                var fis = new FileInputStream(file);
                var ois = new ObjectInputStream(fis)) {
            var copy = (C) ois.readObject();
            System.out.println(copy.value);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static class A {
        static int staticValue = calculateStaticValue();

        static {
            System.out.println("Static block in A");
        }

        int value = calculateValue();

        {
            System.out.println("Non-static block in A");
        }

        public A() {
            System.out.println("A Constructor with no parameter run");
        }

        public A(int i) {
            System.out.println("A Constructor with int parameter run");
        }

        static int calculateStaticValue() {
            System.out.println("Calculating static value for A");
            return 5;
        }

        static int calculateValue() {
            System.out.println("Calculating non-static value for A");
            return 3;
        }
    }

    static class B extends A implements Serializable {
        static int staticValue = calculateStaticValue();

        static {
            System.out.println("Static block in B");
        }

        int value = calculateValue();

        {
            System.out.println("Non-static block in B");
        }

        public B() {
            System.out.println("B Constructor with no parameter run");
        }

        public B(int i) {
            System.out.println("B Constructor with int parameter run");
        }

        static int calculateStaticValue() {
            System.out.println("Calculating static value for B");
            return 11;
        }

        static int calculateValue() {
            System.out.println("Calculating non-static value for B");
            return 3;
        }
    }

    static class C extends B implements Serializable {
        private static final long serialVersionUID = -2944410120993042742L; // If we explicitly define a serialVersionUID and then change the class to add a new field (removing a field makes deserialization incompatible),
        // old serialized instances with the same id will be deserialized without an error and
        // the new field value be its default value (null if it is a reference type, primitive default value if it is a primitive type).
        // If we didn't explicitly define this constant, a value would be generated automatically by the compiler.
        // When we made a change to the class and try to deserialize, compiler would generate a new number for the new class, which would not be equal to the old class,
        // thus failing the deserialization of the old class instance.
        // The way to solve the issue is to explicitly define the serialVersionUID in that case and set its value from the value we see in the exception error showing the old instance serialVersionUID.
        // Note that this explicit definition is not necessary for records, because if the change is compatible with the old record
        // (for records, adding a field, removing a field or changing the field order are all compatible, unlike a regular class. Changing the record component type is not compatible though.),
        // it will deserialize without an issue.
        static int staticValue = calculateStaticValue();

        static {
            System.out.println("Static block in C");
        }

        int value = calculateValue();
//        A a = new A(); // Since A is not serializable this would result in an error in serialization.
        // However, if this is added later, and we try to deserialize a prior instance without this field, deserialization would work fine.

        {
            System.out.println("Non-static block in C");
        }

        public C() {
            System.out.println("C Constructor with no parameter run");
        }

        public C(int i) {
            System.out.printf("C Constructor with %d parameter run%n", i);
            value = i;
        }

        static int calculateStaticValue() {
            System.out.println("Calculating static value for C");
            return 16;
        }

        static int calculateValue() {
            System.out.println("Calculating non-static value for C");
            return 3;
        }
    }
}
