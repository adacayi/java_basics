package com.sanver.basics.lambda;

import java.util.function.BiFunction;

public class MethodReferenceForNonStatic {
    class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String greet(String name) {
            return String.format("Selamunaleykum %s.\nMy name is %s.", name, this.name);
        }
    }

    public static void main(String[] args) {
        BiFunction<Person, String, String> greet = Person::greet;// To refer to non static greet method of person
        // the first parameter of BiFunction must be Person. Else it will get an error.
        Person ahmet = new MethodReferenceForNonStatic().new Person("Ahmet");
        String result = greet.apply(ahmet, "Mustafa");// This will use the greet method in ahmet person with the string
        // parameter "Mustafa"
        System.out.println(result);
    }
}
