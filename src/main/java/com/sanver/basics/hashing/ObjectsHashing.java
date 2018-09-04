package com.sanver.basics.hashing;

import java.util.Objects;
import java.util.Scanner;

public class ObjectsHashing {
    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int traditionalHashCoding() {
            return super.hashCode();
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Person))
                return false;

            Person second = (Person) obj;
            return second.name.equals(name) && second.age == age;
        }
    }

    public static void main(String... args) {
        Person first = new Person("Abdullah", 35);
        Person second = new Person("Abdullah", 35);
        System.out.printf("First hash code : %d\nSecond hash code: %d\n", first.hashCode(), second.hashCode());
        System.out.println("\nIf we used traditional hash coding\n");
        System.out.printf("First hash code : %d\nSecond hash code: %d",
                first.traditionalHashCoding(), second.traditionalHashCoding());
    }
}
