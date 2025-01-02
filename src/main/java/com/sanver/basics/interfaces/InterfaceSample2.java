package com.sanver.basics.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

public class InterfaceSample2 {
    public static void main(String[] args) {
        var person = new Person("Ahmet");
        System.out.println(person.lovable());
        System.out.println(person instanceof Serializable);
        System.out.println(person.isLovable());
//        System.out.println(Person.isLovable()); // This results in a compile error, since Person class has no static method isLovable
        System.out.println(Lovable.isLovable());
    }

    interface Lovable extends Serializable {
        int DEGREE = 1; // Fields in interfaces are always public static and final.

        static String isLovable() {
            return "not lovable";
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
            return getMessage(this.name) + " " + isLovable();
        }

        public String isLovable() {
            return name.length() > 4 ? "is lovable" : "not lovable";
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
