package com.sanver.basics.objects;

import lombok.Value;
import lombok.experimental.NonFinal;

public class MultipleCastingSample {
    public static void main(String[] args) {
        var person = (Person & Walk & Talk) getPerson("Jane", 12);
        person.walk();
        person.talk();
        System.out.println(person.getName());
    }

    private static Person getPerson(String name, int age) {
        return (age < 20 ? new Child(name, age) : new Adult(name, age));
    }

    interface Walk {
        default void walk() {
            System.out.println("Walking started");
        }
    }

    interface Talk {
        default void talk() {
            System.out.println("Talking started");
        }
    }

    @Value
    @NonFinal
    static class Person {
        String name;
        int age;
    }

    static class Child extends Person implements Walk, Talk {

        public Child(String name, int age) {
            super(name, age);
        }
    }

    static class Adult extends Person implements Walk, Talk {

        public Adult(String name, int age) {
            super(name, age);
        }
    }
}
