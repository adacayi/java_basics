package com.sanver.basics.objects;

import lombok.Value;
import lombok.experimental.NonFinal;

public class MultipleCastingSample {
    public static void main(String[] args) {
        var person = (Person & Walk & Talk) getPerson("Jane", 12);
        System.out.println(person.getName());
        person.walk();
        person.talk();
        System.out.println();
        walkAndTalkAPerson(person);
        var adult = new Adult("George", 26);
        walkAndTalkAPerson(adult);
    }

    private static Person getPerson(String name, int age) {
        return (age < 20 ? new Child(name, age) : new Adult(name, age));
    }

    private static <T extends Person & Walk & Talk> void walkAndTalkAPerson(T person) {
        System.out.println(person);
        person.walk();
        person.talk();
        System.out.println();
    }

    interface Walk {
        void walk();
    }

    interface Talk {
        void talk();
    }

    @Value
    @NonFinal //Value classes are final. This is to make Person class non-final so that we can extend it.
    // The fields are still final because of the @Value annotation. We need to annotate them with @NonFinal individually to make them not final.
    static class Person {
        String name;
        int age;
    }

    static class Child extends Person implements Walk, Talk {

        public Child(String name, int age) {
            super(name, age);
        }

        public void walk() {
            System.out.printf("Child %s at age %d started walking%n", getName(), getAge());
        }

        public void talk() {
            System.out.printf("Child %s at age %d started talking%n", getName(), getAge());
        }
    }

    static class Adult extends Person implements Walk, Talk {

        public Adult(String name, int age) {
            super(name, age);
        }

        public void walk() {
            System.out.printf("Adult %s at age %d started walking%n", getName(), getAge());
        }

        public void talk() {
            System.out.printf("Adult %s at age %d started talking%n", getName(), getAge());
        }
    }
}
