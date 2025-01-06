package com.sanver.basics.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public class InterfaceSample2 {
    public static void main(String[] args) {
        var person = new Person("Ahmet");
        System.out.println(person.lovable());
        System.out.println(person instanceof Serializable);
        System.out.println(person.isLovable()); // If there were no instance method isLovable, this would result in symbol not found, since the static method is on the interface, not the class. This only causes error for static methods in an interface. Static methods on a class, or a parent class can be called from an instance/child instance.
//        System.out.println(Person.isLovable()); // This results in a compile error, since Person class has no static method isLovable. If Person had a parent class Organism which has a static method move, we would be able to invoke that move method through an instance of Person thought. This is not the case with static methods in an interface.
        System.out.println(Lovable.isLovable());
    }

    interface Lovable extends Serializable, AutoCloseable { // For interfaces extending other interfaces we use extends rather than implements and we can extend from multiple interfaces.
        int DEGREE = 1; // Fields in interfaces are always public static and final.

        static boolean isLovable() {
            return false;
        }

        String lovable();

        private int getSize(String value) {
            return value.length();
        }

        default String getMessage(String value) {
            return value + " with size " + getSize(value);
        }
    }

    record Person(String name) implements Lovable {
        public String lovable() {
            return getMessage(this.name) + " " + isLovable(); // isLovable will invoke the non-static method, static isLovable on the interface can only be called through the interface (i.e. Lovable.isLovable())
        }

        public String isLovable() { // Since the static isLovable can only be called from the interface, this does not result in a compile error, although having the same method name, parameters (parameterless in this case) but different return type.
            return name.length() > 4 ? "is lovable" : "not lovable";
        }

        // Assume we don't have the static isLovable() method in the Lovable interface, still the following definition results in isLovable()' is already defined in 'Person'.
        // However, the static isLovable() method in the Lovable interface does not result any compile errors, although there is a non-static method with same name and same arguments in the Person class.
//        public static boolean isLovable() {
//            return false;
//        }

        @Override
        public void close() { // Since Lovable extends Autocloseable, it should implement the close method.
            System.out.println("Closed " + this);
        }
    }

    interface One {
        void go() throws IOException;
    }

    interface Two {
        void go() throws FileNotFoundException;
    }

    class Three implements One, Two {
        public void go() throws FileNotFoundException {
            System.out.println();
        }
    }
}
