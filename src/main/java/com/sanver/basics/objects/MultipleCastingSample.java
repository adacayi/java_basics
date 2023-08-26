package com.sanver.basics.objects;

import lombok.Value;
import lombok.experimental.NonFinal;

public class MultipleCastingSample {
    @Value
    @NonFinal
    static class Person {
        String name;
        int age;
    }

    static class Child extends Person implements Walk {

        public Child(String name, int age) {
            super(name, age);
        }
    }

    static class Adult extends Person implements Walk{

        public Adult(String name, int age) {
            super(name, age);
        }
    }

    interface Walk {
        default void walk() {
            System.out.println("Walking started");
        }
    }

    public static void main(String[] args) {
        var person = (Person & Walk) getWalkingPerson("Jane", 12);
        person.walk();
        System.out.println(person.getName());
    }

    private static Person getWalkingPerson(String name, int age) {
        return (age < 20 ? new Child(name, age) : new Adult(name, age));
    }
}
