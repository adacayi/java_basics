package com.sanver.basics.lambda;

import java.util.function.BiFunction;

public class MethodReferenceForNonStatic {
    static class Person {
        private final String name;

        public Person(String name) {
            this.name = name;
        }

        public String greet(String name) {
            return String.format("Selamunaleykum %s.%nMy name is %s.", name, this.name);
        }
    }

    static class Student extends Person{
        public Student(String name) {
            super(name);
        }

        @Override
        public String greet(String name) {
            return String.format("Hi, %s. I am a student. My name is %s.", name, super.name);
        }
    }

    public static void main(String[] args) {
        BiFunction<Person, String, String> greet = Person::greet;// To refer to non-static greet method of person
        // the first parameter of BiFunction must be Person. Else it will get an error. It corresponds to (person, name) -> person.greet(name)
        Person ahmet = new Person("Ahmet");
        String result = greet.apply(ahmet, "Mustafa");// This will use the greet method in ahmet person with the string
        // parameter "Mustafa"
        System.out.println(result);
        Student mehmet = new Student("Mehmet");
        result = greet.apply(mehmet, "Ali"); // This will use the greet method in mehmet Student with the string
        // parameter "Ali"
        System.out.println(result);
    }
}
